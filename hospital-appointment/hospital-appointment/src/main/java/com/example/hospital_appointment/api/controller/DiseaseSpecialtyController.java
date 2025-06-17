package com.example.hospital_appointment.api.controller;

import com.example.hospital_appointment.application.service.interfaces.IDiseaseSpecialtyService;
import com.example.hospital_appointment.infrastructure.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/disease")
public class DiseaseSpecialtyController {


    private final IDiseaseSpecialtyService diseaseSpecialtyService;
    private final JwtUtil jwtUtil;

    public DiseaseSpecialtyController(IDiseaseSpecialtyService diseaseSpecialtyService, JwtUtil jwtUtil) {
        this.diseaseSpecialtyService = diseaseSpecialtyService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/getSpecialty")
    public ResponseEntity<?> getSpecialty(@RequestParam String disease, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "PATIENT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: PATIENT only");
        }

        return ResponseEntity.ok(diseaseSpecialtyService.getSpecialty(disease));
    }
}

