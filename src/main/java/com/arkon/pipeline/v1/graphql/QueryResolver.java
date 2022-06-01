package com.arkon.pipeline.v1.graphql;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Informacion;
import com.arkon.pipeline.v1.repository.AlcaldiaRepository;
import com.arkon.pipeline.v1.repository.InformationRepository;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.util.List;

@Component
public class QueryResolver implements GraphQLQueryResolver {
    public static final Logger log = LoggerFactory.getLogger(Data.class);
    private final InformationRepository recordRepository;
    private final AlcaldiaRepository alcaldiaRepository;

    public QueryResolver(InformationRepository recordRepository, AlcaldiaRepository alcaldiaRepository) {
        this.recordRepository = recordRepository;
        this.alcaldiaRepository = alcaldiaRepository;
    }

    public List<Informacion> records(){
        return this.recordRepository.findAll();
    }
    public List<Alcaldia> alcaldiasDisponibles(){
        return this.alcaldiaRepository.findAll();
    }
    public List<Informacion> unidadesDisponibles() {
        return this.recordRepository.findByStatusVehiculo(true).orElse(null);
    }
    public Informacion buscarPorId(Integer idVehiculo) {
        return this.recordRepository.findByIdVehiculo(idVehiculo).orElse(null);
    }
    public List<Informacion> buscarPorAlcaldia(String alcaldia){
        Alcaldia alc = this.alcaldiaRepository.findByName(alcaldia.toUpperCase()).orElse(null);
        return this.recordRepository.findByAlcaldia(alc).orElse(null);
    }
}
