package com.example.hospital_appointment.infrastructure.repository.jpa;

import com.example.hospital_appointment.domain.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface JpaAppointment extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByAppointmentDateAndStartTimeAndEndTimeAndDoctor_Id(LocalDate appointmentDate, LocalTime startTime,
                                                                                  LocalTime endTime, Long doctorId);
}
