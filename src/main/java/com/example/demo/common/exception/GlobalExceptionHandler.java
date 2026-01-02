package com.example.demo.common.exception;

import com.example.demo.common.response.GlobalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();

        return ResponseEntity.status(ex.getStatusCode()).body(GlobalResponse.exception(message));
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<GlobalResponse<Void>> handlerCustomException(CustomException ex) {

        return ResponseEntity.status(ex.getErrorMessage().getStatus()).body(GlobalResponse.exception(ex.getErrorMessage().getMessage()));
    }
}


