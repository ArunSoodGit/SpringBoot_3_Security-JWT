package com.example.springboot_3_securityjwt.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Service
public class TokenUtils {

    private static final String BEARER = "Bearer ";
    private static final int BEGIN_INDEX_OF_JWT_TOKEN = 7;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Optional<String> obtainJwtTokenFromRequest(final HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return Optional.ofNullable(authHeader)
                .filter(header -> StringUtils.hasText(header) && header.startsWith(BEARER))
                .map(header -> header.substring(BEGIN_INDEX_OF_JWT_TOKEN));
    }
}
