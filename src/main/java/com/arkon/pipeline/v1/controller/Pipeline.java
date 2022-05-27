package com.arkon.pipeline.v1.controller;

import com.arkon.pipeline.v1.model.Record;
import com.arkon.pipeline.v1.model.Template;
import com.arkon.pipeline.v1.services.DataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/api/pipeline")
@CrossOrigin(origins = "*")
public class Pipeline {

    @Autowired private DataServices dataServices;

    @GetMapping
    public List<Record> get() {
        return this.dataServices.records();
    }

    @GetMapping("/recolectar")
    public String recolectar() {
        Template template = this.dataServices.stream();
        this.dataServices.persist(template);
        return "Datos recolectados";
    }

    @GetMapping("/id/{id}/alcaldia/{alcaldia}")
    public String agregarAlcaldia(@PathVariable Integer id, @PathVariable String alcaldia) {
        System.out.println(alcaldia);
        Record rec = this.dataServices.agregarAlcaldia(id, alcaldia);
        return rec != null ? "Alcaldia agregada" : "Error";
    }
}
