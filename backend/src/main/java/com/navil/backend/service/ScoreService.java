package com.navil.backend.service;

import com.navil.backend.model.Score;
import com.navil.backend.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    // Pre-CS
    public Score createScore(Score score) {
        return scoreRepository.save(score);
    }

    public Optional<Score> getScoreById(UUID scoreId) {
        return scoreRepository.findById(scoreId);
    }

    // CS Q1
    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    // CS Q2
    public List<Score> getRecentScores() {
        return scoreRepository.findAllByOrderByCreatedAtDesc();
    }

    // CS Q3
    public List<Score> getScoreAboveValue(Integer minValue) {
        return scoreRepository.findPointGreaterThan(minValue);
    }

    // CS Q4
    public List<Score> getLeaderboard(Integer limit) {
        return scoreRepository.findTopScores(limit);
    }

    // CS Q5
    public void deleteScore(UUID scoreId) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("Score with ID " + scoreId + " was not found"));
        scoreRepository.delete(score);
    }
}
