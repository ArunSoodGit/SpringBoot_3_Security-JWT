package com.example.springboot_3_securityjwt.config;

import com.example.springboot_3_securityjwt.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class UserDetailsConfiguration {

    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return userService::findByLogin;
    }
}
