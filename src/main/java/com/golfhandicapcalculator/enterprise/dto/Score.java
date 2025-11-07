package com.golfhandicapcalculator.enterprise.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the score of a player in a golf game.
 * <p>
 * This class is used to transfer the details of a player's score for a particular round of golf, including
 * the player's score, the par value for the hole/course, the score ID, and the slope rating of the course.
 * </p>
 */
public class Score {

    /**
     * The ID of the player who recorded the score.
     */
    @Setter
    @Getter
    private Long playerId;

    /**
     * The actual score the player achieved in the round (e.g., the number of strokes).
     */
    private int score;

    /**
     * The par value of the hole/course. Par is the expected number of strokes a skilled player should take.
     */
    private int par;

    /**
     * The unique identifier for the score entry.
     * <p>
     * This ID is typically generated and assigned when the score is saved in the database.
     * </p>
     */
    private Long scoreId;

    /**
     * The slope rating of the course, which is used to calculate the difficulty of the course.
     * A higher slope indicates a more difficult course.
     */
    private int slope;

    /**
     * Gets the player's score for the round.
     *
     * @return the score achieved by the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score for the round.
     *
     * @param score the score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the par value for the course/hole.
     *
     * @return the par value.
     */
    public int getPar() {
        return par;
    }

    /**
     * Sets the par value for the course/hole.
     *
     * @param par the par value to set.
     */
    public void setPar(int par) {
        this.par = par;
    }

    /**
     * Gets the unique identifier for the score.
     *
     * @return the score ID.
     */
    public Long getScoreId() {
        return scoreId;
    }

    /**
     * Sets the unique identifier for the score.
     *
     * @param scoreId the score ID to set.
     */
    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    /**
     * Gets the slope rating of the course.
     *
     * @return the slope rating.
     */
    public int getSlope() {
        return slope;
    }

    /**
     * Sets the slope rating of the course.
     *
     * @param slope the slope rating to set.
     */
    public void setSlope(int slope) {
        this.slope = slope;
    }
}
