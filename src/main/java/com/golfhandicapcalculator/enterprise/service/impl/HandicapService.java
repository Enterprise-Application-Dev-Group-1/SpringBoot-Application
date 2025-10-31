package com.golfhandicapcalculator.enterprise.service.impl;

import com.golfhandicapcalculator.enterprise.dto.Score;
import com.golfhandicapcalculator.enterprise.service.IHandicapService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandicapService implements IHandicapService{

    @Override
    public double calculateHandicap(double[] scores, double[] pars, double[] slopes) {
        if (scores == null || pars == null) {
            return 0.0;
        }

        int count;
        if (slopes == null) {
            count = Math.min(scores.length, pars.length);
        } else {
            count = Math.min(Math.min(scores.length, pars.length), slopes.length);
        }

        if (count == 0) {
            return 0.0;
        }

        double totalDifferential = 0;
        for (int i = 0; i < count; i++) {
            double slope = 113; // default baseline
            if (slopes != null && slopes.length > i && slopes[i] >= 55 && slopes[i] <= 155) {
                slope = slopes[i];
            }
            double differential = ((scores[i] - pars[i]) * 113) / slope;
            totalDifferential += differential;
        }

        double average = totalDifferential / count;
        return Math.round(average * 100.0) / 100.0; // round to 2 decimals
    }

    @Override
    public double calculatePlayerHandicap(List<Score> scores) {
        if (scores == null || scores.isEmpty()) {
            return 0.0;
        }

        double[] scoreValues = scores.stream().mapToDouble(Score::getScore).toArray();
        double[] parValues = scores.stream().mapToDouble(Score::getPar).toArray();
        double[] slopeValues = scores.stream().mapToDouble(Score::getSlope).toArray();

        double handicap = calculateHandicap(scoreValues, parValues, slopeValues);
        return handicap;
    }
}