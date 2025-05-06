package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Specialization;

import java.util.List;

public interface ISpecializationRepo {
    Specialization add(Specialization specialization);
    List<Specialization> findByHospital(Long hospital_id);
    Boolean existsByNameAndHospital(String name, Hospital hospital);
    Specialization findById(Long id);
}
