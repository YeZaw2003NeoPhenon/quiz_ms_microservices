package com.example.quiz_service.feign;

import com.example.quiz_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("USER-SERVICE")
public interface UserInterface {

    @GetMapping("/api/v1/users/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable Integer id);

    @PostMapping("/api/v1/users/add-quiz-to-user")
    ResponseEntity<UserResponse> addQuizToUser(@RequestParam Integer userId, @RequestParam Integer quizId);
}
