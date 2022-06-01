package com.arkon.pipeline.v1.repository;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Informacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    private Informacion info, info2;
    private Alcaldia alcaldia, alcaldia2;

    @BeforeEach
    public void setup() {
        this.alcaldia = new Alcaldia(1, "AZCAPOTZALCO");
        this.alcaldiaRepository.save(alcaldia);
        this.info = new Informacion();
        info.setId(500);
        info.setAlcaldia(alcaldia);
        info.setIdVehiculo(170);
        info.setStatusVehiculo(true);
        info.setLatitud(12.000);
        info.setLongitud(-99.000);
        this.informationRepository.save( this.info );

        this.alcaldia2 = new Alcaldia(null, "AZCAPOTZALCO");
        Informacion info2 = new Informacion();
        info2.setId(2);
        info2.setAlcaldia(alcaldia2);
        info2.setIdVehiculo(171);
        info2.setStatusVehiculo(false);
        info2.setLatitud(13.000);
        info2.setLongitud(-99.000);
        this.alcaldiaRepository.save(alcaldia2);
        this.informationRepository.save(info2);
    }

    @Test
    void ifIFindById_thenReturnsInformation(){
        Assertions.assertNotNull( this.informationRepository.findById(1) );
    }

    @Test
    void zero_negatives_thenReturnsNull(){
        Assertions.assertNull( this.informationRepository.findById(0).orElse(null) );
        Assertions.assertNull( this.informationRepository.findById(-1).orElse(null) );
    }

    @Test
    void ifIFindByIdVehicle_thenReturnsInformation(){
        Assertions.assertNotNull( this.informationRepository.findByIdVehiculo(170).get());
    }

    @Test
    void ifIdDoesntExists_thenReturnNull(){
        Assertions.assertNull( this.informationRepository.findByIdVehiculo(-1).orElse(null) );
    }

    @Test
    void ifIFindByStatus_thenIGetAList() {
        List<Informacion> lista = this.informationRepository.findByStatusVehiculo(true).get();
        assertTrue( lista.size() > 0 );
        assertFalse( lista.isEmpty() );
    }

    @Test
    void ifIFindByAlcaldia_thenIGetAList() {
        List<Informacion> lista = this.informationRepository.findByAlcaldia(this.alcaldia).get();
        assertTrue( lista.size() > 0 );
    }
}