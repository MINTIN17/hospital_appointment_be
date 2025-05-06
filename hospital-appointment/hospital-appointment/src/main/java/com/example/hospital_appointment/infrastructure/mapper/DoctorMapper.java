package com.example.hospital_appointment.infrastructure.mapper;

import com.example.hospital_appointment.api.dto.DoctorRequest;
import com.example.hospital_appointment.api.dto.DoctorResponse;
import com.example.hospital_appointment.api.dto.SpecializationRequest;
import com.example.hospital_appointment.api.dto.SpecializationResponse;
import com.example.hospital_appointment.domain.Enums.Role;
import com.example.hospital_appointment.domain.model.Doctor;
import com.example.hospital_appointment.domain.model.Hospital;
import com.example.hospital_appointment.domain.model.Specialization;
import com.example.hospital_appointment.domain.model.User;

import javax.print.Doc;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorMapper {
    public static Doctor toDoctor(DoctorRequest doctorRequest, Specialization specialization ) {
        User user = User.builder()
                .name(doctorRequest.getRegisterRequest().getName())
                .email(doctorRequest.getRegisterRequest().getEmail())
                .password(doctorRequest.getRegisterRequest().getPassword())
                .phone(doctorRequest.getRegisterRequest().getPhone())
                .gender(doctorRequest.getRegisterRequest().getGender())
                .dateOfBirth(doctorRequest.getRegisterRequest().getDateOfBirth())
                .avatarUrl(doctorRequest.getRegisterRequest().getAvatarUrl())
                .address(doctorRequest.getRegisterRequest().getAddress())
                .role(Role.DOCTOR)
                .enabled(true)
                .deleted(false)
                .build();

        return Doctor.builder()
                .user(user)
                .yearsOfExperience(doctorRequest.getYearsOfExperience())
                .about(doctorRequest.getAbout())
                .specialization(specialization)
                .build();
    }

    public static DoctorResponse toDoctorResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getUser().getName())
                .email(doctor.getUser().getEmail())
                .about(doctor.getAbout())
                .phone(doctor.getUser().getPhone())
                .gender(doctor.getUser().getGender())
                .dateOfBirth(doctor.getUser().getDateOfBirth())
                .avatarUrl(doctor.getUser().getAvatarUrl())
                .address(doctor.getUser().getAddress())
                .role(Role.DOCTOR)
                .yearsOfExperience(doctor.getYearsOfExperience())
                .specialization_name(doctor.getSpecialization().getName())
                .hospital_name(doctor.getSpecialization().getHospital().getName())
                .build();

    }

    public static List<DoctorResponse> toDoctorResponseList(List<Doctor> doctors) {
        return doctors.stream()
                .map(DoctorMapper::toDoctorResponse)
                .collect(Collectors.toList());
    }

}
