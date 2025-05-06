package com.example.hospital_appointment.infrastructure.mapper;

import com.example.hospital_appointment.api.dto.HospitalRequest;
import com.example.hospital_appointment.api.dto.RegisterRequest;
import com.example.hospital_appointment.domain.Enums.Role;
import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.model.User;

public class HospitalMapper {
    public static Hospital toHospital(HospitalRequest hospitalRequest) {
        return Hospital.builder()
                .avatarUrl(hospitalRequest.getAvatarUrl())
                .name(hospitalRequest.getName())
                .address(hospitalRequest.getAddress())
                .phone(hospitalRequest.getPhone())
                .build();
    }
}
