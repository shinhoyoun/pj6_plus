package com.example.demo.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    @NotBlank
    @Size(min = 4, max = 20, message = "아이디를 4 ~ 20자로 입력하세요.")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "아이디는 영문/숫자만 허용합니다.")
    private String username;

    @NotBlank
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank
    @Size(min = 8, message = "비밀번호를 8자 이상 입력하세요.")
    private String password;

    @NotBlank
    @Size(min = 2, max = 30, message = "이름은 2 ~ 30자로 입력하세요")
    private String name;

}
