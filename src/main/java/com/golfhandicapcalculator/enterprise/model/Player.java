package com.golfhandicapcalculator.enterprise.model;

import javax.persistence.*;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playerId;

    private String name;
    private double handicap;

    public Player() {} // Required by JPA

    public Player(String name, double handicap) {
        this.name = name;
        this.handicap = handicap;
    }

    // Getters and setters
    public int getPlayerId() { return playerId; }
    public String getName() { return name; }
    public double getHandicap() { return handicap; }
    public void setName(String name) { this.name = name; }
    public void setHandicap(double handicap) { this.handicap = handicap; }
}
