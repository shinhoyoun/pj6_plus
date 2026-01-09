package com.example.demo.domain.store.controller;

import com.example.demo.common.enums.ErrorMessage;
import com.example.demo.common.enums.SuccessMessage;
import com.example.demo.common.response.GlobalResponse;
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

import static com.example.demo.common.enums.SuccessMessage.*;

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
    public ResponseEntity<GlobalResponse<Void>> uploadFormPostApi(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(GlobalResponse.exception(ErrorMessage.INVALID_REQUEST.getMessage()));
        }

        try {
            String tempFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File tempFile = File.createTempFile(tempFileName, null);
            file.transferTo(tempFile);

            storeUploadService.uploadCsvFile(tempFile);

            return ResponseEntity.ok(GlobalResponse.success(FILE_UPLOAD_SUCCESS,null));
        } catch (IOException e) {
            log.error("파일 저장 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalResponse.exception(ErrorMessage.FILE_UPLOAD_ERROR.getMessage()));
        } catch (Exception e) {
            log.error("업로드 요청 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalResponse.exception(ErrorMessage.INTERNAL_SERVER_ERROR.getMessage()));
        }
    }

    /**
     * API 호출
     */
    @GetMapping("/collection-openapi")
    public ResponseEntity<GlobalResponse<Void>> callStoreApi(
            @RequestParam(value = "start", defaultValue = "1") int start,
            @RequestParam(value = "end", defaultValue = "100") int end
    ){
        try {
            storeUploadService.saveAllStore(start, end);
            return ResponseEntity.ok(GlobalResponse.successNodata(SuccessMessage.STORE_UPLOAD_COMPLETE));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalResponse.exception(e.getMessage()));
        }


    }



}
