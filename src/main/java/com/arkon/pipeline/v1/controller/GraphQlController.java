package com.arkon.pipeline.v1.controller;

import com.arkon.pipeline.v1.dto.AlcaldiaDto;
import com.arkon.pipeline.v1.graphql.GraphQLRegister;
import com.arkon.pipeline.v1.graphql.QueryResolver;
import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.repository.InformationRepository;
import com.arkon.pipeline.v1.services.DataServices;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/graph")
@CrossOrigin(origins = "*")
public class GraphQlController {
    public static final Logger log = LoggerFactory.getLogger(GraphQlController.class);
    @Autowired private DataServices dataServices;
    @Autowired private QueryResolver queryResolver;

    @Autowired
    private GraphQLRegister register;

    @Value("classpath:schema.graphqls")
    private Resource schemaResource;

    private GraphQL graphQL;

    @PostConstruct
    public void loadSchema() {
        try {
            File schemaFile = schemaResource.getFile();
            TypeDefinitionRegistry registry = new SchemaParser().parse(schemaFile);
            RuntimeWiring wiring = register.buildWiring();
            GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
            graphQL = GraphQL.newGraphQL(schema).build();
        } catch (Exception e) {
            log.error( e.getMessage() );
        }
    }

    @PostMapping
    public ResponseEntity<Object> getAll(@RequestBody String query) {
        ExecutionResult result = graphQL.execute(query);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }
}
