package com.golfhandicapcalculator.enterprise.dao.impl;

import com.golfhandicapcalculator.enterprise.dao.IScoreDAO;
import com.golfhandicapcalculator.enterprise.dao.ScoreRepository;
import com.golfhandicapcalculator.enterprise.dto.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class ScoreDAOJPA implements IScoreDAO {

    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public Score fetchScoreById(Long scoreId) {
        return scoreRepository.findById(scoreId).orElse(null);
    }

    @Override
    public List<Score> fetchScoresByPlayerId(Long playerId) {
        return scoreRepository.findByPlayer_PlayerId(playerId);
    }

    @Override
    public Score saveScore(Score score) {
        return scoreRepository.save(score);
    }

    @Override
    public Score updateScore(Score score) {
        if (scoreRepository.existsById(score.getScoreId())) {
            return scoreRepository.save(score);
        }
        return null;
    }

    @Override
    public void deleteScoresByPlayerId(Long playerId) {
        List<Score> scores = scoreRepository.findByPlayer_PlayerId(playerId);
        scoreRepository.deleteAll(scores);
    }
}
