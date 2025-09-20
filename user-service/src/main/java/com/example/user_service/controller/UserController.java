package com.example.user_service.controller;

import com.example.user_service.dto.UserRequest;
import com.example.user_service.dto.UserResponse;
import com.example.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.created(URI.create("/api/v1/users/" + userRequest.getName()))
                .body(userService.createUser(userRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/add-quiz-to-user")
    public ResponseEntity<UserResponse> addQuizToUser(@RequestParam Integer userId, @RequestParam Integer quizId) {
        UserResponse createdQuizByUser = userService.addQuizToUser(userId, quizId);
        return ResponseEntity.ok(createdQuizByUser);
    }

}
