package com.example.question_service.dao;


import com.example.question_service.model.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class QuestionDaoTest {

    private final QuestionDao questionDao;


    @Autowired
    QuestionDaoTest(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @BeforeEach
    void setUp(){
        Question q = new Question();
        q.setQuestionTitle("What is the capital of France?");
        q.setOption1("Berlin");
        q.setOption2("Madrid");
        q.setOption3("Paris");
        q.setOption4("Rome");
        q.setRightAnswer("Paris");
        q.setCategory("Geography");
        questionDao.save(q);
    }

    @Test
    void testFindQuestionByCategory(){
        var questions = questionDao.findQuestionByCategory("Geography");
        assertThat(questions).isNotNull();
        assertThat( questions.size()).isEqualTo(1);
        assertThat(questions.get(0).getQuestionTitle()).isEqualTo("What is the capital of France?");
    }

    @Test
    void testFindRandomQuestionsByCategory(){
        var questionIds = questionDao.findRandomQuestionsByCategory("Geography", 1);
        assertThat(questionIds).isNotNull();
        assertThat(questionIds.size()).isEqualTo(1);
    }

    @AfterEach
    void tearDown(){
        questionDao.deleteAll();
    }

}