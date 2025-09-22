package com.example.quiz_service.service;

import com.example.quiz_service.dao.QuizDao;
import com.example.quiz_service.dao.ScoreDto;
import com.example.quiz_service.dto.QuestionResponse;
import com.example.quiz_service.dto.QuizResponse;
import com.example.quiz_service.dto.Role;
import com.example.quiz_service.exception.QuizNotFoundException;
import com.example.quiz_service.feign.AccountInterface;
import com.example.quiz_service.feign.QuizInterface;
import com.example.quiz_service.feign.ScoreInterface;
import com.example.quiz_service.feign.UserInterface;
import com.example.quiz_service.model.Quiz;
import com.example.quiz_service.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizDao quizDao;

    private final QuizInterface quizInterface;

    private final UserInterface userInterface;

    private final ScoreInterface scoreInterface;

    private final AccountInterface accountInterface;

    public QuizResponse createQuiz(String token, String categoryName, Integer numOfQuestions, String title, Integer userId) {
        var userResponse = userInterface.getUserById(userId).getBody();

        var accountResponse = accountInterface.validate("Bearer " + token).getBody();
        if (accountResponse == null) {
            throw new RuntimeException("Invalid or expired token");
        }

        if(!accountResponse.getRole().equals(Role.ADMIN)){
            throw new RuntimeException("Access denied: only ADMIN can create quizzes");
        }

        List<Integer> questionsIds = quizInterface.getQuestionsForQuiz(categoryName, numOfQuestions).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionsIds);
        quiz.setUserId(userId);
        quizDao.save(quiz);

        userInterface.addQuizToUser(userId, quiz.getId());

        return new QuizResponse(quiz.getId(), quiz.getTitle(), userResponse.getName(), userResponse.getEmail(), accountResponse.getRole());
    }

    public List<QuestionResponse> getQuestionsByQuiz(String token, Integer id){
        var accountResponse = accountInterface.validate("Bearer " + token).getBody();
        if (accountResponse == null) {
            throw new RuntimeException("Invalid or expired token");
        }

        Quiz quiz = quizDao.findById(id).orElseThrow(()-> new QuizNotFoundException("Quiz with id "+id+" not found"));
        List<Integer> questionsIds = quiz.getQuestionIds();
        return quizInterface.getQuestionsFromIds(questionsIds).getBody();
    }

    public Integer submitQuiz(Integer quizId, Integer userId, List<Response> responses){
       Integer score =  quizInterface.getScore(responses).getBody();
//        var user = userInterface.getUserById(userId).getBody();
//        var quiz = quizDao.findById(quizId)
//                .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));
        ScoreDto scoreDto = new ScoreDto(userId, quizId, score);
        scoreInterface.saveScore(scoreDto);
        return score;
    }

    public List<ScoreDto> getUserScoresForQuiz(Integer userId, Integer quizId){
        return scoreInterface.getUserScoresForQuiz(userId, quizId).getBody();
    }

    ///  the method will retrieve all quizzes taken by a user
    public List<QuizResponse> getQuizzesForUser(Integer userId){
        var userResponse = userInterface.getUserById(userId).getBody();

       List<Quiz> quizzes =  quizDao.findByUserId(userId);
       return quizzes.stream()
               .map(q -> new QuizResponse(q.getId(), q.getTitle(), userResponse.getName(), userResponse.getEmail()))
               .toList();
    }

}
