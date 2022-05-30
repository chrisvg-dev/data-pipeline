package com.arkon.pipeline.v1.controller;

import com.arkon.pipeline.v1.dto.AlcaldiaDto;
import com.arkon.pipeline.v1.dto.maps.AddressComponent;
import com.arkon.pipeline.v1.dto.maps.GoogleMaps;
import com.arkon.pipeline.v1.dto.Template;
import com.arkon.pipeline.v1.dto.maps.Results;
import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.services.DataServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
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
    public Set<AlcaldiaDto> alcaldiasDisponibles() {
        return this.dataServices.alcaldiasDisponibles();
    }

    @GetMapping("/alcaldia/{alcaldia}")
    public List<Information> unidadesPorAlcaldia(@PathVariable String alcaldia) {
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
            log.error( "Error a la hora de recolectar: " + e.getMessage() );
            return false;
        }
    }

    @PutMapping
    public boolean agregarAlcaldia(@RequestBody AlcaldiaDto alcaldia) {
        Information rec = this.dataServices.agregarAlcaldia(alcaldia);
        return rec != null;
    }

    @GetMapping("/buscarAlcaldia")
    public String buscarAlcaldia() {
        return this.dataServices.buscarAlcaldiaPorCoordenadas(19.3174991607666, -99.18779754638672);
    }
}
