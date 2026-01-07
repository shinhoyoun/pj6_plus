package com.example.demo.domain.store.controller;

import com.example.demo.domain.store.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/keywords")
public class KeywordController {

    private final KeywordService keywordService;

    // 키워드 저장
    @PostMapping
    public void saveKeyword(@RequestParam String keyword) {
        keywordService.saveKeyword(keyword);
    }


    // 키워드 목록 조회
    @GetMapping
    public List<String> findAll() {
        return keywordService.getKeywords();
    }


}
