package com.arkon.pipeline.v1.services;

import com.arkon.pipeline.v1.dto.AlcaldiaDto;
import com.arkon.pipeline.v1.dto.maps.GoogleMaps;
import com.arkon.pipeline.v1.dto.Template;
import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.repository.AlcaldiaRepository;
import com.arkon.pipeline.v1.repository.InformationRepository;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class DataServices {
    public static final Logger log = LoggerFactory.getLogger(Data.class);
    @Value("${api.url.cdmx}")
    private String urlApiCDMX;
    @Autowired private InformationRepository recordRepository;
    @Autowired private AlcaldiaRepository alcaldiaRepository;
    @Autowired private RestTemplate templateService;

    public List<Information> records(){
        return this.recordRepository.findAll();
    }

    public Set<AlcaldiaDto> alcaldiasDisponibles(){
        return this.recordRepository.findAll().stream()
                .map( information -> new AlcaldiaDto( null, information.getAlcaldia().toString() ))
                .collect(Collectors.toSet());
    }

    public void persist(Template template) {
        List<Information> registers = template.getResult().getRecords().stream()
                .filter( record ->
                    record.getPosition_latitude() > 0.0 || record.getPosition_longitude() > 0.0
                 )
                .map( record -> {
                    Information info = new Information();
                    info.setIdVehiculo( record.getVehicle_id() );
                    info.setLatitud( record.getPosition_latitude() );
                    info.setLongitud( record.getPosition_longitude() );
                    info.setAlcaldia(
                            this.buscarAlcaldiaPorCoordenadas(
                                    record.getPosition_latitude(),
                                    record.getPosition_longitude()
                            )
                    );
                    info.setStatusVehiculo( record.getVehicle_current_status() == 1 );
                    return info;
                })

                .collect(Collectors.toList());

        this.recordRepository.saveAll(registers);
    }

    public List<Information> unidadesDisponibles() {
        return this.recordRepository.findByStatusVehiculo(true).orElse(null);
    }

    public Template stream() {
        /**
         * WARNING
         */
        this.recordRepository.deleteAll();
        /**
         * WARNING
         */
        return this.templateService.getForObject(this.urlApiCDMX, Template.class);
    }

    public synchronized Alcaldia buscarAlcaldiaPorCoordenadas(Double lat, Double lng) {
        return getAlcaldia(lat, lng, this.templateService, this.alcaldiaRepository, log);
    }

    @Nullable
    public static Alcaldia getAlcaldia(Double lat, Double lng, RestTemplate templateService, AlcaldiaRepository alcaldiaRepository, Logger log) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lng+"&key=AIzaSyA0wl0GTeqSZQOnYkpG_OMYkmw9J8KEOwY";
        try {
            GoogleMaps gm = templateService.getForObject(
                    url,
                    GoogleMaps.class
            );
            assert gm != null;
            int len = !gm.getResults().isEmpty() ? gm.getResults().size() : 0;

            String name = gm.getResults().get(len-4).getAddress_components().get(0).getLong_name();
            boolean exists = alcaldiaRepository.existsByName(name);

            if (exists) return alcaldiaRepository.findByName(name).orElse(null);
            Alcaldia newObject = new Alcaldia();
            newObject.setName(name);
            return alcaldiaRepository.save(newObject);

        } catch (Exception e){
            log.error( e.getMessage() );
            return null;
        }
    }
}
