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

    /**
     * Este método se encarga de gestionar las peticiones del cliente y devolver los datos con GraphQL.
     * Recibe una consulta a través de una petición POST
     * @param query
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> getAll(@RequestBody String query) {
        ExecutionResult result = graphQL.execute(query);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    /** ---------------------------------------------------------------------------------------------------------
     * NUEVA API -> APIS PARA CONSUMIR LA INFORMACIÓN
     */
    @GetMapping("/all")
    public ResponseEntity<Object> obtenerTodos() {
        String query = "{ records { idVehiculo statusVehiculo longitud latitud alcaldia { name } } }";
        log.info(query);
        return get(query);
    }
    @GetMapping("/alcaldiasDisponibles")
    public ResponseEntity<Object> alcaldiasDisponibles() {
        String query = "{ alcaldiasDisponibles { name } }";
        return get(query);
    }
    @GetMapping("/unidadesDisponibles")
    public ResponseEntity<Object> unidadesDisponibles() {
        String query = "{ unidadesDisponibles { idVehiculo statusVehiculo longitud latitud alcaldia { name } } }";
        return get(query);
    }
    @GetMapping("/vehiculos/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Integer id) {
        String query = "{ buscarPorId(idVehiculo:"+id+") { idVehiculo statusVehiculo longitud latitud alcaldia { name } } }";
        return get(query);
    }
    @GetMapping("/alcaldias/{name}")
    public ResponseEntity<Object> buscarUnidadesPorAlcaldia(@PathVariable String name) {
        String query = "{ buscarPorAlcaldia(alcaldia:"+name+") { idVehiculo statusVehiculo longitud latitud alcaldia { name } } }";
        return get(query);
    }

    public ResponseEntity<Object> get(String query) {
        ExecutionResult result = graphQL.execute(query);
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }
    /**
     * -------------------------------------------------------------------------------------------------------------------------------
     */
}
