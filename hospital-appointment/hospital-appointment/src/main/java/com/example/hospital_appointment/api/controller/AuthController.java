package com.example.hospital_appointment.api.controller;

import com.example.hospital_appointment.api.dto.*;
import com.example.hospital_appointment.application.service.interfaces.IAuthService;
import com.example.hospital_appointment.application.service.interfaces.IPasswordResetOtpService;
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
import org.springframework.web.bind.annotation.*;

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
    private final IPasswordResetOtpService passwordResetOtpService;

    public AuthController(JwtUtil jwtUtil, IPatientService patientService, IAuthService authService, IUserService userService, IPasswordResetOtpService passwordResetOtpService) {
        this.jwtUtil = jwtUtil;
        this.patientService = patientService;
        this.authService = authService;
        this.userService = userService;
        this.passwordResetOtpService = passwordResetOtpService;
    }

    @PostMapping("/register")
    public ResponseEntity<Patient> create(@RequestBody RegisterRequest patientRegister) {
        Patient patient = PatientMapper.toPatient(patientRegister);
        patient = authService.register(patient);
        return ResponseEntity.ok(patient);
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestParam String email ) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.ok("Email not exist");
        }
        return ResponseEntity.ok("Email exist");
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

    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String authHeader, @RequestBody ChangePasswordRequest request) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "PATIENT") && !jwtUtil.checkToken(token, "DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: patient and doctor only");
        }
        return ResponseEntity.ok(authService.changePassword(request));
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email) {

        return ResponseEntity.ok(passwordResetOtpService.sendOtp(email));
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ResetPasswordRequest request, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        System.out.println(token);
        if (!jwtUtil.checkToken(token, "OTP_VERIFIED")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        return ResponseEntity.ok(authService.forgotPassword(request));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = passwordResetOtpService.verifyOtp(email, otp);
        if (isValid) {
            return ResponseEntity.ok(jwtUtil.generateTemporaryToken(email));
        } else {
            return ResponseEntity.ok("Invalid OTP");
        }
    }

}
