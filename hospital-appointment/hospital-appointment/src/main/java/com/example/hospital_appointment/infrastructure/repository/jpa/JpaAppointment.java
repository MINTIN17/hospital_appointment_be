package com.example.hospital_appointment.infrastructure.repository.jpa;

import com.example.hospital_appointment.domain.Enums.AppointmentStatus;
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
    List<Appointment> findByDoctor_Id(Long doctorId);
    List<Appointment> findByPatient_Id(Long patientId);
    List<Appointment> findByDoctorIdAndAppointmentDateInAndStatusIn(
            Long doctorId,
            List<LocalDate> appointmentDates,
            List<AppointmentStatus> statuses
    );
    List<Appointment> findByDoctorIdAndStatusIn(Long doctorId, List<AppointmentStatus> statuses);

    @Query("SELECT a.status, COUNT(a) " +
            "FROM Appointment a " +
            "WHERE a.status IN (:statuses) " +
            "AND a.appointmentDate BETWEEN :startDate AND :endDate " +
            "GROUP BY a.status")
    List<Object[]> countAppointmentsByStatuses(
            @Param("statuses") List<AppointmentStatus> statuses,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
    SELECT a.doctor.specialization.hospital.name, COUNT(a)
    FROM Appointment a
    WHERE a.status = :status
      AND a.appointmentDate BETWEEN :startDate AND :endDate
    GROUP BY a.doctor.specialization.hospital.name """)
    List<Object[]> countAppointmentsByHospital(
            @Param("status") AppointmentStatus status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT a.doctor.id, a.doctor.user.name, a.status, COUNT(a) " +
            "FROM Appointment a " +
            "WHERE a.status IN :statuses AND a.appointmentDate BETWEEN :startDate AND :endDate " +
            "GROUP BY a.doctor.id, a.doctor.user.name, a.status")
    List<Object[]> countAppointmentsByDoctor(
            @Param("statuses") List<AppointmentStatus> statuses,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT a.patient.id, a.appointmentDate " +
            "FROM Appointment a " +
            "WHERE a.status = :status " +
            "ORDER BY a.patient.id, a.appointmentDate")
    List<Object[]> findCompletedAppointments(@Param("status") AppointmentStatus status);


}
