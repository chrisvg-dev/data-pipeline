package com.arkon.pipeline.v1.controller;

import graphql.ExecutionResult;
import graphql.GraphQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Permite al usuario realizar las peticiones para obtener la información recolectada del API de la ciudad de México
 * y de la API de Google
 */
@RestController
@RequestMapping("/api/graph")
@CrossOrigin(origins = "*")
public class GraphQLController {
    public static final Logger log = LoggerFactory.getLogger(GraphQLController.class);
    private final GraphQL graphQL;
    public GraphQLController(GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    /**
     * Este método se encarga de gestionar las peticiones del cliente y devolver los datos con GraphQL
     * Recibe una consulta mediante el método POST
     * @param query
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> getAll(@RequestBody String query) {
        ExecutionResult result = graphQL.execute(query);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }
}
