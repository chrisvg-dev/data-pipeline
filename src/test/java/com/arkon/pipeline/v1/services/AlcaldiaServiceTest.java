package com.arkon.pipeline.v1.services;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.repository.AlcaldiaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AlcaldiaServiceTest {
    private AlcaldiaRepository alcaldiaRepository;
    private AlcaldiaService alcaldiaService;

    private List<Alcaldia> alcaldias;

    @BeforeEach
    public void setup(){
        this.alcaldiaRepository = Mockito.mock( AlcaldiaRepository.class );
        this.alcaldiaService = new AlcaldiaService(this.alcaldiaRepository);
        Alcaldia a = new Alcaldia(1, "MEXICO");
        Alcaldia b = new Alcaldia(2, "CUAUHTEMOC");
        Alcaldia c = new Alcaldia(3, "IZTAPALAPA");
        Alcaldia d = new Alcaldia(4, "BENITO JUAREZ");
        this.alcaldias = Arrays.asList( a,b,c,d );
    }

    @Test
    public void contextLoads(){
        Alcaldia a = new Alcaldia(1, "MEXICO");
        Alcaldia d = new Alcaldia(4, "BENITO JUAREZ");
        when(alcaldiaRepository.findByName("BENITO JUAREZ")).thenReturn(d);
        when(alcaldiaRepository.findByName("MEXICO")).thenReturn(a);

        Alcaldia alc = this.alcaldiaService.buscarPorNombre("MEXICO");
        assertEquals("MEXICO", alc.getName());
    }
}