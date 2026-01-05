package com.example.demo.domain.user.service;

import com.example.demo.common.response.CommonResponse;
import com.example.demo.common.util.PasswordEncoder;
import com.example.demo.domain.user.dto.request.UserCreateRequest;
import com.example.demo.domain.user.dto.request.UserUpdateRequest;
import com.example.demo.domain.user.dto.response.UserCreateResponse;
import com.example.demo.domain.user.dto.response.UserGetListResponse;
import com.example.demo.domain.user.dto.response.UserGetOneDetailResponse;
import com.example.demo.domain.user.dto.response.UserUpdateResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원가입
    @Transactional
    public CommonResponse<UserCreateResponse> create(UserCreateRequest request) {

        boolean exitsEmail = userRepository.existsByEmail(request.getEmail());

        if (exitsEmail) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }

        boolean exitsUsername = userRepository.existsByUsername(request.getUsername());

        if (exitsUsername) {
            throw new IllegalArgumentException("이미 사용중인 사용자명입니다.");
        }

        User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getName());

        User savedUser = userRepository.save(user);

        UserCreateResponse response = UserCreateResponse.from(savedUser);

        return new CommonResponse<>(true, "회원가입이 완료되었습니다.", response);
    }

    // 사용자 정보 상세 조회 (단건)
    @Transactional(readOnly = true)
    public CommonResponse<UserGetOneDetailResponse> getOneDetail(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow( () -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        UserGetOneDetailResponse response = UserGetOneDetailResponse.from(user);

        return new CommonResponse<>(true, "사용자 상세 정보 조회 성공", response);
    }

    // 사용자 목록 조회
    @Transactional(readOnly = true)
    public CommonResponse<List<UserGetListResponse>> getList() {

        List<User> userList = userRepository.findAll();

        List<UserGetListResponse> userGetListResponsesList = userList.stream()
                .map(UserGetListResponse::from)
                .toList();

        return new CommonResponse<>(true, "사용자 목록 조회 성공", userGetListResponsesList);
    }

    // 사용자 정보 수정
    @Transactional
    public CommonResponse<UserUpdateResponse> update(Long userId, @Valid UserUpdateRequest request) {

        User foundUser = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new RuntimeException("없는 사용자입니다."));

        boolean exitsEmail = userRepository.existsByEmail(request.getEmail());

        if (exitsEmail) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }

        boolean exitsUsername = userRepository.existsByUsername(request.getName());

        if (exitsUsername) {
            throw new IllegalArgumentException("이미 사용중인 사용자명입니다.");
        }

        String newUserName = request.getUsername();
        String newEmail = request.getEmail();
        String newName = request.getName();

        User updatedUser = foundUser.update(newUserName, newEmail, newName);

        UserUpdateResponse response = UserUpdateResponse.from(updatedUser);

        return new CommonResponse<>(true, "사용자 정보가 수정되었습니다.", response);
    }

    // 사용자 탈퇴
    @Transactional
    public CommonResponse<Void> delete(Long id) {

        log.info("User Service - delete");

        User foundUser = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("없는 사용자입니다."));

        foundUser.softDelete();

        return new CommonResponse<>(true, "회원 탈퇴되었습니다.", null);
    }


}
