package com.example.demo.entities.dto;

import com.example.demo.entities.*;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
public class UserDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String username;
    private String password;

    private String address;
    private String role;

    public User convertTO() {
        return User.builder().id(id).firstName(firstName)
                .lastName(lastName).username(username)
                .password(password).address(address)
                .role(role)
                .build();
    }
}
