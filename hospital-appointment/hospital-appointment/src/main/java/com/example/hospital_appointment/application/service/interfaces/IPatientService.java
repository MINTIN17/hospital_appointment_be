package com.example.hospital_appointment.application.service.interfaces;

import com.example.hospital_appointment.domain.model.Patient;

import java.util.List;

public interface IPatientService {
    Patient create(Patient patient);
    List<Patient> getAllPatients();
    String Ban(Long patient_id);
    String unBan(Long patient_id);
}
