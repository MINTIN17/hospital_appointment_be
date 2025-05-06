package com.example.hospital_appointment.infrastructure.mapper;

import com.example.hospital_appointment.api.dto.PatientDTO;
import com.example.hospital_appointment.api.dto.RegisterRequest;
import com.example.hospital_appointment.api.dto.UserDTO;
import com.example.hospital_appointment.domain.Enums.Role;
import com.example.hospital_appointment.domain.model.Patient;
import com.example.hospital_appointment.domain.model.User;

public class PatientMapper {
    public static Patient toPatient(RegisterRequest patient) {
        User user = User.builder()
                .name(patient.getName())
                .email(patient.getEmail())
                .password(patient.getPassword())
                .phone(patient.getPhone())
                .gender(patient.getGender())
                .dateOfBirth(patient.getDateOfBirth())
                .avatarUrl("https://res.cloudinary.com/di53bdbjf/image/upload/v1746346822/hospital_appointment/avatar_jam4xb.png")
                .address(patient.getAddress())
                .role(Role.PATIENT)
                .enabled(true)
                .deleted(false)
                .build();

        return Patient.builder().user(user).build();
    }

    public static PatientDTO toDTO(Patient patient) {
        User user = patient.getUser();

        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setGender(user.getGender());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setAvatarUrl(user.getAvatarUrl());
        userDTO.setAddress(user.getAddress());

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setUser(userDTO);

        return patientDTO;
    }

}
