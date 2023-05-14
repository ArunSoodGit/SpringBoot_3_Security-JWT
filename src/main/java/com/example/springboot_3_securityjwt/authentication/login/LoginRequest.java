package com.example.springboot_3_securityjwt.authentication.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String password;
}
