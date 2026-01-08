package com.example.demo.domain.store.controller;

import com.example.demo.domain.store.service.StoreUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @PostMapping("/collerction")
    public ResponseEntity<String> uploadFormPostApi(@RequestPart("file") MultipartFile file) {
        try {
            storeUploadService.uploadCsvFile(file);
            return ResponseEntity.ok("업로드 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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
