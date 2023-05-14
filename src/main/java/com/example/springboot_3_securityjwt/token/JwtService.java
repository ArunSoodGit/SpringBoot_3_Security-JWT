package com.example.springboot_3_securityjwt.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final TokenUtils tokenUtils;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String generateToken(final Authentication user) {
        final String username = user.getName();
        final Date currentDate = new Date();
        final Date expireDate = new Date(currentDate.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(tokenUtils.getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }
}
