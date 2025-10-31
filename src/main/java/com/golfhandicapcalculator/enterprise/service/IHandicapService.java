package com.golfhandicapcalculator.enterprise.service;

import com.golfhandicapcalculator.enterprise.dto.Score;

import java.util.List;

public interface IHandicapService {
    /**
     * Calculate the player's handicap based on a list of scores.
     * @param scores the list of scores
     * @return the calculated handicap
     */
    double calculatePlayerHandicap(List<Score> scores);


}
