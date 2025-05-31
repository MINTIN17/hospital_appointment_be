package com.example.hospital_appointment.infrastructure.repository;

import com.example.hospital_appointment.domain.Enums.AppointmentStatus;
import com.example.hospital_appointment.domain.model.Appointment;
import com.example.hospital_appointment.domain.repository.IAppointmentRepo;
import com.example.hospital_appointment.infrastructure.repository.jpa.JpaAppointment;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public class AppointmentRepo implements IAppointmentRepo {
    private final JpaAppointment jpaAppointment;

    public AppointmentRepo(JpaAppointment jpaAppointment) {
        this.jpaAppointment = jpaAppointment;
    }

    public String save(Appointment appointment) {
        Appointment appointment1 = jpaAppointment.save(appointment);
        return "Success";
    }

    @Override
    public Boolean CheckAppointment(Appointment appointment) {
        AppointmentStatus status = appointment.getStatus();
        return status == AppointmentStatus.PENDING || status == AppointmentStatus.CONFIRMED;
    }

    @Override
    public Optional<Appointment> findExist(LocalDate appointmentDate, LocalTime startTime, LocalTime endTime, Long doctor_id) {
        return jpaAppointment.findByAppointmentDateAndStartTimeAndEndTimeAndDoctor_Id(appointmentDate, startTime,
                                         endTime,
                                         doctor_id);
    }



}
