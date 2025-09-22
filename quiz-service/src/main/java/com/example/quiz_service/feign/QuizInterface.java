package com.example.quiz_service.feign;

import com.example.quiz_service.dto.QuestionResponse;
import com.example.quiz_service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

     @GetMapping("/api/v1/questions/generate")
     ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam int numberOfQuestions);

     @PostMapping("/api/v1/questions/getQuestions")
     ResponseEntity<List<QuestionResponse>> getQuestionsFromIds(@RequestBody List<Integer> questionIds);

     @PostMapping("/api/v1/questions/getScore")
     ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
