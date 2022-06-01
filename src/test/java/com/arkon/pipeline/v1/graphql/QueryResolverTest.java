package com.arkon.pipeline.v1.graphql;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Informacion;
import com.arkon.pipeline.v1.repository.AlcaldiaRepository;
import com.arkon.pipeline.v1.repository.InformationRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(MockitoJUnitRunner.class)
public class QueryResolverTest {
    @Mock
    private InformationRepository informationRepository;
    @Mock
    private AlcaldiaRepository alcaldiaRepository;
    private QueryResolver queryResolver;

    private Informacion info, info2;
    private Alcaldia alcaldia, alcaldia2;

    private List<Informacion> lista;

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
        info2.setStatusVehiculo(true);
        info2.setLatitud(13.000);
        info2.setLongitud(-99.000);
        this.alcaldiaRepository.save(alcaldia2);
        this.informationRepository.save(info2);
    }

    @DisplayName("JUnit test for get all")
    @Test
    public void givenInformationList_whenGetAllInformation_thenReturnInformationList(){
        List<Informacion> lista = new ArrayList();
        lista.add(info); lista.add(info2);
        given(informationRepository.findAll()).willReturn(lista);
        List<Informacion> informacionList = this.queryResolver.records();
        assertThat(informacionList).isNotNull();
        assertThat(informacionList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for get all")
    @Test
    public void givenInformationList_whenGetAvailableInformation_thenReturnInformationList(){
        List<Informacion> lista = new ArrayList();
        lista.add(info); lista.add(info2);
        assertNotEquals(null, Optional.of(this.informationRepository.findByStatusVehiculo(true)));
        assertNotEquals(null, this.queryResolver.unidadesDisponibles());
        assertTrue( this.queryResolver.unidadesDisponibles().isEmpty() );
    }
}