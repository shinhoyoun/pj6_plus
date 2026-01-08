package com.example.demo.domain.user.dto.response;

import com.example.demo.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Locale;

@Getter
@RequiredArgsConstructor
public class UserCreateResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final LocalDateTime createdAt;

    public static UserCreateResponse from(User user) {
        return new UserCreateResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getCreatedAt()
        );
    }
}
