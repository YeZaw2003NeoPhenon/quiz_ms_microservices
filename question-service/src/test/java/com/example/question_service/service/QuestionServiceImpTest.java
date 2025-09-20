package com.example.question_service.service;

import com.example.question_service.dao.QuestionDao;
import com.example.question_service.dto.QuestionResponse;
import com.example.question_service.model.Question;
import com.example.question_service.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImpTest {

    @InjectMocks
    private QuestionServiceImp questionServiceImp;

    @Mock
    private QuestionDao questionDao;

    Question question;

    QuestionResponse questionResponse;

    Question question2;

    QuestionResponse questionResponse2;

    @BeforeEach
    void setUp(){
        question = new Question(1, "What is Java?", "A programming language", "A coffee brand", "An island", "A car model", "A programming language", "Technology");
        questionResponse = new QuestionResponse(1, "What is Java?", "A programming language", "A coffee brand", "An island", "A car model", "Technology");

        question2 = new Question(2, "What is Spring Boot?", "A framework", "A database", "A server", "A language", "A framework", "Technology");
        questionResponse2 = new QuestionResponse(2, "What is Spring Boot?", "A framework", "A database", "A server", "A language", "Technology");
    }

    @Test
    @DisplayName("Should Return List of Question Ids")
    void testGetQuestionForQuiz(){
        when(questionDao.findRandomQuestionsByCategory("Technology", 1)).thenReturn(Collections.singletonList(1));
        List<Integer> ids =  questionServiceImp.getQuestionsForQuiz("Technology", 1);
        assertThat(ids).isNotNull();
        assertThat(ids.size()).isEqualTo(1);
        verify(questionDao, times(1)).findRandomQuestionsByCategory("Technology", 1);
    }

    @Test
    @DisplayName("Should Return List of Questions from Ids")
    void testGetQuestionsFromIds(){
        when(questionDao.findById(1)).thenReturn(Optional.of(question));
        when(questionDao.findById(2)).thenReturn(Optional.of(question2));
        List<QuestionResponse> questions = questionServiceImp.getQuestionsFromIds(List.of(1, 2));
        assertThat(questions).isNotNull();
        assertThat(questions.size()).isEqualTo(2);
        assertThat(questions.get(0)).isEqualTo(questionResponse);
        assertThat(questions.get(1)).isEqualTo(questionResponse2);

        verify(questionDao).findById(1);
        verify(questionDao).findById(2);
    }

    @Test
    @DisplayName("Should Return Accurate Score Based on Responses")
    void testGetScore(){
        when(questionDao.findById(1)).thenReturn(Optional.of(question));
        when(questionDao.findById(2)).thenReturn(Optional.of(question2));

        List<Response> responses = List.of(new Response(1, "A programming language"),
                new Response(2, "A database"));
        int answers = questionServiceImp.getScore(responses);

        assertThat(answers).isEqualTo(1);
        verify(questionDao).findById(1);
    }

}