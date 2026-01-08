package com.example.demo.domain.search.controller;

import com.example.demo.domain.search.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/keywords")
public class CacheController {

    private final CacheService cacheService;

    // 키워드 저장
    @PostMapping
    public void saveKeyword(@RequestParam String keyword) {

        cacheService.saveKeyword(keyword);
    }

    // 키워드 단건 조회
    @GetMapping
    public String getOneKeyword(@RequestParam String keyword) {

        log.info("[단건 조회 요청 들어옴] keyword={}", keyword);

        return cacheService.GetOneKeyword(keyword);
    }


    // 키워드 목록 조회
    @GetMapping("/all")
    public Map<Object, Object> getAllKeywords() {

        return cacheService.getAllKeyword();
    }


}
