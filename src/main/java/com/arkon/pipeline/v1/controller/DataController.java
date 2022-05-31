package com.arkon.pipeline.v1.controller;

import com.arkon.pipeline.v1.dto.Template;
import com.arkon.pipeline.v1.services.DataServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pipeline")
@CrossOrigin(origins = "*")
public class DataController {
    public static final Logger log = LoggerFactory.getLogger(DataController.class);
    private final DataServices dataServices;

    public DataController(DataServices dataServices) {
        this.dataServices = dataServices;
    }

    /**
     * Los datos son extraidos desde la API proporcionada mediante el método recolectar,
     * para eso se hace uso de RestTemplate, lo cual permite conectar
     * al servicio web con la API de los metrobuses de la Ciudad de México.
     *
     * Si hay un error a la hora de recolectar se retorna un false, lo cual se utiliza para validar la información
     * en el frontend
     * @return
     */
    @GetMapping("/recolectar")
    public boolean recolectar() {
        try {
            Template template = this.dataServices.stream();
            this.dataServices.persist(template);
            log.info("DATOS RECOLECTADOS");
            return true;
        }catch (Exception e)  {
            log.error( "Error a la hora de recolectar: " + e.getMessage() );
            return false;
        }
    }
}
