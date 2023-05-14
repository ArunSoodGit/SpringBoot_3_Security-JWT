package com.example.springboot_3_securityjwt.authentication.login;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer ";

    public LoginResponse(final String accessToken) {
        this.accessToken = accessToken;
    }
}
