package com.example.demo.domain.auth.repository;

import com.example.demo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {

    //회원을 이메일로 저징
    Optional<User> findByEmail(String email);
}
