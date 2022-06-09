package com.arkon.pipeline.v1.services;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Informacion;
import com.arkon.pipeline.v1.repository.InformationRepository;
import org.junit.Before;
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
public class InformacionServiceTest {
    @Mock
    private InformationRepository repository;
    @InjectMocks
    private InformacionService service;

    private Alcaldia a,b,c,d;
    private Informacion info1, info2, info3, info4;
    private List<Informacion> lista;

    @Before
    public void setup(){
        this.a = new Alcaldia("MX", "MEXICO");
        this.b = new Alcaldia("CUA", "CUAUHTEMOC");
        this.c = new Alcaldia("IZTA", "IZTAPALAPA");
        this.d = new Alcaldia("BN", "BENITO JUAREZ");
        this.info1 = new Informacion(1, a, 10.99,-20.99, 100, true);
        this.info2 = new Informacion(2, b, 10.99,-20.99, 100, false);
        this.info3 = new Informacion(3, c, 10.99,-20.99, 100, true);
        this.info4 = new Informacion(4, d, 10.99,-20.99, 100, false);

        this.lista = Arrays.asList(info1, info2, info3, info4);
    }

    @Test
    public void buscarTodos() {
        when(this.repository.findAll()).thenReturn(lista);
        List<Informacion> datos = this.service.buscarTodos();
        assertEquals(4,datos.size());
    }

    @Test
    public void unidadesDisponibles() {
        List<Informacion> disponibles = Arrays.asList(info1, info3);
        when(this.repository.findByStatusVehiculo(true)).thenReturn(disponibles);
        List<Informacion> datos = this.service.unidadesDisponibles();
        assertEquals(2, datos.size());
    }

    @Test
    public void buscarPorId() {
        when(repository.findByIdVehiculo(1)).thenReturn(info1);
        Informacion dato = service.buscarPorId(1);
        assertEquals(true, dato.getStatusVehiculo());
        assertNotNull(dato);
    }

    @Test
    public void buscarPorAlcaldia() {
        Informacion info1 = new Informacion(1, a, 10.99,-20.99, 100, true);
        Informacion info2 = new Informacion(2, a, 10.99,-20.99, 100, false);
        List<Informacion> lista = Arrays.asList(info1, info2);
        when(repository.findByAlcaldia(a)).thenReturn(lista);
        assertEquals(2, lista.size());
        assertNotNull(lista);
    }
}