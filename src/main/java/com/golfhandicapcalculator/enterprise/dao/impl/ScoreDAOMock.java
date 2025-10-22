package com.golfhandicapcalculator.enterprise.dao.impl;

import com.golfhandicapcalculator.enterprise.dao.IScoreDAO;
import com.golfhandicapcalculator.enterprise.dto.Score;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ScoreDAOMock implements IScoreDAO {

    // Mock database table: Map<ScoreId, ScoreObject>
    private final Map<Long, Score> scoreTable = new ConcurrentHashMap<>();
    private Long nextId = 200L; // Starting ID for mock data

    // Constructor to seed mock data (assuming player 100 exists from PlayerDAOMock)
    public ScoreDAOMock() {
        Score s1 = new Score();
        s1.setScoreId(nextId++);
        s1.setPlayerId(100L); // Link to Alice Jones
        s1.setScore(85);
        s1.setPar(72);
        s1.setSlope(120);
        scoreTable.put(s1.getScoreId(), s1);
    }

    @Override
    public Score fetchScoreById(Long scoreId) {
        return scoreTable.get(scoreId);
    }

    @Override
    public List<Score> fetchScoresByPlayerId(Long playerId) {
        // Simulate a database query by filtering the map
        return scoreTable.values().stream()
                .filter(s -> s.getPlayerId().equals(playerId))
                .collect(Collectors.toList());
    }

    @Override
    public Score saveScore(Score score) {
        // Simulates DB generating a new ID
        if (score.getScoreId() == null || score.getScoreId() == 0) {
            score.setScoreId(nextId++);
        }
        scoreTable.put(score.getScoreId(), score);
        return score;
    }

    @Override
    public Score updateScore(Score score) {
        if (scoreTable.containsKey(score.getScoreId())) {
            scoreTable.put(score.getScoreId(), score);
            return score;
        }
        return null; // Simulate record not found
    }

    @Override
    public void deleteScoresByPlayerId(Long playerId) {
        // Remove all scores linked to the player
        scoreTable.values().removeIf(score -> score.getPlayerId().equals(playerId));
        System.out.println("DAO Mock: Deleted all scores for Player " + playerId);
    }
}