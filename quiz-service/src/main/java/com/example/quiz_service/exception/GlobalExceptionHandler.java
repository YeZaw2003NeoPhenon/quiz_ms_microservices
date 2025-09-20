package com.example.quiz_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<Object> quizNotFoundHandler(QuizNotFoundException ex) {
        Map<String,String> body = Map.of("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(body);
    }

}
