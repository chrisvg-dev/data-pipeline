package com.arkon.pipeline.v1.controller;

import com.arkon.pipeline.v1.model.Record;
import com.arkon.pipeline.v1.model.Template;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/api/pipeline")
public class Pipeline {

    @GetMapping
    public List<Record> get() {
        String URI = "https://datos.cdmx.gob.mx/api/3/action/datastore_search?resource_id=ad360a0e-b42f-482c-af12-1fd72140032e";
        RestTemplate rt = new RestTemplate();
        Template result = rt.getForObject(URI, Template.class);
        return result.getResult().getRecords();
    }
}
