package com.golfhandicapcalculator.enterprise;

import org.junit.Test;
import static org.junit.Assert.*;

public class GolfHandicapCalculatorTest {

    private final GolfHandicapCalculator calculator = new GolfHandicapCalculator();

    @Test
    public void testCalculateHandicap_withValidInputs() {
        double[] scores = {89, 85, 90};
        double[] pars = {72, 72, 72};
        double[] slopes = {121, 113, 130};

        // Expected differentials:
        // ((89 - 72) * 113 / 121) = 15.87
        // ((85 - 72) * 113 / 113) = 13.00
        // ((90 - 72) * 113 / 130) = 15.63
        // Avg = (15.87 + 13.00 + 15.63) / 3 = 14.83

        Double result = calculator.calculateHandicap(scores, pars, slopes);
        assertEquals(14.83, result, 0.01);
    }

    @Test
    public void testCalculateHandicap_withDefaultSlope() {
        double[] scores = {85, 90};
        double[] pars = {72, 72};
        double[] slopes = {0, 0}; // zero slope means default to 113

        Double result = calculator.calculateHandicap(scores, pars, slopes);
        // ((85-72)*113/113 + (90-72)*113/113)/2 = (13 + 18)/2 = 15.5
        assertEquals(15.5, result, 0.01);
    }

    @Test
    public void testCalculateHandicap_withNullSlopeArray() {
        double[] scores = {72};
        double[] pars = {72};

        Double result = calculator.calculateHandicap(scores, pars, null);
        // ((72 - 72)*113/113) = 0.0
        assertEquals(0.0, result, 0.01);
    }

    @Test
    public void testCalculateHandicap_withUnequalLengths() {
        double[] scores = {85, 90, 88};
        double[] pars = {72, 72};
        double[] slopes = {113, 113, 113};

        // Only first two rounds used:
        // ((85-72)*113/113 + (90-72)*113/113)/2 = (13 + 18)/2 = 15.5

        Double result = calculator.calculateHandicap(scores, pars, slopes);
        assertEquals(15.5, result, 0.01);
    }

    @Test
    public void testCalculateHandicap_withEmptyArrays() {
        Double result = calculator.calculateHandicap(new double[]{}, new double[]{}, new double[]{});
        assertNull(result);
    }

    @Test
    public void testCalculateHandicap_withNullArrays() {
        assertNull(calculator.calculateHandicap(null, new double[]{70}, new double[]{113}));
        assertNull(calculator.calculateHandicap(new double[]{72}, null, new double[]{113}));
        // Slopes null defaults to 113, so result is 0.0, not null
        assertEquals(0.0, calculator.calculateHandicap(new double[]{72}, new double[]{72}, null), 0.01);
    }

    @Test
    public void testCalculateHandicap_withNegativeScores() {
        double[] scores = {65, 68};
        double[] pars = {72, 72};
        double[] slopes = {113, 113};

        // ((65-72)*113/113 + (68-72)*113/113)/2 = (-7 + -4)/2 = -5.5

        Double result = calculator.calculateHandicap(scores, pars, slopes);
        assertEquals(-5.5, result, 0.01);
    }
}
