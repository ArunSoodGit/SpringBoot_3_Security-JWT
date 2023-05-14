package com.example.springboot_3_securityjwt.authentication.login;

import com.example.springboot_3_securityjwt.token.JwtService;
import com.example.springboot_3_securityjwt.token.Token;
import com.example.springboot_3_securityjwt.token.TokenRepository;
import com.example.springboot_3_securityjwt.token.TokenType;
import com.example.springboot_3_securityjwt.user.User;
import com.example.springboot_3_securityjwt.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.springboot_3_securityjwt.config.ApplicationConfiguration.USER_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class LoginService {

    public static final String LOGIN_ERROR_MESSAGE = "Login error. Check if your login and password are correct";
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> login(final LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getLogin(),
                            request.getPassword()));
            String jwtToken = jwtService.generateToken(authentication);
            User user = obtainUserFromRequest(request);
            saveUserToken(user, jwtToken);
            return new ResponseEntity<>(new LoginResponse(jwtToken), HttpStatus.OK);

        } catch (AuthenticationException exception) {
            final ErrorResponse response = ErrorResponse.builder()
                    .message(LOGIN_ERROR_MESSAGE)
                    .statusCode(HttpStatus.UNAUTHORIZED)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    private User obtainUserFromRequest(final LoginRequest request) {
        return userRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    private void saveUserToken(final User user, final String jwtToken) {
        final Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }
}
