package com.example.springboot_3_securityjwt.authentication.Registration;

import com.example.springboot_3_securityjwt.authentication.login.ErrorResponse;
import com.example.springboot_3_securityjwt.user.User;
import com.example.springboot_3_securityjwt.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private static final String USER_REGISTERED_SUCCESS = "User registered success!";

    private final RegistrationValidator registrationValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> register(RegistrationRequest request) {
        try {
            registrationValidator.validate(request);
            User user = createUser(request);
            userRepository.save(user);
            return new ResponseEntity<>(USER_REGISTERED_SUCCESS, HttpStatus.OK);

        } catch (RegistrationException exception) {
            final ErrorResponse errorResponse = ErrorResponse.builder()
                    .message(exception.getMessage())
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    private User createUser(final RegistrationRequest request) {
        final String encodePassword = passwordEncoder.encode(request.getPassword());
        return User.builder()
                .login(request.getLogin())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(encodePassword)
                .role(request.getRole())
                .build();
    }
}
