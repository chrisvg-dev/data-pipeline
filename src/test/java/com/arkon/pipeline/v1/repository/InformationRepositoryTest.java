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
        Informacion info = this.informationRepository.findByIdVehiculo(170).get();
        Assertions.assertNotNull( info );
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
        Alcaldia alcaldia = this.alcaldiaRepository.findByName("TLALPAN").get();
        List<Informacion> lista = this.informationRepository.findByAlcaldia(alcaldia).get();
        assertTrue( lista.size() > 0 );
    }
}