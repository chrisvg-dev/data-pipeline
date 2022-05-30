package com.arkon.pipeline.v1.graphql;

import com.arkon.pipeline.v1.model.Information;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class GraphQLProvider {
    @Autowired private AllInformationFetcher allInformationFetcher;
    @Value("classpath:schema.graphqls")
    private Resource resource;
    public GraphQL graphQL;
    @Bean
    public GraphQL graphQL(){
        return graphQL;
    }

  //  @PostConstruct
    public void loadSchema() throws IOException {
        File schemaFile = resource.getFile();
        TypeDefinitionRegistry registry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring().type("Query",
                        typeWriting -> typeWriting.dataFetcher("getAllPerson", allInformationFetcher))
                .build();

    }
}
