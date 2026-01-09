package com.example.demo.domain.user.controller;
import com.example.demo.common.response.GlobalResponse;
import com.example.demo.domain.user.dto.request.UserCreateRequest;
import com.example.demo.domain.user.dto.request.UserUpdateRequest;
import com.example.demo.domain.user.dto.response.UserCreateResponse;
import com.example.demo.domain.user.dto.response.UserGetListResponse;
import com.example.demo.domain.user.dto.response.UserGetOneDetailResponse;
import com.example.demo.domain.user.dto.response.UserUpdateResponse;
import com.example.demo.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.common.enums.SuccessMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<GlobalResponse<UserCreateResponse>> createUser(@RequestBody @Valid UserCreateRequest request) {

        log.info("UserController - 회원가입 요청 들어옴");

        UserCreateResponse result = userService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(GlobalResponse.success(USER_SIGNUP_SUCCESS, result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<UserGetOneDetailResponse>> getOneDetail(@PathVariable Long id) {

        UserGetOneDetailResponse result = userService.getOneDetail(id);

        return ResponseEntity.ok(GlobalResponse.success(USER_INFO_SUCCESS, result));
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<UserGetListResponse>>> getList() {

        List<UserGetListResponse> result = userService.getList();

        return ResponseEntity.ok(GlobalResponse.success(USER_INFO_SUCCESS, result));
    }

    // 사용자 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponse<UserUpdateResponse>> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateRequest request
    ) {
        UserUpdateResponse result = userService.update(id ,request);

        return ResponseEntity.ok(GlobalResponse.success(USER_UPDATE_SUCCESS, result));
    }


    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<Void>> delete(@PathVariable Long id) {

        userService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(GlobalResponse.successNodata(USER_DELETE_SUCCESS));
    }

}
