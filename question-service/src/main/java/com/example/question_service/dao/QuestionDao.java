package com.example.question_service.dao;

import com.example.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    @Query("SELECT q FROM Question q WHERE LOWER(q.category) LIKE LOWER(CONCAT('%', :category, '%'))")
    List<Question> findQuestionByCategoryContainingIgnoreCase(String category);

    @Query("SELECT q.id FROM Question q WHERE q.category = :category ORDER BY RANDOM() LIMIT :numberOfQuestions")
    List<Integer> findRandomQuestionsByCategory(String category, int numberOfQuestions);

}
