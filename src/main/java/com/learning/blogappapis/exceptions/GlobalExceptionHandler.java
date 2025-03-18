package com.learning.blogappapis.exceptions;

import com.learning.blogappapis.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
        String message = exception.getMessage();
        LocalDateTime localDate = LocalDateTime.now();
        ApiResponse apiResponse = new ApiResponse(message,true,localDate);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String,String> response = new HashMap<>();
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        allErrors.forEach(e -> {
            String field = ((FieldError) e).getField();
            String defaultMessage = e.getDefaultMessage();
            response.put(field,defaultMessage);
        });


        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
