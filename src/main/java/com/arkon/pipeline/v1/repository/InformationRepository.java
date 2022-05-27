package com.arkon.pipeline.v1.repository;

import com.arkon.pipeline.v1.dto.Record;
import com.arkon.pipeline.v1.model.Information;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InformationRepository extends JpaRepository<Information, Integer> {
    Optional<Information> findById(Integer id);
    Optional<Information> findByIdVehiculo(Integer idVehiculo);
    Optional<List<Information>> findByStatusVehiculo(Boolean status);
    Optional<List<Information>> findByAlcaldia(String alcaldia);
}
