package com.golfhandicapcalculator.enterprise.dao.impl;

import com.golfhandicapcalculator.enterprise.dao.IPlayerDAO;
import com.golfhandicapcalculator.enterprise.dto.Player;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock implementation of the {@link IPlayerDAO} interface, simulating a database for {@link Player} objects.
 * <p>
 * This class is annotated with {@link Repository}, marking it as a Spring-managed component.
 * It uses an in-memory {@link Map} to simulate a database and provides CRUD operations for the {@link Player} entity.
 * </p>
 */
@Repository // Marks this class as a Spring repository/DAO
public class PlayerDAOMock implements IPlayerDAO {

    // In-memory "mock database" table mapping player IDs to Player objects
    // Mock database table: Map<PlayerId, PlayerObject>
    private final Map<Long, Player> playerTable = new HashMap<>();
    // Simulates the next ID that will be assigned to a new player
    private Long nextId = 100L; // Starting ID for mock data

    /**
     * Default constructor that seeds initial mock data into the "database".
     * Adds a player (Alice Jones) with a handicap of 15.4 to the mock database.
     */
    // Constructor to seed mock data
    public PlayerDAOMock() {
        Player p1 = new Player();
        p1.setPlayerId(nextId++);
        p1.setName("Alice Jones");
        p1.setHandicap(15.4);
        playerTable.put(p1.getPlayerId(), p1);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Fetches all players from the "mock database".
     * </p>
     *
     * @return a list of all {@link Player} objects in the database.
     */
    @Override
    public List<Player> fetchAllPlayers() {
        return new ArrayList<>(playerTable.values());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves a player from the "mock database" by their unique player ID.
     * </p>
     *
     * @param playerId the ID of the player to fetch.
     * @return the {@link Player} with the specified player ID, or {@code null} if not found.
     */
    @Override
    public Player fetchPlayer(Long playerId) {
        return playerTable.get(playerId);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Saves a player to the "mock database". If the player has no ID (or ID equals 0), a new ID is generated and assigned.
     * </p>
     *
     * @param player the {@link Player} object to save.
     * @return the saved {@link Player} object, with an assigned ID if it was newly created.
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
     * {@inheritDoc}
     * <p>
     * Updates an existing player in the "mock database". If the player does not exist, returns {@code null}.
     * </p>
     *
     * @param player the {@link Player} object with updated data.
     * @return the updated {@link Player} object, or {@code null} if the player with the specified ID doesn't exist.
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
     * {@inheritDoc}
     * <p>
     * Deletes a player from the "mock database" by their player ID.
     * </p>
     *
     * @param playerId the ID of the player to delete.
     */
    @Override
    public void deletePlayer(Long playerId) {
        playerTable.remove(playerId);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Updates the handicap for a player in the "mock database".
     * </p>
     *
     * @param playerId the ID of the player whose handicap is to be updated.
     * @param newHandicap the new handicap value to be set for the player.
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
