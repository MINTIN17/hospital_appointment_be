package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.model.Specialty;

import java.util.List;

public interface ISpecialtyRepo {
    void saveAll(List<Specialty> specialties);
    Long count();
}
