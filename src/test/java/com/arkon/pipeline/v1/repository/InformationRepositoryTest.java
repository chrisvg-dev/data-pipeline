package com.arkon.pipeline.v1.repository;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Informacion;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InformationRepositoryTest {
    @Mock
    private InformationRepository informationRepository;
    @Mock
    private AlcaldiaRepository alcaldiaRepository;

    private Alcaldia a,b,c,d;
    private Informacion info1, info2, info3, info4;
    private List<Informacion> lista;

    @Before
    public void setup(){
        this.a = new Alcaldia(1, "MEXICO");
        this.c = new Alcaldia(3, "IZTAPALAPA");
        this.d = new Alcaldia(4, "BENITO JUAREZ");
        this.info1 = new Informacion(1, a, 10.99,-20.99, 100, true);
        this.info2 = new Informacion(2, a, 10.99,-20.99, 100, false);
        this.info3 = new Informacion(3, c, 10.99,-20.99, 100, true);
        this.info4 = new Informacion(4, d, 10.99,-20.99, 100, false);

        this.lista = Arrays.asList(info1, info2, info3, info4);
    }

    @Test
    public void ifIFindById_thenReturnsInformation(){
        //Assertions.assertNotNull( this.informationRepository.findById(1) );
    }

    @Test
    public void zero_negatives_thenReturnsNull(){
        //Assertions.assertNull( this.informationRepository.findById(0).orElse(null) );
        //Assertions.assertNull( this.informationRepository.findById(-1).orElse(null) );
    }

    @Test
    public void ifIFindByIdVehicle_thenReturnsInformation(){
        //Informacion info = this.informationRepository.findByIdVehiculo(170);
        //Assertions.assertNotNull( info );
    }

    @Test
    public void ifIdDoesntExists_thenReturnNull(){
        //Assertions.assertNull( this.informationRepository.findByIdVehiculo(-1));
    }

    @Test
    public void ifIFindByStatus_thenIGetAList() {
        //List<Informacion> lista = this.informationRepository.findByStatusVehiculo(true);
        //assertTrue( lista.size() > 0 );
        //assertFalse( lista.isEmpty() );
    }

    @Test
    public void ifIFindByAlcaldia_thenIGetAList() {
        List<Informacion> lista = Arrays.asList(info1, info2);
        when(this.informationRepository.findByAlcaldia(a)).thenReturn(lista);
        List<Informacion> result = this.informationRepository.findByAlcaldia( this.a );
        assertTrue( lista.size() > 0 );
    }
}