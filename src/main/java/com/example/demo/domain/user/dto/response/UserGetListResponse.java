package com.example.demo.domain.user.dto.response;

import com.example.demo.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserGetListResponse {

    private Long id;
    private String username;
    private String email;
    private String name;
    private LocalDateTime createdAt;

    public static UserGetListResponse from(User user) {
        return new UserGetListResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getCreatedAt()
        );
    }
}
