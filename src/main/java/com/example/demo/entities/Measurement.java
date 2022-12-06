package com.example.demo.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.sql.*;
import java.time.*;

@Entity(name="measurements")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_measurement")
    private Integer id;

    @Column
    private Timestamp timestamp;

    @Column
    private Double consumation;

    @ManyToOne
    @JoinColumn(name="id_device")
    @JsonIgnore
    private Device device;

    public Measurement(Timestamp timestamp, Double consumation) {
        this.timestamp = timestamp;
        this.consumation = consumation;
    }
}
