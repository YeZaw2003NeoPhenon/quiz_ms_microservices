package com.example.quiz_service.feign;

import com.example.quiz_service.dao.ScoreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "SCORE-SERVICE")
public interface ScoreInterface {

    @PostMapping("/api/v1/scores/save")
    ResponseEntity<ScoreDto> saveScore(ScoreDto scoreDto);

    @GetMapping("/api/v1/scores/user-score")
    ResponseEntity<List<ScoreDto>> getUserScoresForQuiz(@RequestParam Integer userId, @RequestParam Integer quizId);

}
