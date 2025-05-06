package com.example.hospital_appointment.api.controller;

import com.example.hospital_appointment.application.service.interfaces.IPatientService;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.api.dto.RegisterRequest;
import com.example.hospital_appointment.infrastructure.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

}
