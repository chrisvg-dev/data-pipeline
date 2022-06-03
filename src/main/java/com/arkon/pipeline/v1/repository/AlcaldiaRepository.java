package com.arkon.pipeline.v1.repository;

import com.arkon.pipeline.v1.model.Alcaldia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public interface AlcaldiaRepository extends JpaRepository<Alcaldia, Integer> {
    /**
     * Permite buscar una Alcaldia mediante el nombre, retorna un Opcional
     * @param alcaldia
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    Alcaldia findByName(String alcaldia);
    /**
     * Permite buscar una Alcaldia mediante el nombre, si existe retorna true, caso contrario retorna false
     * @param alcaldia
     * @return
     */
    boolean existsByName(String alcaldia);
}
