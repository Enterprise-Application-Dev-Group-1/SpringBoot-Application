package model;

public class Score {
    private final int scoreId;
    private final int score;
    private final int par;
    private final int slope;

    public Score(int scoreId, int score, int par, int slope) {
        this.scoreId = scoreId;
        this.score = score;
        this.par = par;
        this.slope = slope;
    }

    public int getScoreId() {
        return scoreId;
    }

    public int getScore() {
        return score;
    }

    public int getPar() {
        return par;
    }

    public int getSlope() {
        return slope;
    }
}