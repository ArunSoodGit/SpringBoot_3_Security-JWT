package com.example.springboot_3_securityjwt.authentication.registration;

import com.example.springboot_3_securityjwt.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String login;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
}
