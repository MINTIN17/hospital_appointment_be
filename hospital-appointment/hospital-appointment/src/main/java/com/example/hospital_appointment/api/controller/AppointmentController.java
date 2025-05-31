package com.example.hospital_appointment.api.controller;

import com.example.hospital_appointment.api.dto.AppointmentRequest;

import com.example.hospital_appointment.application.service.AppointmentService;
import com.example.hospital_appointment.domain.model.Appointment;
import com.example.hospital_appointment.infrastructure.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final JwtUtil jwtUtil;
    private final AppointmentService appointmentService;

    public AppointmentController(JwtUtil jwtUtil, AppointmentService appointmentService) {
        this.jwtUtil = jwtUtil;
        this.appointmentService = appointmentService;
    }

    @PostMapping("/createAppointment")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentRequest appointmentRequest, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "PATIENT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins only");
        }
        return ResponseEntity.ok(appointmentService.BookAppointment(appointmentRequest));
    }
}
