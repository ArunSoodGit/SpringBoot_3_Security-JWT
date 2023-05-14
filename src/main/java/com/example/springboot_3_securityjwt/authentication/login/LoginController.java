package com.example.springboot_3_securityjwt.authentication.login;

import com.example.springboot_3_securityjwt.authentication.login.model.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody final LoginRequest request) {
        return loginService.login(request);
    }
}
