package com.golfhandicapcalculator.enterprise.persistence;

import com.golfhandicapcalculator.enterprise.dao.IScoreDAO;
import com.golfhandicapcalculator.enterprise.dao.ScoreRepository;
import com.golfhandicapcalculator.enterprise.dto.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class JpaScoreDAO implements IScoreDAO {

    private final ScoreRepository scoreRepository;

    @Autowired
    public JpaScoreDAO(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public Score fetchScoreById(Long scoreId) {
        return scoreRepository.findById(scoreId).orElse(null);
    }

    @Override
    public List<Score> fetchScoresByPlayerId(Long playerId) {
        return scoreRepository.findByPlayerId(playerId);
    }

    @Override
    public Score saveScore(Score score) {
        return scoreRepository.save(score);
    }

    @Override
    public Score updateScore(Score score) {
        if (score.getScoreId() == null) return null;
        if (scoreRepository.existsById(score.getScoreId())) {
            return scoreRepository.save(score);
        }
        return null;
    }

    @Override
    public void deleteScoresByPlayerId(Long playerId) {
        List<Score> scores = scoreRepository.findByPlayerId(playerId);
        scoreRepository.deleteAll(scores);
    }
}
