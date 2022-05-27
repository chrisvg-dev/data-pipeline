package com.arkon.pipeline.v1.services;

import com.arkon.pipeline.v1.dto.AlcaldiaDto;
import com.arkon.pipeline.v1.dto.Template;
import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.repository.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DataServices {

    @Autowired private InformationRepository recordRepository;

    public List<Information> records(){
        return this.recordRepository.findAll();
    }

    public List<AlcaldiaDto> alcaldiasDisponibles(){
        return this.recordRepository.findAll().stream()
                .map( information -> new AlcaldiaDto(null, information.getAlcaldia()))
                .collect(Collectors.toList());
    }

    public List<Information> findByAlcaldia(String alcaldia){
        return this.recordRepository.findByAlcaldia(alcaldia.toUpperCase()).orElse(null);
    }

    public void persist(Template template) {
        List<Information> registers = template.getResult().getRecords().stream()
                .map( record -> {
                    Information info = new Information();
                    info.setIdVehiculo( record.getVehicle_id() );
                    info.setUbicacion( record.getGeographic_point() );
                    info.setLatitud( record.getPosition_latitude() );
                    info.setLongitud( record.getPosition_longitude() );
                    info.setAlcaldia( null );
                    info.setStatusVehiculo( record.getVehicle_current_status() == 1 );
                    return info;
                }).collect(Collectors.toList());

        this.recordRepository.saveAll(registers);
    }

    public List<Information> disponibles() {
        return this.recordRepository.findByStatusVehiculo(true).orElse(null);
    }

    public Information buscarPorId(Integer idVehiculo) {
        return this.recordRepository.findByIdVehiculo(idVehiculo).orElse(null);
    }

    public Template stream() {
        /**
         * WARNING
         */
        this.recordRepository.deleteAll();
        String URI = "https://datos.cdmx.gob.mx/api/3/action/datastore_search?resource_id=ad360a0e-b42f-482c-af12-1fd72140032e";
        RestTemplate rt = new RestTemplate();
        return rt.getForObject(URI, Template.class);
    }

    public Information agregarAlcaldia(AlcaldiaDto alcaldiaDto) {
        Information registro = this.recordRepository.findById(alcaldiaDto.getIdVehiculo()).orElse(null);
        if (registro != null) {
            registro.setAlcaldia( alcaldiaDto.getAlcaldia() );
            this.recordRepository.save(registro);
            return registro;
        }
        return null;
    }

}
