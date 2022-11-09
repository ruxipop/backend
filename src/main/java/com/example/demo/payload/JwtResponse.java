package com.example.demo.payload;

import lombok.*;


@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String role;

    public JwtResponse(String token, Integer id, String username, String firstName, String lastName, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
