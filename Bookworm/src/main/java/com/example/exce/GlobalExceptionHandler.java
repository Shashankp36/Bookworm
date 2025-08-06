package com.example.exce;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobal(Exception e) {
        e.printStackTrace(); // print full stack
        return ResponseEntity.status(500).body("Error: " + e.getMessage());
    }
}
