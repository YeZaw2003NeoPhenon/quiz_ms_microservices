package com.example.score_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDto {

    private Integer userId;
    private Integer quizId;
    private Integer score;
}
