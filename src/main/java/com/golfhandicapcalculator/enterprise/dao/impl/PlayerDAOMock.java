package com.golfhandicapcalculator.enterprise.dao.impl;

import com.golfhandicapcalculator.enterprise.dao.IPlayerDAO;
import com.golfhandicapcalculator.enterprise.dto.Player;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A mock implementation of the {@link IPlayerDAO} interface.
 * This class simulates interaction with a database for managing {@link Player} objects using an in-memory {@link Map}.
 * It provides basic CRUD operations to fetch, save, update, and delete player data.
 *
 * <p>This is intended for testing or development purposes, and does not persist data beyond the application runtime.</p>
 *
 * @see IPlayerDAO
 */

@Repository // Marks this class as a Spring repository/DAO
public class PlayerDAOMock implements IPlayerDAO {

    // Mock database table: Map<PlayerId, PlayerObject>
    private final Map<Long, Player> playerTable = new HashMap<>();
    private Long nextId = 100L; // Starting ID for mock data

    /**
     * Constructor that seeds the mock database with a sample player.
     * Initializes the player table with a player named "Alice Jones".
     */

    public PlayerDAOMock() {
        Player p1 = new Player();
        p1.setPlayerId(nextId++);
        p1.setName("Alice Jones");
        p1.setHandicap(15.4);
        playerTable.put(p1.getPlayerId(), p1);
    }

    /**
     * Fetches all players from the mock database.
     *
     * @return a list of all {@link Player} objects in the mock database.
     */

    @Override
    public List<Player> fetchAllPlayers() {
        return new ArrayList<>(playerTable.values());
    }

    /**
     * Fetches a single player based on the player ID.
     *
     * @param playerId the ID of the player to fetch.
     * @return the {@link Player} object corresponding to the given playerId, or {@code null} if the player is not found.
     */

    @Override
    public Player fetchPlayer(Long playerId) {
        return playerTable.get(playerId);
    }

    /**
     * Saves a new player or updates an existing player in the mock database.
     * If the player's ID is {@code null} or {@code 0}, a new ID will be generated.
     *
     * @param player the {@link Player} object to save.
     * @return the saved {@link Player} object with the assigned or updated ID.
     */

    @Override
    public Player savePlayer(Player player) {
        // Simulates DB generating a new ID
        if (player.getPlayerId() == null || player.getPlayerId() == 0) {
            player.setPlayerId(nextId++);
        }
        playerTable.put(player.getPlayerId(), player);
        return player;
    }

    /**
     * Updates an existing player in the mock database.
     * If the player exists, it is updated with the provided details. Otherwise, the method returns {@code null}.
     *
     * @param player the {@link Player} object with updated information.
     * @return the updated {@link Player} object, or {@code null} if the player was not found.
     */

    @Override
    public Player updatePlayer(Player player) {
        if (playerTable.containsKey(player.getPlayerId())) {
            playerTable.put(player.getPlayerId(), player);
            return player;
        }
        return null; // Simulate record not found
    }

    /**
     * Deletes a player from the mock database by their ID.
     *
     * @param playerId the ID of the player to delete.
     */

    @Override
    public void deletePlayer(Long playerId) {
        playerTable.remove(playerId);
    }

    /**
     * Updates the handicap value of a player in the mock database.
     *
     * @param playerId the ID of the player whose handicap is to be updated.
     * @param newHandicap the new handicap value to set for the player.
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
