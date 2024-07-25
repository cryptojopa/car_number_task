package com.jobtest.jobtest.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "car_number")
public class CarNumber {

    @Id
    @SequenceGenerator(name = "car_number_id_seq", sequenceName = "car_number_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_number_id_seq")
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private boolean alreadyUsed = false;
}
