package com.example.hospital_appointment.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "specialization")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL)
    private List<Doctor> doctors;
}
