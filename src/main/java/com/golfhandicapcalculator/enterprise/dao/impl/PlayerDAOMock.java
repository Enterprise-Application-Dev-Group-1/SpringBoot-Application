package com.golfhandicapcalculator.enterprise.dao.impl;

import com.golfhandicapcalculator.enterprise.dao.IPlayerDAO;
import com.golfhandicapcalculator.enterprise.dto.Player;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock implementation of the IPlayerDAO interface for testing and development purposes.
 * Uses an in-memory HashMap to store player data.
 * This implementation is annotated with @Repository to be detected by Spring's component scanning.
 */
@Repository
public class PlayerDAOMock implements IPlayerDAO {

    private final Map<Long, Player> playerTable = new HashMap<>();
    private Long nextId = 100L;

    /**
     * Constructs a new PlayerDAOMock instance and initializes it with sample data.
     * Creates one default player entry with ID 100.
     */
    public PlayerDAOMock() {
        Player p1 = new Player();
        p1.setPlayerId(nextId++);
        p1.setName("Alice Jones");
        p1.setHandicap(15.4);
        playerTable.put(p1.getPlayerId(), p1);
    }

    /**
     * Fetches all players from the data store.
     *
     * @return a list of all Player objects, empty list if none exist
     */
    @Override
    public List<Player> fetchAllPlayers() {
        return new ArrayList<>(playerTable.values());
    }

    /**
     * Fetches a player by their unique identifier.
     *
     * @param playerId the unique identifier of the player to retrieve
     * @return the Player object if found, null otherwise
     */
    @Override
    public Player fetchPlayer(Long playerId) {
        return playerTable.get(playerId);
    }

    /**
     * Saves a new player to the data store.
     * If the player does not have an ID or has ID 0, a new ID is assigned.
     *
     * @param player the Player object to save
     * @return the saved Player object with its assigned ID
     */
    @Override
    public Player savePlayer(Player player) {
        if (player.getPlayerId() == null || player.getPlayerId() == 0) {
            player.setPlayerId(nextId++);
        }
        playerTable.put(player.getPlayerId(), player);
        return player;
    }

    /**
     * Updates an existing player in the data store.
     *
     * @param player the Player object with updated information
     * @return the updated Player object if found and updated, null if the player ID does not exist
     */
    @Override
    public Player updatePlayer(Player player) {
        if (playerTable.containsKey(player.getPlayerId())) {
            playerTable.put(player.getPlayerId(), player);
            return player;
        }
        return null;
    }

    /**
     * Deletes a player from the data store.
     *
     * @param playerId the unique identifier of the player to delete
     */
    @Override
    public void deletePlayer(Long playerId) {
        playerTable.remove(playerId);
    }

    /**
     * Updates the handicap for a specific player.
     * Prints a confirmation message to the console after updating.
     *
     * @param playerId the unique identifier of the player
     * @param newHandicap the new handicap value to set
     */
    @Override
    public void updateHandicap(Long playerId, double newHandicap) {
        Player player = playerTable.get(playerId);
        if (player != null) {
            player.setHandicap(newHandicap);
            System.out.println("DAO Mock: Updated handicap for Player " + playerId + " to " + newHandicap);
        }
    }
}
