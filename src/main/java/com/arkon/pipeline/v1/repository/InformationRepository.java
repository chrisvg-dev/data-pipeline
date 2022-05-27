package com.arkon.pipeline.v1.repository;

import com.arkon.pipeline.v1.dto.Record;
import com.arkon.pipeline.v1.model.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Utilizando la interface JPA se realiza el mapeo de la base de datos mediante clases Java,
 * lo cual facilita el manejo de la información
 */
@Repository
public interface InformationRepository extends JpaRepository<Information, Integer> {
    /**
     * Busca un registro por id y retorna un opcional para validar objetos nulos.
     * @param id
     * @return
     */
    Optional<Information> findById(Integer id);
    /**
     * Busca un registro por id y retorna un opcional para validar objetos nulos.
     * @param idVehiculo
     * @return
     */
    Optional<Information> findByIdVehiculo(Integer idVehiculo);

    /**
     * Busca la lista de información teniendo en cuenta que solo debe traerlos si están disponibles
     * @param status
     * @return
     */
    Optional<List<Information>> findByStatusVehiculo(Boolean status);

    /**
     * Busca una lista de unidades pertenecientes a una alcaldia pasada como parámetro
     * @param alcaldia
     * @return
     */
    Optional<List<Information>> findByAlcaldia(String alcaldia);
}
