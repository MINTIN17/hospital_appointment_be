package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.model.Disease;

import java.util.List;
import java.util.Optional;

public interface IDiseaseRepo {
    void saveAll(List<Disease> diseases);
    Long count();
    Optional<Disease> findByName(String name);
}
