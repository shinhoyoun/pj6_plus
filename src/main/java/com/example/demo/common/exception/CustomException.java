package com.example.demo.common.exception;

import com.example.demo.common.enums.ErrorMessage;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public CustomException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
