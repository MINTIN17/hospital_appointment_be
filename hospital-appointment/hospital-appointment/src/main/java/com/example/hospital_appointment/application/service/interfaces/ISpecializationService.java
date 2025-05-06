package com.example.hospital_appointment.application.service.interfaces;

import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Specialization;

import java.util.List;

public interface ISpecializationService {
    Specialization addSpecialization(Specialization specialization);
    List<Specialization> getAllSpecialization(Long hospital_id);
    boolean existsByNameAndHospital(String name, Hospital hospital);
    Specialization getSpecializationById(Long id);
}
