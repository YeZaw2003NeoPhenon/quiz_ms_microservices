package com.example.question_service.controller;


import com.example.question_service.dto.QuestionRequest;
import com.example.question_service.dto.QuestionResponse;
import com.example.question_service.model.Question;
import com.example.question_service.model.Response;
import com.example.question_service.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<QuestionResponse> createQuestion(@Valid @RequestBody QuestionRequest request) {

        QuestionResponse response = questionService.createQuestion(request);
        return ResponseEntity.created(URI.create("/api/v1/questions/" + response.getId())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions(){
        return ResponseEntity.ok().body(questionService.getAllQuestions());
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category){
        return ResponseEntity.ok().body(questionService.getQuestionByCategory(category));
    }

    // this endpoint will be handled to generate quiz
    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam int numberOfQuestions){
        return ResponseEntity.ok().body(questionService.getQuestionsForQuiz(category, numberOfQuestions));
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionResponse>> getQuestionsFromIds(@RequestBody List<Integer> questionIds){
        return ResponseEntity.ok().body(questionService.getQuestionsFromIds(questionIds));
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return ResponseEntity.ok().body(questionService.getScore(responses));
    }

}
