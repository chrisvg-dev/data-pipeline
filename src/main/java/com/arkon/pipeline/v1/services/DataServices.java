package com.arkon.pipeline.v1.services;

import com.arkon.pipeline.v1.dto.AlcaldiaDto;
import com.arkon.pipeline.v1.dto.Template;
import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.repository.InformationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DataServices {
    public static final Logger log = LoggerFactory.getLogger(Data.class);

    @Autowired private InformationRepository recordRepository;

    public List<Information> records(){
        return this.recordRepository.findAll();
    }

    public List<AlcaldiaDto> alcaldiasDisponibles(){
        return this.recordRepository.findAll().stream()
                .map( information -> new AlcaldiaDto(null, information.getAlcaldia()))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Information> findByAlcaldia(String alcaldia){
        log.info(alcaldia);
        return this.recordRepository.findByAlcaldia(alcaldia.toUpperCase()).orElse(null);
    }

    public void persist(Template template) {
        List<Information> registers = template.getResult().getRecords().stream()
                .filter( record -> record.getPosition_latitude().doubleValue() > 0.0)
                .map( record -> {
                    Information info = new Information();
                    info.setIdVehiculo( record.getVehicle_id() );
                    info.setUbicacion( record.getGeographic_point() );
                    info.setLatitud( record.getPosition_latitude() );
                    info.setLongitud( record.getPosition_longitude() );
                    info.setAlcaldia( null );
                    info.setStatusVehiculo( record.getVehicle_current_status() == 1 );
                    log.info( info.toString() );
                    return info;
                })

                .collect(Collectors.toList());

        this.recordRepository.saveAll(registers);
    }

    public List<Information> unidadesDisponibles() {
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
        /**
         * WARNING
         */
        String URI = "https://datos.cdmx.gob.mx/api/3/action/datastore_search?resource_id=ad360a0e-b42f-482c-af12-1fd72140032e&limit=207";
        RestTemplate rt = new RestTemplate();
        return rt.getForObject(URI, Template.class);
    }

    public Information agregarAlcaldia(AlcaldiaDto alcaldiaDto) {
        Information registro = this.recordRepository.findById(alcaldiaDto.getIdVehiculo()).orElse(null);
        if (registro != null) {
            registro.setAlcaldia( alcaldiaDto.getAlcaldia().toUpperCase() );
            this.recordRepository.save(registro);
            return registro;
        }
        return null;
    }

}
