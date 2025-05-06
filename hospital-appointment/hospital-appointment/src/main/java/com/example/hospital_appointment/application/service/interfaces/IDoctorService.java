package com.example.hospital_appointment.application.service.interfaces;

import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Hospital;

import java.util.List;

public interface IDoctorService {
    Doctor save(Doctor doctor);
    List<Doctor> getDoctorsByHospital(Long hospital_id);
}
