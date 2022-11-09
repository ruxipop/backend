package com.example.demo.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity(name="device")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_device")
    private Integer id;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private  Double maxConsumation;

    @OneToMany(mappedBy = "device")
    private Collection<Measurement> measurements;

    @OneToOne
    @JoinColumn(name = "users_id")
    @JsonIgnore
    private User user;

}
