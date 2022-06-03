package com.arkon.pipeline.v1.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(MockitoJUnitRunner.class)
public class AlcaldiaRepositoryTest {
    @Mock
    private AlcaldiaRepository alcaldiaRepository;

    @Test
    public void saveAlcaldiaTest() {
        /**Alcaldia nuevaAlcaldia = new Alcaldia();
        nuevaAlcaldia.setName("ALCALDIA1");
        Alcaldia dbAlcaldia = alcaldiaRepository.save(nuevaAlcaldia);
        Assertions.assertThat( dbAlcaldia.getId() ).isGreaterThan(0);
        Assertions.assertThat( dbAlcaldia ).isNotNull();*/
    }

    @Test
    public void buscarAlcaldiaPorNombre_devuelveObjeto() {
        /**Alcaldia nuevaAlcaldia = new Alcaldia();
        nuevaAlcaldia.setName("ALCALDIA1");
        alcaldiaRepository.save(nuevaAlcaldia);

        Alcaldia dbAlcaldia = alcaldiaRepository.findByName("ALCALDIA1");
        Assertions.assertThat( dbAlcaldia.getId() ).isGreaterThan(0);
        Assertions.assertThat( dbAlcaldia ).isNotNull();*/
    }

    @Test
    public void buscarAlcaldiaPorNombreNoExistente_devuelveNull() {
        /**Alcaldia dbAlcaldia = alcaldiaRepository.findByName("ALCALDIA1");
        org.junit.jupiter.api.Assertions.assertNull(dbAlcaldia);*/
    }

    @Test
    public void buscarTodasLasAlcaldias_DevuelveUnaLista() {
        /**Alcaldia nuevaAlcaldia = new Alcaldia();
        nuevaAlcaldia.setName("ALCALDIA1");
        alcaldiaRepository.save(nuevaAlcaldia);
        nuevaAlcaldia = new Alcaldia();
        nuevaAlcaldia.setName("ALCALDIA2");
        alcaldiaRepository.save(nuevaAlcaldia);
        Assertions.assertThat(alcaldiaRepository.findAll().size()).isGreaterThan(0); */
    }
}