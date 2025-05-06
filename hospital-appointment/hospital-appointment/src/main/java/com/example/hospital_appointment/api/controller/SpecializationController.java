package com.example.hospital_appointment.api.controller;

import com.example.hospital_appointment.api.dto.RegisterRequest;
import com.example.hospital_appointment.api.dto.SpecializationRequest;
import com.example.hospital_appointment.api.dto.SpecializationResponse;
import com.example.hospital_appointment.application.service.interfaces.IAuthService;
import com.example.hospital_appointment.application.service.interfaces.IHospitalService;
import com.example.hospital_appointment.application.service.interfaces.ISpecializationService;
import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.model.Specialization;
import com.example.hospital_appointment.infrastructure.mapper.PatientMapper;
import com.example.hospital_appointment.infrastructure.mapper.SpecializationMapper;
import com.example.hospital_appointment.infrastructure.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialization")
public class SpecializationController {
    private final ISpecializationService specializationService;
    private final IHospitalService hospitalService;
    private final JwtUtil jwtUtil;

    public SpecializationController(ISpecializationService specializationService, IHospitalService hospitalService, JwtUtil jwtUtil) {
        this.specializationService = specializationService;
        this.hospitalService = hospitalService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody SpecializationRequest specializationRequest, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins only");
        }

        Hospital hospital = hospitalService.getHospitalById(specializationRequest.getHospital_id());
        boolean exists = specializationService.existsByNameAndHospital(
                specializationRequest.getName(),
                hospital
        );
        System.out.println(exists);

        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Specialization already exists");
        }
        Specialization specialization = SpecializationMapper.toSpecialization(specializationRequest,hospital);
        specialization = specializationService.addSpecialization(specialization);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String authHeader,@RequestParam("hospital_id") Long hospital_id) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins only");
        }

        List<SpecializationResponse> responseList = SpecializationMapper.toSpecializationResponseList(specializationService.getAllSpecialization(hospital_id));
        return ResponseEntity.ok(responseList);
    }

}
