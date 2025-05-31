package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface IAppointmentRepo {
    String save(Appointment appointment);
    Boolean CheckAppointment(Appointment appointment);
    Optional<Appointment> findExist(LocalDate appointmentDate, LocalTime startTime,
                                    LocalTime endTime, Long doctor_id);
    String confirmAppointment(Long id);
    String cancelAppointment(Long id);
}
