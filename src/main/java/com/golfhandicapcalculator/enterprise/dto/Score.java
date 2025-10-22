package com.golfhandicapcalculator.enterprise.dto;

import lombok.Getter;
import lombok.Setter;

public class Score {
    @Setter
    @Getter
    private Long playerId;
    private int score;
    private int par;
    private Long scoreId;
    private int slope;


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public int getSlope() {
        return slope;
    }

    public void setSlope(int slope) {
        this.slope = slope;
    }
}
