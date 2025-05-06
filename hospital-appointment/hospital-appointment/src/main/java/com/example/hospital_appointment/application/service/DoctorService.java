package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.application.service.interfaces.IDoctorService;
import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.repository.IDoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService implements IDoctorService {
    @Autowired
    private IDoctorRepo doctorRepo;

    @Override
    public Doctor save(Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    @Override
    public List<Doctor> getDoctorsByHospital(Long hospital_id) {
        return doctorRepo.findByUser_Hospital_Id(hospital_id);
    }
}
