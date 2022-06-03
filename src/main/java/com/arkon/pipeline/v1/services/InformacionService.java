package com.arkon.pipeline.v1.services;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Informacion;
import com.arkon.pipeline.v1.repository.InformationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * Servicio que agrupa todas las funcionalidades que tienen relación con la entidad Información, cuyos datos provienen
 * de origenes remotos.
 */
@Service
@Transactional
public class InformacionService {
    private final InformationRepository informationRepository;
    private final AlcaldiaService alcaldiaService;

    public InformacionService(InformationRepository informationRepository, AlcaldiaService alcaldiaService) {
        this.informationRepository = informationRepository;
        this.alcaldiaService = alcaldiaService;
    }
    /**
     * Permite listar la información utilizando la interface JPA
     * @return
     */
    public List<Informacion> buscarTodos(){
        return this.informationRepository.findAll();
    }
    /**
     * Permite listar las unidades registradas en la base de datos siempre y cuando tengan el status en disponible
     * @return
     */
    public List<Informacion> unidadesDisponibles() {
        return this.informationRepository.findByStatusVehiculo(true);
    }
    /**
     * Permite obtener un registro de la base de datos que coincida con el ID proporcionado por el usuario mediante
     * la api de Graphql
     * @param idVehiculo
     * @return
     */
    public Informacion buscarPorId(Integer idVehiculo) {
        return this.informationRepository.findByIdVehiculo(idVehiculo);
    }
    /**
     * Permite obtener un todos los registros de la base de datos que coincidan con
     * el nombre de la alcaldia proporcionado por el usuario mediante la api de graphql
     * @param alcaldia
     * @return
     */
    public List<Informacion> buscarPorAlcaldia(String alcaldia){
        Alcaldia alc = this.alcaldiaService.buscarPorNombre(alcaldia.toUpperCase());
        return this.informationRepository.findByAlcaldia(alc);
    }
}
