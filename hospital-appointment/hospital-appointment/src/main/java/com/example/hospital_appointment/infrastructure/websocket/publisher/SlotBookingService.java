package com.example.hospital_appointment.infrastructure.websocket.publisher;

import com.example.hospital_appointment.api.dto.AppointmentRequest;
import com.example.hospital_appointment.application.exception.SlotAlreadyBookedException;
import com.example.hospital_appointment.application.service.AppointmentService;
import com.example.hospital_appointment.domain.model.Appointment;
import com.example.hospital_appointment.domain.repository.IAppointmentRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SlotBookingService {

    private final SimpMessagingTemplate messagingTemplate;
    private final IAppointmentRepo appointmentRepo;
    private final AppointmentService appointmentService;

    @Transactional
    public String bookSlot(AppointmentRequest appointmentRequest) {
        // Check slot
        Optional<Appointment> optional = appointmentRepo.findExist(appointmentRequest.getAppointmentDate(), appointmentRequest.getStartTime(),
                                                                    appointmentRequest.getEndTime(), appointmentRequest.getDoctor_id());
        if (optional.isPresent()) {
            throw new SlotAlreadyBookedException();
        }

        // Gửi thông báo real-time
        Map<String, Object> payload = new HashMap<>();
        payload.put("doctorId", appointmentRequest.getDoctor_id());
        payload.put("date", appointmentRequest.getAppointmentDate());
        payload.put("startTime", appointmentRequest.getStartTime());
        payload.put("patientId", appointmentRequest.getPatient_id());
        messagingTemplate.convertAndSend("/topic/slot-booked", payload);
        return appointmentService.BookAppointment(appointmentRequest);
    }
}
