package com.arkon.pipeline.v1.services;

import com.arkon.pipeline.v1.dto.AlcaldiaDto;
import com.arkon.pipeline.v1.dto.maps.GoogleMaps;
import com.arkon.pipeline.v1.dto.Template;
import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.repository.AlcaldiaRepository;
import com.arkon.pipeline.v1.repository.InformationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Transactional
public class DataServices {
    public static final Logger log = LoggerFactory.getLogger(Data.class);
    @Value("${api.url.cdmx}")
    private String urlApiCDMX;
    @Value("${api.url.apiGoogle}")
    private String urlGoogle;
    @Value("${api.url.apiKey}")
    private String apiKey;
    @Autowired private InformationRepository recordRepository;
    @Autowired private AlcaldiaRepository alcaldiaRepository;
    @Autowired private RestTemplate templateService;

    public List<Information> record(){
        return this.recordRepository.findAll();
    }

    public void persist(Template template) {
            List<Information> data = template.getResult().getRecords().stream()
                .filter( record ->
                        record.getPosition_latitude() > 0.0 || record.getPosition_longitude() > 0.0
                )
                .map(record -> {
                    Information info = new Information();
                    info.setIdVehiculo( record.getVehicle_id() );
                    info.setLatitud( record.getPosition_latitude() );
                    info.setLongitud( record.getPosition_longitude() );
                    info.setStatusVehiculo( record.getVehicle_current_status() == 1 );
                    info.setAlcaldia(null);
                    return info;
                }).collect(Collectors.toList());
            List<Information> modified = data.parallelStream().map(record -> {
                Alcaldia alcaldia = this.buscarAlcaldiaPorCoordenadas(record.getLatitud(), record.getLongitud());
                record.setAlcaldia(alcaldia);
                log.info(alcaldia.toString());
                return record;
            }).collect(Collectors.toList());
            this.recordRepository.saveAll(data);
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

    public Alcaldia buscarAlcaldiaPorCoordenadas(Double lat, Double lng) {
        String url = this.urlGoogle+"latlng="+lat+","+lng+"&key=" + this.apiKey;
        try {
            GoogleMaps gm = templateService.getForObject(
                    url,
                    GoogleMaps.class
            );

            int len = gm.getResults().size();
            String name = gm.getResults().get(len-4).getAddress_components().get(0).getLong_name();

            boolean exists = alcaldiaRepository.existsByName(name);
            if (exists) return alcaldiaRepository.findByName(name).orElse(null);

            Alcaldia alcaldia = new Alcaldia();
            alcaldia.setName(name);
            return alcaldiaRepository.save(alcaldia);

        } catch (Exception e){
            log.error("ERROR: "+  e.getLocalizedMessage() );
            return null;
        }
    }


}
