package com.example.hospital_appointment.infrastructure.mapper;

import com.example.hospital_appointment.api.dto.HospitalRequest;
import com.example.hospital_appointment.api.dto.SpecializationRequest;
import com.example.hospital_appointment.api.dto.SpecializationResponse;
import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Specialization;

import java.util.List;
import java.util.stream.Collectors;

public class SpecializationMapper {
    public static Specialization toSpecialization(SpecializationRequest specializationRequest, Hospital hospital) {
        return Specialization.builder()
                .name(specializationRequest.getName())
                .hospital(hospital)
                .build();
    }

    public static SpecializationResponse toSpecializationResponse(Specialization specialization) {
        return SpecializationResponse.builder()
                .id(specialization.getId())
                .name(specialization.getName())
                .build();
    }

    public static List<SpecializationResponse> toSpecializationResponseList(List<Specialization> specializations) {
        return specializations.stream()
                .map(SpecializationMapper::toSpecializationResponse)
                .collect(Collectors.toList());
    }

}
