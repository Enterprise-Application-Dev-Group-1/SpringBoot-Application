package com.golfhandicapcalculator.enterprise.service;

import com.golfhandicapcalculator.enterprise.GolfHandicapCalculator;
import com.golfhandicapcalculator.enterprise.dto.Score;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HandicapService {

    private final GolfHandicapCalculator calculator;

    public HandicapService(GolfHandicapCalculator calculator) {
        if (calculator == null) {
            throw new IllegalArgumentException("GolfHandicapCalculator must not be null");
        }
        this.calculator = calculator;
    }

    public double calculatePlayerHandicap(List<Score> scores) {
        if (scores == null || scores.isEmpty()) {
            return 0.0;
        }

        double[] scoreValues = scores.stream().mapToDouble(Score::getScore).toArray();
        double[] parValues = scores.stream().mapToDouble(Score::getPar).toArray();
        double[] slopeValues = scores.stream().mapToDouble(Score::getSlope).toArray();

        Double handicap = calculator.calculateHandicap(scoreValues, parValues, slopeValues);
        return handicap != null ? handicap : 0.0;
    }
}
