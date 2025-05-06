package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.model.Doctor;

import java.util.List;

public interface IDoctorRepo {
    Doctor save(Doctor doctor);
    List<Doctor> findByUser_Hospital_Id(Long hospitalId);

}
