package com.arkon.pipeline.v1.services;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.repository.AlcaldiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio que permite el acceso a la capa de datos de la entidad Alcaldia, aquí se procesa toda la información relacionada
 * con las alcaldias, su búsqueda por filtros, etc.
 */
@Service
@Transactional
public class AlcaldiaService {
    private final AlcaldiaRepository alcaldiaRepository;

    public AlcaldiaService(AlcaldiaRepository alcaldiaRepository) {
        this.alcaldiaRepository = alcaldiaRepository;
    }

    /**
     * Permite listar las alcaldias disponibles en la base de datos
     * @return
     */
    public List<Alcaldia> buscarTodas(){
        return this.alcaldiaRepository.findAll();
    }

    /**
     * Permite buscar una alcaldia solamente ingresando su nombre, lo cual devuelve un objeto de tipo Alcaldia
     * @param nombre
     * @return
     */
    public Alcaldia buscarPorNombre(String nombre) {
        return this.alcaldiaRepository.findByName(nombre);
    }
}
