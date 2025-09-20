package com.example.quiz_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {

    private String category;
    private Integer numOfQuestions;
    private String title;
    private Integer userId;
}
