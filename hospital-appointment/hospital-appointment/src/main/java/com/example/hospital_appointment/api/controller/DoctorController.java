package com.example.hospital_appointment.api.controller;

import com.example.hospital_appointment.api.dto.DoctorRequest;
import com.example.hospital_appointment.api.dto.DoctorResponse;
import com.example.hospital_appointment.api.dto.HospitalRequest;
import com.example.hospital_appointment.application.service.interfaces.IDoctorService;
import com.example.hospital_appointment.application.service.interfaces.IPatientService;
import com.example.hospital_appointment.application.service.interfaces.ISpecializationService;
import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Specialization;
import com.example.hospital_appointment.infrastructure.mapper.DoctorMapper;
import com.example.hospital_appointment.infrastructure.mapper.HospitalMapper;
import com.example.hospital_appointment.infrastructure.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final IDoctorService doctorService;
    private final ISpecializationService specializationService;
    private final JwtUtil jwtUtil;

    public DoctorController(IDoctorService doctorService, ISpecializationService specializationService, JwtUtil jwtUtil) {
        this.doctorService = doctorService;
        this.specializationService = specializationService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody DoctorRequest doctorRequest, @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins only");
        }

        Specialization specialization = specializationService.getSpecializationById(doctorRequest.getSpecialization_id());

        Doctor doctor = DoctorMapper.toDoctor(doctorRequest, specialization);
        doctor = doctorService.save(doctor);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/getDoctorByHospital")
    public ResponseEntity<?> getDoctorByHospital(@RequestParam("hospital_id") Long hospital_id, @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN") && !jwtUtil.checkToken(token, "PATIENT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins only");
        }

        List<DoctorResponse> doctors = DoctorMapper.toDoctorResponseList(doctorService.getDoctorsByHospital(hospital_id));

        return ResponseEntity.ok(doctors);
    }

    @PutMapping("/ban")
    public ResponseEntity<?> ban(@RequestParam("doctor_id") Long doctor_id, @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins only");
        }

        String result = doctorService.Ban(doctor_id);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/unban")
    public ResponseEntity<?> unban(@RequestParam("doctor_id") Long doctor_id, @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins only");
        }

        String result = doctorService.unBan(doctor_id);

        return ResponseEntity.ok(result);
    }

}
