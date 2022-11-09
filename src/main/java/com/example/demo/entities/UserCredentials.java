package com.example.demo.entities;

import com.sun.istack.*;
import lombok.*;
import org.apache.logging.log4j.message.*;

import javax.persistence.*;

@Getter
@Setter
public class UserCredentials {
    @NotNull ()
    @Column(unique=true)
    private String username;
    @NotNull
    private String password;

    public UserCredentials() {

    }

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;

    }
}
