package com.golfhandicapcalculator.enterprise.dao;

import com.golfhandicapcalculator.enterprise.dto.Player;

import java.util.List;

/**
 * Data Access Object (DAO) interface for interacting with {@link Player} entities.
 * <p>
 * This interface defines the CRUD operations (Create, Read, Update, Delete) and additional methods for managing {@link Player} objects.
 * Implementations of this interface will handle the persistence logic, which could include database access, mock data, or other storage mechanisms.
 * </p>
 */

public interface IPlayerDAO {

    /**
     * Fetches all players from the data source.
     * <p>
     * This method retrieves all {@link Player} entities in the system.
     * </p>
     *
     * @return a list of all {@link Player} objects.
     */
    List<Player> fetchAllPlayers();

    /**
     * Fetches a specific player by their unique player ID.
     * <p>
     * This method retrieves a {@link Player} object based on the provided player ID.
     * </p>
     *
     * @param playerId the ID of the player to retrieve.
     * @return the {@link Player} with the specified player ID, or {@code null} if not found.
     */
    Player fetchPlayer(Long playerId);

    /**
     * Saves a {@link Player} to the data source.
     * <p>
     * This method stores the given {@link Player} object. If the player does not have a valid player ID, a new ID is typically assigned by the data source.
     * </p>
     *
     * @param player the {@link Player} object to save.
     * @return the saved {@link Player} object with an assigned ID if it was newly created.
     */
    Player savePlayer(Player player);

    /**
     * Updates an existing player's information in the data source.
     * <p>
     * This method updates the data for a given player identified by the player's ID. If the player does not exist, the method may return {@code null}.
     * </p>
     *
     * @param player the {@link Player} object with updated information.
     * @return the updated {@link Player} object, or {@code null} if the player with the specified ID does not exist.
     */
    Player updatePlayer(Player player);

    /**
     * Deletes a player from the data source.
     * <p>
     * This method removes the player identified by the provided player ID from the system.
     * </p>
     *
     * @param playerId the ID of the player to delete.
     */
    void deletePlayer(Long playerId);

    /**
     * Updates the handicap of a player in the data source.
     * <p>
     * This method modifies the handicap value for a specific player identified by their player ID.
     * </p>
     *
     * @param playerId the ID of the player whose handicap is to be updated.
     * @param newHandicap the new handicap value to set for the player.
     */
    void updateHandicap(Long playerId, double newHandicap);
}
