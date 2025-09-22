package com.example.quiz_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponse {
    private Integer id;
    private String title;
    private String userName;
    private String userEmail;
    private Role role;

    public QuizResponse(Integer userId, String title, String userName, String userEmail) {
        this.id = userId;
        this.title = title;
        this.userName = userName;
        this.userEmail = userEmail;
    }
}
