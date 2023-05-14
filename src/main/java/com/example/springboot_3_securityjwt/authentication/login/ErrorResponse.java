package com.example.springboot_3_securityjwt.authentication.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String message;
    private HttpStatus statusCode;
}
