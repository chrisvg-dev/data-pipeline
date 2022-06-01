package com.arkon.pipeline.v1.graphql;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Informacion;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.apache.commons.io.Charsets;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Permite inyectar al contexto de Spring las dependencias necesarias para trabajar con Graphql
 */
@Component
public class GraphQLProvider {
    /**
     * Inicia la instancia de Graphql, la cual será necesaria para compilar el esquema utilizado
     */
    private GraphQL graphQL;
    /**
     * Objeto que accede a la clase QueryResolver, en la cual se definen todos los métodos de acceso
     * a la información de solo lectura.
     */
    private final QueryResolver queryResolver;
    public GraphQLProvider(QueryResolver queryResolver) {
        this.queryResolver = queryResolver;
    }

    /**
     * Inyecta mediante un bean a la dependencia de Graphl al contexto de Spring
     * @return
     */
    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    /**
     * Toma el esquema y lo inicializa en la aplicación para poder hacer uso de las funciones
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    /**
     * Compila el esquema y registra las funcionalidades de la API
     * @param sdl
     * @return
     */
    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    /**
     * Se definen las funcionalidades mediante la interface Datafetcher, las cuales se agregan al contexto de la
     * aplicación con RuntimeWiring
     * @return
     */
    private RuntimeWiring buildWiring() {
        DataFetcher<List<Informacion>> records = data -> {
            return (List<Informacion>) queryResolver.records();
        };
        DataFetcher<List<Alcaldia>> alcaldias = data -> {
            return (List<Alcaldia>) queryResolver.alcaldiasDisponibles();
        };
        DataFetcher<List<Informacion>> unidadesDisponibles = data -> {
            return (List<Informacion>) queryResolver.unidadesDisponibles();
        };
        DataFetcher<Informacion> buscarPorId = data -> {
            return (Informacion) queryResolver.buscarPorId(data.getArgument("idVehiculo"));
        };
        DataFetcher<List<Informacion>> buscarPorAlcaldia = data -> {
            return (List<Informacion>) queryResolver.buscarPorAlcaldia(data.getArgument("alcaldia"));
        };

        return RuntimeWiring.newRuntimeWiring().type("Query",
                        typeWriting ->
                                typeWriting
                                        .dataFetcher("records", records)
                                        .dataFetcher("alcaldiasDisponibles", alcaldias)
                                        .dataFetcher("unidadesDisponibles", unidadesDisponibles)
                                        .dataFetcher("buscarPorId", buscarPorId)
                                        .dataFetcher("buscarPorAlcaldia", buscarPorAlcaldia)
                )
                .build();

    }
}
