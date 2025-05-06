package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.application.service.interfaces.IPatientService;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.infrastructure.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService implements IPatientService {
    @Autowired
    private PatientRepo patientRepository;

    @Override
    public Patient create(Patient patient) {
        patient = patientRepository.save(patient);
        return patient;
    }
}
