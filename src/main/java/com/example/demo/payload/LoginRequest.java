package com.example.demo.payload;

import com.sun.istack.*;
import lombok.*;

@Getter
@Setter
public class LoginRequest {
    @NotNull
    private String username;

    @NotNull
    private String password;

}
