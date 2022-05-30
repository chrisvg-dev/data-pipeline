package com.arkon.pipeline.v1.repository;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.services.DataServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InformationRepositoryTest {
    @Autowired
    private InformationRepository informationRepository;
    @Autowired
    private AlcaldiaRepository alcaldiaRepository;

    @Test
    void ifIFindById_thenReturnsInformation(){
        Alcaldia alc = new Alcaldia(null, "AZCAPOTZALCO");

        Information info = new Information();
        info.setId(1);
        info.setAlcaldia(alc);
        info.setIdVehiculo(170);
        info.setStatusVehiculo(true);
        info.setLatitud(12.000);
        info.setLongitud(-99.000);

        this.alcaldiaRepository.save(alc);
        this.informationRepository.save(info);
        Assertions.assertNotNull( this.informationRepository.findById(1) );
    }

    @Test
    void zero_negatives_thenReturnsNull(){
        Assertions.assertNull( this.informationRepository.findById(0).orElse(null) );
        Assertions.assertNull( this.informationRepository.findById(-1).orElse(null) );
    }

    @Test
    void ifIFindByIdVehicle_thenReturnsInformation(){
        Integer id = 170;
        Assertions.assertNotNull( this.informationRepository.findByIdVehiculo(id).orElse(null) );
    }

    @Test
    void ifIdDoesntExists_thenReturnNull(){
        Assertions.assertNull( this.informationRepository.findByIdVehiculo(-1).orElse(null) );
    }

    @Test
    void ifIFindByStatus_thenIGetAList() {
        Alcaldia alc = new Alcaldia(null, "AZCAPOTZALCO");
        Information info = new Information();
        info.setId(1);
        info.setAlcaldia(alc);
        info.setIdVehiculo(170);
        info.setStatusVehiculo(true);
        info.setLatitud(12.000);
        info.setLongitud(-99.000);

        this.alcaldiaRepository.save(alc);
        this.informationRepository.save(info);

        Alcaldia alc2 = new Alcaldia(null, "AZCAPOTZALCO");

        Information info2 = new Information();
        info.setId(2);
        info.setAlcaldia(alc2);
        info.setIdVehiculo(171);
        info.setStatusVehiculo(false);
        info.setLatitud(13.000);
        info.setLongitud(-99.000);
        this.alcaldiaRepository.save(alc2);
        this.informationRepository.save(info2);

        List<Information> lista = this.informationRepository.findByStatusVehiculo(true).get();
        assertTrue( lista.size() > 0 );
        assertFalse( lista.isEmpty() );
    }

    @Test
    void ifIFindByAlcaldia_thenIGetAList() {
        Alcaldia alc = new Alcaldia(null, "AZCAPOTZALCO");
        Information info = new Information();
        info.setId(2);
        info.setAlcaldia( alc );
        info.setIdVehiculo(171);
        info.setStatusVehiculo(false);
        info.setLatitud(13.000);
        info.setLongitud(-99.000);

        this.alcaldiaRepository.save(alc);
        this.informationRepository.save(info);

        List<Information> lista = this.informationRepository.findByAlcaldia(alc).get();
        assertTrue( lista.size() > 0 );
    }
}