package com.arkon.pipeline.v1.services;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.repository.AlcaldiaRepository;
import com.arkon.pipeline.v1.repository.InformationRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(MockitoJUnitRunner.class)
public class DataServicesTest {
    @Mock
    private InformationRepository informationRepository;
    @Mock
    private AlcaldiaRepository alcaldiaRepository;

    @Test
    public void when_save_aldea_it_should_return_aldea(){
        Alcaldia esperado = new Alcaldia(1, "AZCAPOTZALCO");
        when(alcaldiaRepository.save(any(Alcaldia.class))).thenReturn(esperado);
        Alcaldia existe = this.alcaldiaRepository.save(esperado);
        Assertions.assertThat(existe.getName()).isSameAs(esperado.getName());
    }
}