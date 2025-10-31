package com.golfhandicapcalculator.enterprise.service;

import com.golfhandicapcalculator.enterprise.dto.Score;

import java.util.List;

public interface IHandicapService {
    
    /**
     * Calculate handicap based on scores, pars, and slopes.
     * @param scores the array of scores
     * @param pars the array of par values
     * @param slopes the array of slope values
     * @return the calculated handicap
     */
    double calculateHandicap(double[] scores, double[] pars, double[] slopes);
    
    /**
     * Calculate the player's handicap based on a list of scores.
     * @param scores the list of scores
     * @return the calculated handicap
     */
    double calculatePlayerHandicap(List<Score> scores);


}
