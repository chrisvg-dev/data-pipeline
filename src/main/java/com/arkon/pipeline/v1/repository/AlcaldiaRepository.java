package com.arkon.pipeline.v1.repository;

import com.arkon.pipeline.v1.model.Alcaldia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlcaldiaRepository extends JpaRepository<Alcaldia, Integer> {
    /**
     * Permite buscar una Alcaldia mediante el nombre, retorna un Opcional
     * @param alcaldia
     * @return
     */
    Optional<Alcaldia> findByName(String alcaldia);
    /**
     * Permite buscar una Alcaldia mediante el nombre, si existe retorna true, caso contrario retorna false
     * @param alcaldia
     * @return
     */
    boolean existsByName(String alcaldia);
}
