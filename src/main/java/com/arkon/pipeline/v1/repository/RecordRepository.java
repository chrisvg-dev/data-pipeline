package com.arkon.pipeline.v1.repository;

import com.arkon.pipeline.v1.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Integer> {
    Optional<Record> findByRecordId(Integer id);
}
