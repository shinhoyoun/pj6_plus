package com.example.demo.domain.user.service;

import com.example.demo.common.exception.CustomException;
import com.example.demo.domain.user.dto.request.UserCreateRequest;
import com.example.demo.domain.user.dto.request.UserUpdateRequest;
import com.example.demo.domain.user.dto.response.UserCreateResponse;
import com.example.demo.domain.user.dto.response.UserGetListResponse;
import com.example.demo.domain.user.dto.response.UserGetOneDetailResponse;
import com.example.demo.domain.user.dto.response.UserUpdateResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.demo.common.util.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.common.enums.ErrorMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원가입
    @Transactional
    public UserCreateResponse create(UserCreateRequest request) {

        User newUser = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName()
        );

        User savedUser = userRepository.save(newUser);

        return UserCreateResponse.from(savedUser);
    }

    // 사용자 정보 상세 조회 (단건)
    @Transactional(readOnly = true)
    public UserGetOneDetailResponse getOneDetail(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow( () -> new CustomException(NOT_FOUND_USER));

        return UserGetOneDetailResponse.from(user);
    }

    // 사용자 목록 조회
    @Transactional(readOnly = true)
    public List<UserGetListResponse> getList() {

        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(UserGetListResponse::from)
                .toList();
    }

    // 사용자 정보 수정
    @Transactional
    public UserUpdateResponse update(Long userId, UserUpdateRequest request) {

        User foundUser = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if (foundUser.getEmail().equals(request.getEmail())) {
            throw new CustomException(EXISTS_EMAIL);
        }

        if (foundUser.getUsername().equals(request.getUsername())) {
            throw new CustomException(EXISTS_USERNAME);
        }

        String newUserName = request.getUsername();
        String newEmail = request.getEmail();
        String newName = request.getName();

        User updatedUser = foundUser.update(newUserName, newEmail, newName);

        return UserUpdateResponse.from(updatedUser);
    }

    // 사용자 탈퇴
    @Transactional
    public void delete(Long id) {

        log.info("User Service - delete");

        User foundUser = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        foundUser.softDelete(true);
    }
}
