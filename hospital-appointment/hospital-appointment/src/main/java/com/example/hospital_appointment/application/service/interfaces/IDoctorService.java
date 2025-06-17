package com.example.hospital_appointment.application.service.interfaces;

import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Hospital;

import java.util.List;
import java.util.Optional;

public interface IDoctorService {
    Doctor save(Doctor doctor);
    List<Doctor> getDoctorsByHospital(Long hospital_id);
    Optional<Doctor> getDoctorByEmail(String email);
    String Ban(Long patient_id);
    String unBan(Long patient_id);
}
