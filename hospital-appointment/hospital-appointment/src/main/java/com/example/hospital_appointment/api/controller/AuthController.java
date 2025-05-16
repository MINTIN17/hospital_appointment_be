package com.example.hospital_appointment.api.controller;

import com.example.hospital_appointment.api.dto.*;
import com.example.hospital_appointment.application.service.interfaces.IAuthService;
import com.example.hospital_appointment.application.service.interfaces.IPatientService;
import com.example.hospital_appointment.application.service.interfaces.IUserService;
import com.example.hospital_appointment.domain.Enums.Role;
import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.model.User;
import com.example.hospital_appointment.infrastructure.mapper.DoctorMapper;
import com.example.hospital_appointment.infrastructure.mapper.PatientMapper;
import com.example.hospital_appointment.infrastructure.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final IPatientService patientService;
    private final IAuthService authService;
    private final IUserService userService;

    public AuthController(JwtUtil jwtUtil, IPatientService patientService, IAuthService authService, IUserService userService) {
        this.jwtUtil = jwtUtil;
        this.patientService = patientService;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Patient> create(@RequestBody RegisterRequest patientRegister) {
        Patient patient = PatientMapper.toPatient(patientRegister);
        patient = authService.register(patient);
        return ResponseEntity.ok(patient);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Optional<User> userOpt = authService.CheckAdmin(request.getEmail(), request.getPassword());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                String token = jwtUtil.generateToken(request.getEmail(), user.getRole());

                Map<String, String> response = new HashMap<>();
                response.put("token", token);

                return ResponseEntity.ok(response);
            }

            String token = authService.login(request.getEmail(), request.getPassword());
            User user = userService.findByEmail(request.getEmail());
            if (user.getRole() == Role.PATIENT) {
                Patient patient = authService.findByPatientEmail(request.getEmail())
                        .orElseThrow(() -> new RuntimeException("Patient not found"));
                PatientDTO patientDTO = PatientMapper.toDTO(patient);
                return ResponseEntity.ok(new LoginPatientResponse(token, patientDTO));
            }
            Doctor doctor = authService.findByDoctorEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            DoctorResponse doctorResponse = DoctorMapper.toDoctorResponse(doctor);
            return ResponseEntity.ok(new LoginDoctorResponse(token, doctorResponse));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
