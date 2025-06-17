package com.example.hospital_appointment.application.service.interfaces;

import com.example.hospital_appointment.api.dto.HospitalResponse;
import com.example.hospital_appointment.api.dto.HospitalUpdateRequest;
import com.example.hospital_appointment.domain.model.Hospital;

import java.util.List;

public interface IHospitalService {
    Hospital save(Hospital hospital);
    Hospital getHospitalById(Long id);
    List<HospitalResponse> getAllHospital();
    String updateHospital(HospitalUpdateRequest hospitalUpdateRequest);
    String Ban(Long id);
    String unBan(Long id);
}
