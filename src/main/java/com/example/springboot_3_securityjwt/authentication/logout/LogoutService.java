package com.example.springboot_3_securityjwt.authentication.logout;

import com.example.springboot_3_securityjwt.token.Token;
import com.example.springboot_3_securityjwt.token.TokenRepository;
import com.example.springboot_3_securityjwt.token.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogoutService {

    public static final String LOGGED_MESSAGE = "Logged";
    public static final String TOKEN_NOT_FOUND = "Token not found";
    private final TokenRepository tokenRepository;
    private final TokenUtils tokenUtils;

    public ResponseEntity<?> logout(@NonNull final HttpServletRequest request) {
        return tokenUtils.obtainJwtTokenFromRequest(request)
                .flatMap(tokenRepository::findByToken)
                .map(this::invalidateToken)
                .orElse(new ResponseEntity<>(TOKEN_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private ResponseEntity<String> invalidateToken(final Token token) {
        token.setExpired(true);
        token.setRevoked(true);
        tokenRepository.save(token);
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(LOGGED_MESSAGE, HttpStatus.OK);
    }
}
