package com.arkon.pipeline.v1.repository;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Informacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
/**
 * Utilizando la interface JPA se realiza el mapeo de la base de datos mediante clases Java,
 * lo cual facilita el manejo de la información
 */
@Repository
public interface InformationRepository extends JpaRepository<Informacion, Integer> {
    /**
     * Busca un registro por id y retorna un opcional para validar objetos nulos.
     * @param id
     * @return
     */
    Optional<Informacion> findById(Integer id);
    /**
     * Busca un registro por id y retorna un opcional para validar objetos nulos.
     * @param idVehiculo
     * @return
     */
    Informacion findByIdVehiculo(Integer idVehiculo);
    /**
     * Busca la lista de información teniendo en cuenta que solo debe traerlos si están disponibles
     * @param status
     * @return
     */
    List<Informacion> findByStatusVehiculo(Boolean status);
    /**
     * Busca una lista de unidades pertenecientes a una alcaldia pasada como parámetro
     * @param alcaldia
     * @return
     */
    List<Informacion> findByAlcaldia(Alcaldia alcaldia);

    @Query("SELECT count(i) FROM Informacion i")
    Integer countAll();
}
