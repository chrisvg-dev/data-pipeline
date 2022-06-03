package com.arkon.pipeline.v1.services;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.repository.AlcaldiaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AlcaldiaServiceTest {
    @Mock
    private AlcaldiaRepository alcaldiaRepository;
    @InjectMocks
    private AlcaldiaService alcaldiaService;

    private List<Alcaldia> alcaldias;

    @Test
    public void contextLoads(){
        Alcaldia a = new Alcaldia(1, "MEXICO");
        Alcaldia d = new Alcaldia(4, "BENITO JUAREZ");
        when(alcaldiaRepository.findByName("BENITO JUAREZ")).thenReturn(d);
        when(alcaldiaRepository.findByName("MEXICO")).thenReturn(a);
        Alcaldia alc = this.alcaldiaService.buscarPorNombre("MEXICO");
        Alcaldia alc2 = this.alcaldiaService.buscarPorNombre("BENITO JUAREZ");
        assertEquals("MEXICO", alc.getName());
        assertNotNull(alc2);
    }

    @Test
    public void findAll_returnList(){
        Alcaldia a = new Alcaldia(1, "MEXICO");
        Alcaldia b = new Alcaldia(2, "CUAUHTEMOC");
        Alcaldia c = new Alcaldia(3, "IZTAPALAPA");
        Alcaldia d = new Alcaldia(4, "BENITO JUAREZ");
        this.alcaldias = Arrays.asList( a,b,c,d );
        when(alcaldiaRepository.findAll()).thenReturn(this.alcaldias);
        List<Alcaldia> lista = this.alcaldiaService.buscarTodas();
        assertFalse(lista.isEmpty());
        assertEquals(lista.size(), 4);
    }
}