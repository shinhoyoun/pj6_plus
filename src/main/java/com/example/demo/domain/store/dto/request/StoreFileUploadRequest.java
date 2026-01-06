package com.example.demo.domain.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreFileUploadRequest {

    private String originalFileName;
    private String storedFileName;
}
