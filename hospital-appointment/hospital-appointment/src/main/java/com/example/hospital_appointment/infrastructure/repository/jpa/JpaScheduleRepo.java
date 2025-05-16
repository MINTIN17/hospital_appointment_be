package com.example.hospital_appointment.infrastructure.repository.jpa;

import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaScheduleRepo extends JpaRepository<Schedule, Long> {
    List<Schedule> findByDoctorId(Long doctorId);
}
