package com.example.demo.domain.user.controller;

import com.example.demo.common.response.CommonResponse;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

//    Long loginUserId =
//            (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private final UserService userService;

    // 회원가입
    @PostMapping()
    public ResponseEntity<CommonResponse<UserCreateResponse>> createUser(@RequestBody @Valid UserCreateRequest request) {

        log.info("UserController - 회원가입 요청 들어옴");

        CommonResponse<UserCreateResponse> response = userService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 사용자 정보 상세 조회 (단건)
    @GetMapping("/{id}")
    public void getOneDetail(@PathVariable Long id) {

        CommonResponse<UserGetOneDetailResponse> response = userService.getOneDetail(id);
    }

    // 사용자 목록 조회
    @GetMapping
    public ResponseEntity<CommonResponse<List<UserGetListResponse>>> getList() {

        CommonResponse<List<UserGetListResponse>> response = userService.getList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 사용자 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<UserUpdateResponse>> updateUser(@PathVariable Long id,
                                                                         @RequestBody @Valid UserUpdateRequest request
    ) {
        CommonResponse<UserUpdateResponse> response = userService.update(id ,request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> delete(@PathVariable Long id) {

        CommonResponse<Void> response = userService.delete(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
