package com.example.quiz_service.controller;

import com.example.quiz_service.dao.ScoreDto;
import com.example.quiz_service.dto.QuestionResponse;
import com.example.quiz_service.dto.QuizDto;
import com.example.quiz_service.dto.QuizResponse;
import com.example.quiz_service.model.Response;
import com.example.quiz_service.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<QuizResponse> createQuiz(@RequestBody QuizDto quizDto, @RequestHeader("Authorization") String token){

//        String token = authHeader.replace("Bearer ", "");

        QuizResponse response = quizService.createQuiz(token, quizDto.getCategory(), quizDto.getNumOfQuestions(), quizDto.getTitle(), quizDto.getUserId());
        return ResponseEntity.created(URI.create("/api/v1/quiz/create")).body(response);
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<List<QuestionResponse>> getQuizQuestionsByQuizId(@PathVariable Integer id, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok().body(quizService.getQuestionsByQuiz(token,id));
    }

    /// param (id) refers to quiz id
    @PostMapping("/submit")
    public ResponseEntity<Integer> submitResult(@RequestParam Integer quizId, @RequestParam Integer userId, @RequestBody List<Response> responses){
        return ResponseEntity.ok().body(quizService.submitQuiz(quizId, userId, responses));
    }

    @GetMapping("/user-quizzes/{userId}")
    public ResponseEntity<List<QuizResponse>> getQuizzesForUser(@PathVariable Integer userId){
        return ResponseEntity.ok().body(quizService.getQuizzesForUser(userId));
    }

    @GetMapping("/user-scores")
    public ResponseEntity<List<ScoreDto>> getUserScoresForQuiz(@RequestParam Integer userId, @RequestParam Integer quizId){
        return ResponseEntity.ok().body(quizService.getUserScoresForQuiz(userId, quizId));
    }

}
