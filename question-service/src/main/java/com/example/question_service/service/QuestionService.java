package com.example.question_service.service;

import com.example.question_service.dto.QuestionRequest;
import com.example.question_service.dto.QuestionResponse;
import com.example.question_service.model.Question;
import com.example.question_service.model.Response;

import java.util.List;

public interface QuestionService {

    QuestionResponse createQuestion(QuestionRequest request);

    List<Question> getAllQuestions();

    List<Question> getQuestionByCategory(String category);

    List<Integer> getQuestionsForQuiz(String category, int numberOfQuestions);

    List<QuestionResponse> getQuestionsFromIds(List<Integer> questionIds);

    Integer getScore(List<Response> responses);

}
