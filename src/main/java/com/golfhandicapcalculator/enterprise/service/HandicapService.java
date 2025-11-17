package com.golfhandicapcalculator.enterprise.service;

import com.golfhandicapcalculator.enterprise.GolfHandicapCalculator;
import com.golfhandicapcalculator.enterprise.dto.Score;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for calculating a player's golf handicap based on a list of scores.
 * <p>
 * This class uses the {@link GolfHandicapCalculator} to compute the player's handicap using
 * the scores of recent rounds, adjusting for the course par and slope.
 * </p>
 */

@Service
public class HandicapService {

    /**
     * The calculator used to compute the golf handicap.
     * <p>
     * This is an instance of the {@link GolfHandicapCalculator} class, which contains the
     * logic for calculating a golf handicap based on score, par, and slope values.
     * </p>
     */

    private final GolfHandicapCalculator calculator;

    /**
     * Constructs a new instance of the {@link HandicapService}.
     * <p>
     * The constructor accepts a {@link GolfHandicapCalculator} instance and ensures that
     * it is not null, throwing an {@link IllegalArgumentException} if it is.
     * </p>
     *
     * @param calculator the {@link GolfHandicapCalculator} used to compute the player's handicap
     * @throws IllegalArgumentException if the {@link GolfHandicapCalculator} is null
     */

    public HandicapService(GolfHandicapCalculator calculator) {
        if (calculator == null) {
            throw new IllegalArgumentException("GolfHandicapCalculator must not be null");
        }
        this.calculator = calculator;
    }

    /**
     * Calculates a player's golf handicap based on a list of scores.
     * <p>
     * This method processes the provided list of {@link Score} objects, extracting the score,
     * par, and slope for each round, and uses the {@link GolfHandicapCalculator} to compute
     * the overall handicap. If the list of scores is empty or null, it returns a handicap of 0.0.
     * </p>
     *
     * @param scores a list of {@link Score} objects containing the player's golf scores,
     *               course par, and slope for each round
     * @return the calculated golf handicap for the player, or 0.0 if the scores list is null or empty
     */

    public double calculatePlayerHandicap(List<Score> scores) {
        if (scores == null || scores.isEmpty()) {
            return 0.0;
        }

        // Extract score, par, and slope values from the scores list
        double[] scoreValues = scores.stream().mapToDouble(Score::getScore).toArray();
        double[] parValues = scores.stream().mapToDouble(Score::getPar).toArray();
        double[] slopeValues = scores.stream().mapToDouble(Score::getSlope).toArray();

        // Use the calculator to compute the handicap
        Double handicap = calculator.calculateHandicap(scoreValues, parValues, slopeValues);
        return handicap != null ? handicap : 0.0;
    }
}
