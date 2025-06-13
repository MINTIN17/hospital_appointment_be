package com.example.hospital_appointment.domain.repository;

import com.example.hospital_appointment.domain.Enums.AppointmentStatus;
import com.example.hospital_appointment.domain.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface IAppointmentRepo {
    String save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    Boolean CheckAppointment(Appointment appointment);
    Optional<Appointment> findExist(LocalDate appointmentDate, LocalTime startTime,
                                    LocalTime endTime, Long doctor_id);
    List<Appointment> getAllAppointment();
    List<Appointment> getDoctorAppointment(Long doctor_id);
    List<Appointment> getPatientAppointment(Long patient_id);
    List<Appointment> getCurrentAppointment(Long doctor_id);
    String confirmAppointment(Long id);
    String cancelAppointment(Long id);
    String completeAppointment(Long id);
    List<Appointment> findByDoctorIdAndAppointmentDateInAndStatusIn(Long doctorId,List<LocalDate> next7Days, List<AppointmentStatus> appointmentStatus);
}
