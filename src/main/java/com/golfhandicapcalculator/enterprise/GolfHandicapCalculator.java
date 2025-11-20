package com.golfhandicapcalculator.enterprise;

import org.springframework.stereotype.Service;

@Service
public class GolfHandicapCalculator {

    public Double calculateHandicap(double[] scores, double[] pars, double[] slopes) {
        if (scores == null || pars == null) {
            return null;
        }

        int count;
        if (slopes == null) {
            count = Math.min(scores.length, pars.length);
        } else {
            count = Math.min(Math.min(scores.length, pars.length), slopes.length);
        }

        if (count == 0) {
            return null;
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
}
