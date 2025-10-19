package com.golfhandicapcalculator.enterprise;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GolfHandicapCalculatorTest {

    private final GolfHandicapCalculator calculator = new GolfHandicapCalculator();

    @Test
    void testCalculateHandicap_withValidInputs() {
        double[] scores = {72, 75, 70};
        double[] pars = {70, 70, 70};

        Double result = calculator.calculateHandicap(scores, pars);

        assertEquals(2.33, result, 0.01);
    }

    @Test
    void testCalculateHandicap_withUnequalArrayLengths() {
        double[] scores = {72, 75};
        double[] pars = {70, 70, 70};

        Double result = calculator.calculateHandicap(scores, pars);

        assertEquals(3.5, result, 0.01);
    }

    @Test
    void testCalculateHandicap_withEmptyArrays() {
        double[] scores = {};
        double[] pars = {};

        Double result = calculator.calculateHandicap(scores, pars);

        assertNull(result);
    }

    @Test
    void testCalculateHandicap_withNullInputs() {
        Double result1 = calculator.calculateHandicap(null, new double[]{70});
        Double result2 = calculator.calculateHandicap(new double[]{72}, null);

        assertNull(result1);
        assertNull(result2);
    }

    @Test
    void testCalculateHandicap_withNegativeScores() {
        double[] scores = {-10, -5};
        double[] pars = {0, 0};

        Double result = calculator.calculateHandicap(scores, pars);

        assertEquals(-7.5, result, 0.01);
    }
}
