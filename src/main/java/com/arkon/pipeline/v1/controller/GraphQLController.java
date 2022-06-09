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
 * y del API de Google
 */
@RestController
@RequestMapping("/api/graph") // Endpoint para conectarse a la aplicación
@CrossOrigin(origins = "*")
public class GraphQLController {
    public static final Logger log = LoggerFactory.getLogger(GraphQLController.class);
    private final GraphQL graphQL;
    public GraphQLController(GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    /** Primera versión del endpoint, el cual recibía una query desde el cliente, fue reemplazada por los métodos
     * que se encuentran en la parte inferior de este archivo.
     *
     * */
    @PostMapping
     public ResponseEntity<Object> getAll(@RequestBody String query) {
        ExecutionResult result = graphQL.execute(query);
        return new ResponseEntity<>(result, HttpStatus.OK);
     }
}
