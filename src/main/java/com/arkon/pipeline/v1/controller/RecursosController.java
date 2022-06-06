package com.arkon.pipeline.v1.controller;

import com.arkon.pipeline.v1.dto.cdmx.Template;
import com.arkon.pipeline.v1.services.RecursosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Esta clase tiene el objetivo de brindar un endpoint para poder recolectar la información desde un endpoint
 * Reemplaza a la configuración inicial donde se utilizaba CommandLineRunner
 */
@RestController
@RequestMapping("/api/pipeline") // Endpoint de la aplicación
@CrossOrigin(origins = "*") // Activa el acceso a la API desde cualquier ubicación
public class RecursosController {
    public static final Logger log = LoggerFactory.getLogger(RecursosController.class);
    private final RecursosService dataServices;
    public RecursosController(RecursosService dataServices) {
        this.dataServices = dataServices;
    }

    /**
     * Los datos son extraídos desde la API de la ciudad de México mediante el método recolectar,
     * para eso se hace uso de RestTemplate, lo cual permite conectar al servicio web con la API de los
     * metrobuses de la Ciudad de México.
     *
     * Si hay un error a la hora de recolectar se retorna un false, lo cual se utiliza para validar la información
     * en el frontend
     * @return
     */
    @GetMapping("/recolectar")
    public boolean collect() {
        try {
            /**
             * Template almacena la información mediante la implementación del patrón de diseño DTO,
             * mapea toda la información del JSON mediante clases
             */
            Template template = this.dataServices.reset();
            this.dataServices.persist(template);
            return true;
        }catch (Exception e)  {
            log.error( "Error a la hora de recolectar: " + e.getMessage() );
            return false;
        }
    }
}
