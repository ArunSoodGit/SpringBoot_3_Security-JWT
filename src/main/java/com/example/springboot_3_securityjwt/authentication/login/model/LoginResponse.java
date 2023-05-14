package com.example.springboot_3_securityjwt.authentication.login.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

    private String accessToken;
    @Builder.Default
    private String tokenType = "Bearer ";
}
