package com.example.hospital_appointment.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "doctor")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String about;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;
    private int yearsOfExperience;
}
