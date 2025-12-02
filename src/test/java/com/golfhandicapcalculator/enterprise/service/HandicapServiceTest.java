package com.golfhandicapcalculator.enterprise.service;

import com.golfhandicapcalculator.enterprise.GolfHandicapCalculator;
import com.golfhandicapcalculator.enterprise.dto.Score;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HandicapServiceTest {

    @Mock
    private GolfHandicapCalculator calculator;

    private HandicapService handicapService;

    @Before
    public void setUp() {
        handicapService = new HandicapService(calculator);
    }

    @Test
    public void testCalculatePlayerHandicap_withValidScores_returnsHandicap() {
        // Arrange
        List<Score> scores = createScores(
                new int[]{85, 90, 88},
                new int[]{72, 72, 72},
                new int[]{113, 113, 113}
        );
        when(calculator.calculateHandicap(any(double[].class), any(double[].class), any(double[].class)))
                .thenReturn(15.5);

        // Act
        double result = handicapService.calculatePlayerHandicap(scores);

        // Assert
        assertEquals(15.5, result, 0.01);
        verify(calculator, times(1)).calculateHandicap(any(double[].class), any(double[].class), any(double[].class));
    }

    @Test
    public void testCalculatePlayerHandicap_withEmptyScores_returnsZero() {
        // Act
        double result = handicapService.calculatePlayerHandicap(new ArrayList<>());

        // Assert
        assertEquals(0.0, result, 0.01);
        verify(calculator, never()).calculateHandicap(any(), any(), any());
    }

    @Test
    public void testCalculatePlayerHandicap_whenCalculatorReturnsNull_returnsZero() {
        // Arrange
        List<Score> scores = createScores(
                new int[]{85},
                new int[]{72},
                new int[]{113}
        );
        when(calculator.calculateHandicap(any(double[].class), any(double[].class), any(double[].class)))
                .thenReturn(null);

        // Act
        double result = handicapService.calculatePlayerHandicap(scores);

        // Assert
        assertEquals(0.0, result, 0.01);
    }

    // Helper method to create Score objects
    private List<Score> createScores(int[] scoreValues, int[] parValues, int[] slopeValues) {
        List<Score> scores = new ArrayList<>();
        for (int i = 0; i < scoreValues.length; i++) {
            Score score = new Score();
            score.setScore(scoreValues[i]);
            score.setPar(parValues[i]);
            score.setSlope(slopeValues[i]);
            scores.add(score);
        }
        return scores;
    }
}
