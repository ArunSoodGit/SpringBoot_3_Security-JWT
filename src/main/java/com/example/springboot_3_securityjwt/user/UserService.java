package com.example.springboot_3_securityjwt.user;

import com.example.springboot_3_securityjwt.authentication.registration.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final String USER_NOT_FOUND_MESSAGE = "User not found";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByLogin(final String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    public void save(final User user) {
        userRepository.save(user);
    }

    public boolean existsByLogin(final String login) {
        return userRepository.existsByLogin(login);
    }

    public User createUser(final RegistrationRequest request) {
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
