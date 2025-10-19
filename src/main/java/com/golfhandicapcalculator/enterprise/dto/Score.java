package com.golfhandicapcalculator.enterprise.dto;

public class Score {
    private int playerId;
    private int score;
    private int par;
    private int scoreId;
    private int slope;


    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

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

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getSlope() {
        return slope;
    }

    public void setSlope(int slope) {
        this.slope = slope;
    }
}
