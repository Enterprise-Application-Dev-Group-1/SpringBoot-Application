package model;

public class Player {
    private final int playerId;
    private final String name;
    private final double handicap;

    public Player(int playerId, String name, double handicap) {
        this.playerId = playerId;
        this.name = name;
        this.handicap = handicap;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public double getHandicap() {
        return handicap;
    }
}
