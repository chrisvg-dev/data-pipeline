package com.arkon.pipeline.v1.repository;

import com.arkon.pipeline.v1.model.Alcaldia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlcaldiaRepository extends JpaRepository<Alcaldia, Integer> {
    Optional<Alcaldia> findByName(String alcaldia);
    boolean existsByName(String alcaldia);
}
