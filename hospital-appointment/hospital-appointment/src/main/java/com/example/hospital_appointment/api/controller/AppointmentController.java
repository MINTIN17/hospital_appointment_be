package com.example.hospital_appointment.api.controller;

import com.example.hospital_appointment.api.dto.AppointmentRequest;

import com.example.hospital_appointment.application.service.AppointmentService;
import com.example.hospital_appointment.application.service.interfaces.IAppointmentService;
import com.example.hospital_appointment.domain.Enums.AppointmentStatus;
import com.example.hospital_appointment.domain.model.Appointment;
import com.example.hospital_appointment.infrastructure.security.JwtUtil;
import com.example.hospital_appointment.infrastructure.websocket.publisher.SlotBookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final JwtUtil jwtUtil;
    private final IAppointmentService appointmentService;
    private final SlotBookingService slotBookingService;

    public AppointmentController(JwtUtil jwtUtil, AppointmentService appointmentService, SlotBookingService slotBookingService) {
        this.jwtUtil = jwtUtil;
        this.appointmentService = appointmentService;
        this.slotBookingService = slotBookingService;
    }

    @PostMapping("/createAppointment")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentRequest appointmentRequest, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "PATIENT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: PATIENT only");
        }

        return ResponseEntity.ok(slotBookingService.bookSlot(appointmentRequest));
    }

    @GetMapping("/getAllAppointment")
    public ResponseEntity<?> getAllAppointment(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admin only");
        }

        return ResponseEntity.ok(appointmentService.getAllAppointment());
    }

    @GetMapping("/getDoctorAppointment")
    public ResponseEntity<?> getDoctorAppointment(@RequestParam("doctor_id") Long doctor_id, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: DOCTOR only");
        }

        return ResponseEntity.ok(appointmentService.getDoctorAppointment(doctor_id));
    }

    @GetMapping("/getPatientAppointment")
    public ResponseEntity<?> getPatientAppointment(@RequestParam("patient_id") Long patient_id, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "PATIENT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: PATIENT only");
        }

        return ResponseEntity.ok(appointmentService.getPatientAppointment(patient_id));
    }

    @PutMapping("/confirmAppointment")
    public ResponseEntity<String> confirmAppointment(@RequestParam("appointment_id") Long appointment_id, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: doctor only");
        }
        return ResponseEntity.ok(appointmentService.confirmAppointment(appointment_id));
    }

    @PutMapping("/cancelAppointment")
    public ResponseEntity<String> cancelAppointment(@RequestParam("appointment_id") Long appointment_id, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "DOCTOR") && !jwtUtil.checkToken(token, "PATIENT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: patient and doctor only");
        }
        return ResponseEntity.ok(appointmentService.cancelAppointment(appointment_id));
    }

    @PutMapping("/completeAppointment")
    public ResponseEntity<String> completeAppointment(@RequestParam("appointment_id") Long appointment_id, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: doctor and doctor only");
        }
        return ResponseEntity.ok(appointmentService.completeAppointment(appointment_id));
    }

    @GetMapping("/getCurrentAppointment")
    public ResponseEntity<?> getCurrentAppointment(@RequestParam("doctor_id") Long doctor_id, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: doctor and doctor only");
        }
        return ResponseEntity.ok(appointmentService.getCurrentAppointment(doctor_id));
    }

    @GetMapping("/getCountAppointment")
    public ResponseEntity<?> getCountAppointment(@RequestHeader("Authorization") String authHeader,
                                                 @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                 @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admin and doctor only");
        }
        Map<AppointmentStatus, Long> statusCounts = appointmentService.countAppointmentsByStatus(startDate, endDate);
        Map<String, Long> response = new HashMap<>();

        for (Map.Entry<AppointmentStatus, Long> entry : statusCounts.entrySet()) {
            response.put(entry.getKey().name(), entry.getValue());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getCountAppointmentHospital")
    public ResponseEntity<?> getCountAppointmentHospital(@RequestHeader("Authorization") String authHeader,
                                                 @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                 @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admin and doctor only");
        }

        return ResponseEntity.ok(appointmentService.countAppointmentsByHospital(startDate, endDate));
    }

    @GetMapping("/getCountAppointmentDoctor")
    public ResponseEntity<?> getCountAppointmentDoctor(@RequestHeader("Authorization") String authHeader,
                                                         @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                         @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.checkToken(token, "ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Admin and doctor only");
        }

        return ResponseEntity.ok(appointmentService.countAppointmentsByDoctor(startDate, endDate));
    }

    @GetMapping("/revisit-rate")
    public ResponseEntity<?> getRevisitRate(@RequestParam(defaultValue = "120") int days) {
        double rate = appointmentService.calculateRevisitRate(days);
        Map<String, Object> response = new HashMap<>();
        response.put("daysWindow", days);
        response.put("revisitRate", String.format("%.2f%%", rate));
        return ResponseEntity.ok(response);
    }
}
