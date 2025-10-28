package com.golfhandicapcalculator.enterprise;

import org.springframework.stereotype.Service;

/**
 * Service class that handles the logic for calculating golf handicaps
 * based on user input scores, pars, and slope ratings.
 *
 * <p>This class applies validation to ensure consistent and accurate calculations.
 * It rounds the resulting handicap average to two decimal places.</p>
 *
 * @author Brian Faught
 * @version 1.1
 */
@Service
public class GolfHandicapCalculator {

    /**
     * Calculates the handicap average using provided score, par, and slope values.
     *
     * @param scores array of score values entered by the user
     * @param pars array of par values that correspond to each score
     * @param slopes optional array of slope ratings; defaults to 113 if missing
     * @return the calculated handicap average rounded to two decimal places
     * @throws InvalidInputException if any of the input arrays are invalid
     */
    public double calculateHandicap(double[] scores, double[] pars, double[] slopes) {
        // Basic input validation
        if (scores == null || pars == null) {
            throw new InvalidInputException("Scores and pars cannot be null.");
        }

        if (scores.length == 0 || pars.length == 0) {
            throw new InvalidInputException("At least one score and par value is required.");
        }

        if (scores.length != pars.length) {
            throw new InvalidInputException("Scores and pars must have the same number of entries.");
        }

        double totalDifferential = 0.0;

        // Calculate average handicap differential
        for (int i = 0; i < scores.length; i++) {
            double slope = (slopes != null && slopes.length > i) ? slopes[i] : 113.0;
            totalDifferential += ((scores[i] - pars[i]) * 113.0) / slope;
        }

        // Average and round to two decimal places
        double average = totalDifferential / scores.length;
        return Math.round(average * 100.0) / 100.0;
    }
}
