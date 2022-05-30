package com.arkon.pipeline.v1.repository;

import com.arkon.pipeline.v1.model.Information;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InformationRepositoryTest {
    @Autowired
    private InformationRepository informationRepository;

    @Test
    void ifIFindById_thenReturnsInformation(){
        Information info = new Information();
        info.setId(1);
        info.setAlcaldia("AZCAPOTZALCO");
        info.setIdVehiculo(170);
        info.setStatusVehiculo(true);
        info.setLatitud(12.000);
        info.setLongitud(-99.000);
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
        Information info = new Information();
        info.setId(1);
        info.setAlcaldia("AZCAPOTZALCO");
        info.setIdVehiculo(170);
        info.setStatusVehiculo(true);
        info.setLatitud(12.000);
        info.setLongitud(-99.000);
        this.informationRepository.save(info);

        Information info2 = new Information();
        info.setId(2);
        info.setAlcaldia("CUAUHTEMOC");
        info.setIdVehiculo(171);
        info.setStatusVehiculo(false);
        info.setLatitud(13.000);
        info.setLongitud(-99.000);
        this.informationRepository.save(info2);

        List<Information> lista = this.informationRepository.findByStatusVehiculo(true).get();
        assertTrue( lista.size() > 0 );
        assertFalse( lista.isEmpty() );
    }

    @Test
    void ifIFindByAlcaldia_thenIGetAList() {
        Information info = new Information();
        info.setId(2);
        info.setAlcaldia("CUAUHTEMOC");
        info.setIdVehiculo(171);
        info.setStatusVehiculo(false);
        info.setLatitud(13.000);
        info.setLongitud(-99.000);
        this.informationRepository.save(info);

        List<Information> lista = this.informationRepository.findByAlcaldia("CUAUHTEMOC").get();
        assertTrue( lista.size() > 0 );
    }

    @Test
    void ifIFindByAlcaldiaAndDoesntExist_thenIGetANull() {
        List<Information> lista = this.informationRepository.findByAlcaldia("CUAUHTEMOC BLANCO").get();
        assertTrue( lista.isEmpty() );
    }
}