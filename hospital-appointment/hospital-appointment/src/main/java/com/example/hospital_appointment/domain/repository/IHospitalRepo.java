package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.model.Hospital;

import java.util.List;

public interface IHospitalRepo {
    long count();
    Hospital save(Hospital hospital);
    Hospital getHospitalById(Long id);
    List<Hospital> findAll();

}
