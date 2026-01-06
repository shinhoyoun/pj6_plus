package com.example.demo.domain.user.dto.response;

import com.example.demo.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Locale;

@Getter
@AllArgsConstructor
public class UserCreateResponse {

    private Long id;
    private String username;
    private String email;
    private String name;
    private LocalDateTime createdAt;

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
