package com.example.quiz_service.service;

import com.example.quiz_service.dao.QuizDao;
import com.example.quiz_service.dao.ScoreDto;
import com.example.quiz_service.dto.*;
import com.example.quiz_service.feign.AccountInterface;
import com.example.quiz_service.feign.QuizInterface;
import com.example.quiz_service.feign.ScoreInterface;
import com.example.quiz_service.feign.UserInterface;
import com.example.quiz_service.model.Quiz;
import com.example.quiz_service.model.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @InjectMocks
    private QuizService quizService;

    @Mock
    private QuizDao quizDao;

    @Mock
    private QuizInterface quizInterface;

    @Mock
    private UserInterface userInterface;

    @Mock
    private AccountInterface accountInterface;

    @Mock
    private ScoreInterface scoreInterface;

    @Test
    @DisplayName("Should create a new quiz")
    void testCreateQuiz() {
        when(accountInterface.validate(anyString())).thenReturn(ResponseEntity.ok(new AccountResponse("neo@gmail.com", Role.ADMIN)));

        when(userInterface.getUserById(1)).thenReturn(ResponseEntity.ok(new UserResponse("Neo", Gender.MALE, "neo@example.com")));

        when(quizInterface.getQuestionsForQuiz("Mathematics", 2))
                .thenReturn(ResponseEntity.ok(Arrays.asList(1, 2)));

        when(quizDao.save(any(Quiz.class))).thenAnswer(Invocation -> {
            Quiz quiz = Invocation.getArgument(0);
            quiz.setId(1);
            return quiz;
        });

        QuizResponse response = quizService.createQuiz("Bearer dummy-token", "Mathematics", 2, "Sample Quiz", 1);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getTitle()).isEqualTo("Sample Quiz");
        assertThat(response.getRole()).isEqualTo(Role.ADMIN);
        assertThat(response.getUserName()).isEqualTo("Neo");
        assertThat(response.getUserEmail()).isEqualTo("neo@gmail.com");

        verify(accountInterface).validate("Bearer dummy-token");
        verify(userInterface).getUserById(1);
        verify(quizInterface).getQuestionsForQuiz("Mathematics", 2);
        verify(quizDao).save(any(Quiz.class));
    }

    @Test
    @DisplayName("Should fetch quiz questions by quiz ID")
    void testQuizQuestion(){
        Quiz quiz = new Quiz();
        quiz.setId(1);
        quiz.setTitle("Sample Quiz");
        quiz.setQuestionIds(Arrays.asList(1,2));

        when(accountInterface.validate(anyString())).thenReturn(ResponseEntity.ok(new AccountResponse("neo@gmail.com",Role.ADMIN)));
        when(quizDao.findById(any(Integer.class))).thenReturn(Optional.of(quiz));

        when(quizInterface.getQuestionsFromIds(Arrays.asList(1,2)))
                .thenReturn(ResponseEntity.ok(Arrays.asList(
                        new QuestionResponse(1,"What is 2 + 2?", "3", "4", "5", "6"),
                        new QuestionResponse(2, "What is the smallest prime number?", "2", "3", "4", "5")
                )));

       List<QuestionResponse> responses = quizService.getQuestionsByQuiz("Bearer testableToken", 1);
       assertThat(responses).isNotNull();
       assertThat(responses.size()).isEqualTo(2);
       assertThat(responses.get(0).getQuestionTitle()).isEqualTo("What is 2 + 2?");
       assertThat(responses.get(1).getQuestionTitle()).isEqualTo("What is the smallest prime number?");
       verify(quizDao).findById(1);
       verify(quizInterface).getQuestionsFromIds(Arrays.asList(1,2));
       verify(accountInterface).validate(any());
    }

    @Test
    @DisplayName("Should calculate quiz results")
    void testCalculateResults(){
        when(quizInterface.getScore(any())).thenReturn(ResponseEntity.ok(2));
        ScoreDto scoreDto = new ScoreDto(1,1,2);

        when(scoreInterface.saveScore(any())).thenReturn(ResponseEntity.ok(scoreDto));

       int result =  quizService.submitQuiz(1, 1 , Arrays.asList(
                                                new Response(1,"4"),
                                                new Response(2,"2")
                                                ));
        assertThat(result).isEqualTo(2);
        verify(quizInterface).getScore(any());
        verify(scoreInterface).saveScore(scoreDto);
    }

}