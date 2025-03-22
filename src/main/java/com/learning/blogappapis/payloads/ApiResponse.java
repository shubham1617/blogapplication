package com.learning.blogappapis.payloads;

import org.springframework.http.HttpStatusCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApiResponse  {

    private String message;
    private boolean success;
    private LocalDateTime date;

    public ApiResponse(String message, boolean success, LocalDateTime date) {
        this.message = message;
        this.success = success;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
