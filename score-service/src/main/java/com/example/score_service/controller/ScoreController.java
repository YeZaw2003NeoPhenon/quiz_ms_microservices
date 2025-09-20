package com.example.score_service.controller;

import com.example.score_service.dto.ScoreDto;
import com.example.score_service.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/v1/scores")
@RestController
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping("/save")
    public ResponseEntity<ScoreDto> saveScore(ScoreDto scoreDto){
        return ResponseEntity.created(URI.create("/api/v2/scores/save")).body(scoreService.saveScore(scoreDto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ScoreDto>> getScoresForUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(scoreService.getScoresByUserId(userId));
    }

    @GetMapping("/user-score")
    public ResponseEntity<List<ScoreDto>> getUserScoresForQuiz(@RequestParam Integer userId,
                                                            @RequestParam Integer quizId) {
        return ResponseEntity.ok(scoreService.getUserScoreForQuiz(userId, quizId));
    }

}
