package com.example.hospital_appointment.api.controller;

import com.example.hospital_appointment.api.dto.HospitalRequest;
import com.example.hospital_appointment.api.dto.HospitalResponse;
import com.example.hospital_appointment.api.dto.RegisterRequest;
import com.example.hospital_appointment.application.service.interfaces.IHospitalService;
import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.infrastructure.mapper.HospitalMapper;
import com.example.hospital_appointment.infrastructure.mapper.PatientMapper;
import com.example.hospital_appointment.infrastructure.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/hospital")
public class HospitalController {

    private final IHospitalService hospitalService;

    private final JwtUtil jwtUtil;

    public HospitalController(IHospitalService hospitalService, JwtUtil jwtUtil) {
        this.hospitalService = hospitalService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody HospitalRequest hospitalRequest, @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins only");
        }

        Hospital hospital = HospitalMapper.toHospital(hospitalRequest);
        hospital = hospitalService.save(hospital);
        return ResponseEntity.ok(hospital);
    }

    @GetMapping("/getAllHospital")
    public ResponseEntity<?> getAllHospital(@RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN") && !jwtUtil.checkToken(token, "PATIENT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins or Patients only");
        }


        List<HospitalResponse> hospitals = hospitalService.getAllHospital();
        return ResponseEntity.ok(hospitals);
    }

}
