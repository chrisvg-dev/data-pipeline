package com.arkon.pipeline.v1.services;

import com.arkon.pipeline.v1.model.Record;
import com.arkon.pipeline.v1.model.Template;
import com.arkon.pipeline.v1.repository.RecordRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
public class DataServices {

    @Autowired private RecordRepository recordRepository;

    public List<Record> records(){
        return this.recordRepository.findAll();
    }

    public void persist(Template template) {
        template.getResult().getRecords().stream()
                .forEach( record -> {
                    this.recordRepository.save(record);
                } );
    }

    public Template stream() {
        String URI = "https://datos.cdmx.gob.mx/api/3/action/datastore_search?resource_id=ad360a0e-b42f-482c-af12-1fd72140032e";
        RestTemplate rt = new RestTemplate();
        return rt.getForObject(URI, Template.class);
    }

    public Record agregarAlcaldia(Integer id, String alcaldia) {
        Record registro = this.recordRepository.findByRecordId(id).orElse(null);
        if (registro != null) {
            registro.setAlcaldia( alcaldia );
            this.recordRepository.save(registro);
            return registro;
        }
        return null;
    }

}
