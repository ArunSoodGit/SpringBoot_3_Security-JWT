package com.example.springboot_3_securityjwt.token;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenValidator {

    private final TokenUtils tokenUtils;

    public boolean validate(final String token, final UserDetails userDetails) {
        final String username = tokenUtils.extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(final String token) {
        return tokenUtils.extractExpiration(token).before(new Date());
    }
}
