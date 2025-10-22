package com.golfhandicapcalculator.enterprise.dao;

import com.golfhandicapcalculator.enterprise.dto.Score;

import java.util.List;

public interface IScoreDAO {
    Score fetchScoreById(Long scoreId);

    List<Score> fetchScoresByPlayerId(Long playerId);

    Score saveScore(Score score);

    Score updateScore(Score score);

    void deleteScoresByPlayerId(Long playerId);
}