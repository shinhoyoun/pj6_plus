package com.example.demo.domain.store.controller;

import com.example.demo.domain.store.service.StoreUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
@Slf4j
public class StoreUploadController {


    private final StoreUploadService storeUploadService;

    /**
     * 파일 업로드
     */
    @PostMapping("/uploadForm")
    public ResponseEntity<String> uploadFormPostApi(@RequestPart("file") MultipartFile file) {
        try {
            storeUploadService.uploadCsvFile(file);
            return ResponseEntity.ok("업로드 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        //1.csv 파일을 서버에 전송하기
        //2.csv 파일을 자바 라이브러리를 이용해 읽을 수 있는 방법
        //3.엔티티를 통해서 읽을 수 있게 만들기

    }


}
