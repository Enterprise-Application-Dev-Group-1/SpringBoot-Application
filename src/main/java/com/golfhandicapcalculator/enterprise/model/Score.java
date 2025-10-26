package com.golfhandicapcalculator.enterprise.model;

import javax.persistence.*;

@Entity
@Table(name = "score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scoreId;

    private int score;
    private int par;
    private int slope;

    @ManyToOne
    @JoinColumn(name = "player_id")  // Ensures a valid player reference
    private Player player;

    // Default constructor required by JPA
    public Score() {}

    // Constructor to initialize Score with player association
    public Score(int score, int par, int slope, Player player) {
        this.score = score;
        this.par = par;
        this.slope = slope;
        this.player = player;
    }

    // Getters and setters
    public int getScoreId() { return scoreId; }
    public int getScore() { return score; }
    public int getPar() { return par; }
    public int getSlope() { return slope; }
    public Player getPlayer() { return player; }

    public void setScore(int score) { this.score = score; }
    public void setPar(int par) { this.par = par; }
    public void setSlope(int slope) { this.slope = slope; }
    public void setPlayer(Player player) { this.player = player; }
}
