package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.application.service.interfaces.IHospitalService;
import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.repository.IHospitalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService implements IHospitalService {

    @Autowired
    private IHospitalRepo hospitalRepo;

    @Override
    public Hospital save(Hospital hospital) {
        return hospitalRepo.save(hospital);
    }

    @Override
    public Hospital getHospitalById(Long id) {
        return hospitalRepo.getHospitalById(id);
    }

    @Override
    public List<Hospital> getAllHospital() {
        return hospitalRepo.findAll();
    }
}
