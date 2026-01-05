package com.example.demo.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "useremail", unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;


    private String role;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    //삭제하기
    public User(String foundName) {
    }

    public String getName() {
        return "";
    }

    public void updateUser(String foundName) {
    }

    public User updateMember(String foundName) {
        return null;
    }

    public void softDeleted() {

    }
}
