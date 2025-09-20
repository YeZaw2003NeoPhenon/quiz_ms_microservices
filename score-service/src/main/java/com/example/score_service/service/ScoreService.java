package com.example.score_service.service;

import com.example.score_service.dao.ScoreDao;
import com.example.score_service.dto.ScoreDto;
import com.example.score_service.model.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreDao scoreDao;

    public ScoreDto saveScore(ScoreDto scoreDto) {
        Score score = new Score();
        score.setUserId(scoreDto.getUserId());
        score.setQuizId(scoreDto.getQuizId());
        score.setScore(scoreDto.getScore());
        Score savedScore = scoreDao.save(score);
        return new ScoreDto(savedScore.getUserId(), savedScore.getQuizId(), savedScore.getScore());
    }

    public List<ScoreDto> getUserScoreForQuiz(Integer userId, Integer quizId) {
        return scoreDao.findByUserIdAndQuizId(userId, quizId)
                .stream()
                .map(score -> new ScoreDto(score.getUserId(), score.getQuizId(), score.getScore())).collect(Collectors.toList());
    }

    public List<ScoreDto> getScoresByUserId(Integer userId) {
        return scoreDao.findByUserId(userId)
                .stream()
                .map(score -> new ScoreDto(score.getUserId(), score.getQuizId(), score.getScore())).collect(Collectors.toList());
    }

}
