package com.example.score_service.dao;

import com.example.score_service.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreDao extends JpaRepository<Score,Integer> {
    List<Score> findByUserId(Integer userId);
    List<Score> findByQuizId(Integer quizId);
    List<Score> findByUserIdAndQuizId(Integer userId, Integer quizId);
}
