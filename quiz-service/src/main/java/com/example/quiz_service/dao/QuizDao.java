package com.example.quiz_service.dao;

import com.example.quiz_service.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {

    @Query("SELECT q FROM Quiz q WHERE q.userId = :userId")
    List<Quiz> findByUserId(Integer userId);
}
