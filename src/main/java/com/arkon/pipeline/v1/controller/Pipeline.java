package com.arkon.pipeline.v1.controller;

import com.arkon.pipeline.v1.dto.AlcaldiaDto;
import com.arkon.pipeline.v1.dto.Template;
import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.services.DataServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/api/pipeline")
@CrossOrigin(origins = "*")
public class Pipeline {
    public static final Logger log = LoggerFactory.getLogger(Pipeline.class);

    @Autowired private DataServices dataServices;

    @GetMapping
    public List<Information> get() {
        return this.dataServices.records();
    }

    @GetMapping("/alcaldias")
    public List<AlcaldiaDto> alcaldiasDisponibles() {
        return this.dataServices.alcaldiasDisponibles();
    }

    @GetMapping("/alcaldia/{alcaldia}")
    public List<Information> unidadesPorAlcaldia(@PathVariable String alcaldia) {
        log.info(alcaldia);
        return this.dataServices.findByAlcaldia( alcaldia );
    }
    @GetMapping("/udisponibles")
    public List<Information> unidadesDisponibles() {
        return this.dataServices.unidadesDisponibles();
    }

    @GetMapping("/vehiculo/{id}")
    public Information ubicacionVehicle(@PathVariable Integer id) {
        return this.dataServices.buscarPorId(id);
    }

    @GetMapping("/recolectar")
    public boolean recolectar() {
        try {
            Template template = this.dataServices.stream();
            this.dataServices.persist(template);
            return true;
        }catch (Exception e)  {
            return false;
        }
    }

    @PutMapping
    public boolean agregarAlcaldia(@RequestBody AlcaldiaDto alcaldia) {
        Information rec = this.dataServices.agregarAlcaldia(alcaldia);
        return rec != null;
    }
}
