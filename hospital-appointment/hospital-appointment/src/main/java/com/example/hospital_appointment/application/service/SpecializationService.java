package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.application.service.interfaces.ISpecializationService;
import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Specialization;
import com.example.hospital_appointment.domain.repository.ISpecializationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecializationService implements ISpecializationService {
    @Autowired
    private ISpecializationRepo specializationRepo;

    @Override
    public Specialization addSpecialization(Specialization specialization) {
        return specializationRepo.add(specialization);
    }

    @Override
    public List<Specialization> getAllSpecialization(Long hospital_id) {
        return specializationRepo.findByHospital(hospital_id);
    }

    @Override
    public boolean existsByNameAndHospital(String name, Hospital hospital) {
        return specializationRepo.existsByNameAndHospital(name, hospital);
    }

    @Override
    public Specialization getSpecializationById(Long id) {
        return specializationRepo.findById(id);
    }
}
