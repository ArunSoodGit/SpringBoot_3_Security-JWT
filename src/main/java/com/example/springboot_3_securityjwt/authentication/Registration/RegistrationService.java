package com.example.springboot_3_securityjwt.authentication.registration;

import com.example.springboot_3_securityjwt.authentication.ErrorResponse;
import com.example.springboot_3_securityjwt.user.User;
import com.example.springboot_3_securityjwt.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private static final String USER_REGISTERED_SUCCESS = "User registered success!";

    private final RegistrationValidator registrationValidator;
    private final UserService userService;

    public ResponseEntity<?> register(RegistrationRequest request) {
        try {
            registrationValidator.validate(request);
            final User user = userService.createUser(request);
            userService.save(user);

            return new ResponseEntity<>(USER_REGISTERED_SUCCESS, HttpStatus.OK);

        } catch (RegistrationException exception) {
            final ErrorResponse errorResponse = ErrorResponse.builder()
                    .message(exception.getMessage())
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .build();

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
