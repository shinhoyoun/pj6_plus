package com.example.demo.domain.store.controller;

import com.example.demo.domain.store.service.StoreUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
@Slf4j
public class StoreUploadController {


    private final StoreUploadService storeUploadService;

    /**
     * 파일 업로드
     */
    @PostMapping("/collerction")
    public ResponseEntity<String> uploadFormPostApi(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 비어있습니다.");
        }

        try {
            // 임시 파일 생성
            String tempFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File tempFile = File.createTempFile(tempFileName, null);
            file.transferTo(tempFile);

            // 비동기 처리 호출
            storeUploadService.uploadCsvFile(tempFile);

            return ResponseEntity.ok("파일 업로드가 시작되었습니다. 처리가 완료되면 로그를 확인하세요.");
        } catch (IOException e) {
            log.error("파일 저장 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 저장 중 오류가 발생했습니다.");
        } catch (Exception e) {
            log.error("업로드 요청 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업로드 요청 처리 중 오류가 발생했습니다.");
        }
    }

    /**
     * API 호출
     */
    @GetMapping("/collection-openapi")
    public ResponseEntity<String> callStoreApi(
            @RequestParam(value = "start", defaultValue = "1") int start,
            @RequestParam(value = "end", defaultValue = "100") int end
    ){
        try {
            storeUploadService.saveAllStore(start, end);
            return ResponseEntity.ok("업로드 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }


    }



}
