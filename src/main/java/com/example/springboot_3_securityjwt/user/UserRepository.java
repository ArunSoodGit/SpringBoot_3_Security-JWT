package com.example.springboot_3_securityjwt.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);
}
