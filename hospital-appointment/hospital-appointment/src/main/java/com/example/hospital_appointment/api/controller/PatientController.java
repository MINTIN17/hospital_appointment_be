package com.example.hospital_appointment.api.controller;

import com.example.hospital_appointment.api.dto.DoctorResponse;
import com.example.hospital_appointment.api.dto.PatientDTO;
import com.example.hospital_appointment.application.service.interfaces.IPatientService;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.api.dto.RegisterRequest;
import com.example.hospital_appointment.infrastructure.mapper.DoctorMapper;
import com.example.hospital_appointment.infrastructure.mapper.PatientMapper;
import com.example.hospital_appointment.infrastructure.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patient")
public class PatientController {


    private final IPatientService patientService;
    private final JwtUtil jwtUtil;

    public PatientController(IPatientService patientService, JwtUtil jwtUtil) {
        this.patientService = patientService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/getAllPatient")
    public ResponseEntity<?> getAllPatient(@RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins only");
        }

        List<PatientDTO> patients = patientService.getAllPatients()
                .stream()
                .map(PatientMapper::toDTO) // gọi map từng item
                .toList();

        return ResponseEntity.ok(patients);
    }

    @PutMapping("/ban")
    public ResponseEntity<?> ban(@RequestParam("patient_id") Long patient_id, @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins only");
        }

        String result = patientService.Ban(patient_id);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/unban")
    public ResponseEntity<?> unban(@RequestParam("patient_id") Long patient_id, @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins only");
        }

        String result = patientService.unBan(patient_id);

        return ResponseEntity.ok(result);
    }

}
