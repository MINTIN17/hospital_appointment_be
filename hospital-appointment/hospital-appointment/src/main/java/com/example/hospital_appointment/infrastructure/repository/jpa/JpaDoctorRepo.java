package com.example.hospital_appointment.infrastructure.repository.jpa;

import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.Doc;
import java.util.List;

public interface JpaDoctorRepo extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialization_Hospital_Id(Long hospitalId);
}
