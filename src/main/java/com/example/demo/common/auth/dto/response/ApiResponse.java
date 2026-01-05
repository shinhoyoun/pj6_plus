package com.example.demo.common.auth.dto.response;

public class ApiResponse<T> {

    //속성
    private String message;
    private Integer status;
    private T date;

    //생성자
    public ApiResponse(String message, Integer status, T date) {
        this.message = message;
        this.status = status;
        this.date = date;
    }

    //기능
    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }

    public T getDate() {
        return date;
    }
}

