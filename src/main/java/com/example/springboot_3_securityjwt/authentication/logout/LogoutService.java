package com.example.springboot_3_securityjwt.authentication.logout;

import com.example.springboot_3_securityjwt.token.JwtUtils;
import com.example.springboot_3_securityjwt.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogoutService {

    private static final String TOKEN_NOT_FOUND = "Token not found";
    private final TokenService tokenService;

    public ResponseEntity<?> logout(@NonNull final HttpServletRequest request) {
        return JwtUtils.obtainJwtTokenFromRequest(request)
                .flatMap(tokenService::findByJwtToken)
                .map(tokenService::invalidateToken)
                .orElse(new ResponseEntity<>(TOKEN_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
