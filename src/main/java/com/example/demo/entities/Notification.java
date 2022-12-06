package com.example.demo.entities;


import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.sql.*;

@Entity(name = "notification")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_notification")
    private Integer id;

    @Column
    private String message;

    @Column
    private String dates;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private User user;

}
