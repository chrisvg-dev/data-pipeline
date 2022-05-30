package com.arkon.pipeline.v1.graphql;

import com.arkon.pipeline.v1.dto.AlcaldiaDto;
import com.arkon.pipeline.v1.dto.Template;
import com.arkon.pipeline.v1.dto.maps.GoogleMaps;
import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.repository.AlcaldiaRepository;
import com.arkon.pipeline.v1.repository.InformationRepository;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class QueryResolver implements GraphQLQueryResolver {
    public static final Logger log = LoggerFactory.getLogger(Data.class);
    @Value("${api.url.cdmx}")
    private String urlApiCDMX;
    @Autowired private InformationRepository recordRepository;
    @Autowired private AlcaldiaRepository alcaldiaRepository;
    @Autowired private RestTemplate templateService;

    public List<Information> records(){
        return this.recordRepository.findAll();
    }

    public List<Alcaldia> alcaldiasDisponibles(){
        return this.alcaldiaRepository.findAll();
    }

    public List<Information> unidadesDisponibles() {
        return this.recordRepository.findByStatusVehiculo(true).orElse(null);
    }

    public Information buscarPorId(Integer idVehiculo) {
        return this.recordRepository.findByIdVehiculo(idVehiculo).orElse(null);
    }
    public List<Information> buscarPorAlcaldia(String alcaldia){
        Alcaldia alc = this.alcaldiaRepository.findByName(alcaldia.toUpperCase()).orElse(null);
        return this.recordRepository.findByAlcaldia(alc).orElse(null);
    }
}
