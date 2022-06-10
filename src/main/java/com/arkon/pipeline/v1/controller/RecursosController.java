package com.arkon.pipeline.v1.controller;

import com.arkon.pipeline.v1.dto.ResponseDto;
import com.arkon.pipeline.v1.services.RecursosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
     * en el frontend.
     *
     * TIEMPO APROXIMADO DE EJECUCIÓN: 7 SEGS
     * Necesitas acceder a: http://URL/api/pipeline/recolectar
     * URL depende de la forma del despliegue:
     * Docker -> localhost:9090
     * Kubernetes -> IP_MINIKUBE:PUERTO
     * @return
     */
    @GetMapping("/recolectar")
    public ResponseEntity<ResponseDto> collect() {
        try {
            /**
             * Template almacena la información mediante la implementación del patrón de diseño DTO,
             * mapea toda la información del JSON mediante clases.
             */

            if (this.dataServices.countAll() > 200) {
                return ResponseEntity.ok( new ResponseDto(true, "No hace falta descargar, su base de datos ya tiene la información") );
            }
            this.dataServices.persist();
            return ResponseEntity.ok( new ResponseDto(true, "Toda la información ha sido descargada") );


        }catch (Exception e)  {
            return ResponseEntity.ok( new ResponseDto(false, String.format("Error a la hora de recolectar: %s ", e.getMessage() ) ) );
        }
    }
}
