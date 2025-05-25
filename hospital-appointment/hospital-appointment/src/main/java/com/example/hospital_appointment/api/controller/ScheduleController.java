package com.example.hospital_appointment.api.controller;

import com.example.hospital_appointment.api.dto.PatientDTO;
import com.example.hospital_appointment.api.dto.ScheduleAvailableRequest;
import com.example.hospital_appointment.api.dto.ScheduleResponse;
import com.example.hospital_appointment.application.service.interfaces.IScheduleService;
import com.example.hospital_appointment.domain.model.Schedule;
import com.example.hospital_appointment.infrastructure.mapper.PatientMapper;
import com.example.hospital_appointment.infrastructure.mapper.ScheduleMapper;
import com.example.hospital_appointment.infrastructure.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final IScheduleService scheduleService;
    private final JwtUtil jwtUtil;

    public ScheduleController(IScheduleService scheduleService, JwtUtil jwtUtil) {
        this.scheduleService = scheduleService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/getSchedule")
    public ResponseEntity<?> getSchedule(@RequestParam("doctor_id") Long doctor_id, @RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "DOCTOR") && !jwtUtil.checkToken(token, "PATIENT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins only");
        }

        List<ScheduleResponse> schedules = scheduleService.getSchedules(doctor_id)
                .stream()
                .map(ScheduleMapper::toScheduleResponse) // gọi map từng item
                .toList();

        return ResponseEntity.ok(schedules);
    }

    @PutMapping("/availability")
    public ResponseEntity<?> updateAvailability(@RequestBody List<ScheduleAvailableRequest> updates, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admins only");
        }
        scheduleService.updateAvailability(updates);
        return ResponseEntity.ok().body("Updated availability for " + updates.size() + " schedules.");
    }
}
