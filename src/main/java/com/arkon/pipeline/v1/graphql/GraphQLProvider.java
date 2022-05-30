package com.arkon.pipeline.v1.graphql;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.services.DataServices;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.apache.commons.io.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {
    private GraphQL graphQL;
    @Autowired
    private DataServices dataServices;
    @Autowired
    private QueryResolver queryResolver;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        DataFetcher<List<Information>> records = data -> {
            return (List<Information>) dataServices.records();
        };
        DataFetcher<List<Alcaldia>> alcaldias = data -> {
            return (List<Alcaldia>) queryResolver.alcaldiasDisponibles();
        };
        DataFetcher<List<Information>> unidadesDisponibles = data -> {
            return (List<Information>) queryResolver.unidadesDisponibles();
        };
        DataFetcher<Information> buscarPorId = data -> {
            return (Information) queryResolver.buscarPorId(data.getArgument("idVehiculo"));
        };
        DataFetcher<List<Information>> buscarPorAlcaldia = data -> {
            return (List<Information>) queryResolver.buscarPorAlcaldia(data.getArgument("alcaldia"));
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
