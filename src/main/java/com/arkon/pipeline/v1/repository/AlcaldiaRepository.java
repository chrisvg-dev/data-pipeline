package com.arkon.pipeline.v1.repository;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.google.common.util.concurrent.ListenableFuture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

public interface AlcaldiaRepository extends JpaRepository<Alcaldia, Integer> {
    /**
     * Permite buscar una Alcaldia mediante el nombre, retorna un Opcional
     *
     * @param alcaldia
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    Alcaldia findByName(String alcaldia);

    /**
     * Permite buscar una Alcaldia mediante el nombre, si existe retorna true, caso contrario retorna false
     *
     * @param alcaldia
     * @return
     */
    @Async
    ListenableFuture<Boolean> existsByName(String alcaldia);
}