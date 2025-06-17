package com.example.hospital_appointment.application.service;

import com.example.hospital_appointment.api.dto.HospitalResponse;
import com.example.hospital_appointment.api.dto.HospitalUpdateRequest;
import com.example.hospital_appointment.application.service.interfaces.IHospitalService;
import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.repository.IAppointmentRepo;
import com.example.hospital_appointment.domain.repository.IDoctorRepo;
import com.example.hospital_appointment.domain.repository.IHospitalRepo;
import com.example.hospital_appointment.domain.repository.ISpecializationRepo;
import com.example.hospital_appointment.infrastructure.mapper.HospitalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalService implements IHospitalService {

    @Autowired
    private IHospitalRepo hospitalRepo;
    @Autowired
    private ISpecializationRepo specializationRepo;
    @Autowired
    private IDoctorRepo doctorRepo;

    @Override
    public Hospital save(Hospital hospital) {
        return hospitalRepo.save(hospital);
    }

    @Override
    public Hospital getHospitalById(Long id) {
        return hospitalRepo.getHospitalById(id);
    }

    @Override
    public List<HospitalResponse> getAllHospital() {
        List<Hospital> hospitals = hospitalRepo.findAll();
        List<HospitalResponse> responses = new ArrayList<>();

        for (Hospital hospital : hospitals) {
            int doctorCount = doctorRepo.countByHospitalId(hospital.getId());
            int specializationCount = specializationRepo.countByHospitalId(hospital.getId());

            HospitalResponse response = HospitalMapper.toHospitalResponse(hospital, doctorCount, specializationCount);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public String updateHospital(HospitalUpdateRequest hospitalUpdateRequest) {
        Hospital hospital = hospitalRepo.getHospitalById(hospitalUpdateRequest.getId());
        hospital.setAvatarUrl(hospitalUpdateRequest.getAvatarUrl());
        hospital.setName(hospitalUpdateRequest.getName());
        hospital.setAddress(hospitalUpdateRequest.getAddress());
        hospital.setPhone(hospitalUpdateRequest.getPhone());
        hospitalRepo.save(hospital);
        return "update success";
    }

    @Override
    public String Ban(Long id) {
        return hospitalRepo.Ban(id);
    }

    @Override
    public String unBan(Long id) {
        return hospitalRepo.unBan(id);
    }
}
