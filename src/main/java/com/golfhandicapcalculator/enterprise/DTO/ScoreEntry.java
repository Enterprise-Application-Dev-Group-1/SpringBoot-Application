package com.golfhandicapcalculator.enterprise.DTO;

public class ScoreEntry {
    private double score;
    private double par;

    public ScoreEntry() {}
    public ScoreEntry(double score, double par) { this.score = score; this.par = par; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public double getPar() { return par; }
    public void setPar(double par) { this.par = par; }
}
