package com.example.hospital_appointment.application.service.interfaces;

import com.example.hospital_appointment.domain.model.Hospital;

import java.util.List;

public interface IHospitalService {
    Hospital save(Hospital hospital);
    Hospital getHospitalById(Long id);
    List<Hospital> getAllHospital();
}
