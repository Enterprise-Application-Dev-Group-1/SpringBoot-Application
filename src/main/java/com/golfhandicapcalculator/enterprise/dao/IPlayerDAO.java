package com.golfhandicapcalculator.enterprise.dao;

import com.golfhandicapcalculator.enterprise.dto.Player;

import java.util.List;

/**
 * Interface for data access operations related to {@link Player} entities.
 * <p>
 * This interface defines the contract for CRUD operations that can be performed
 * on the {@link Player} data. The methods in this interface are intended to be
 * implemented by a data access object (DAO) that handles persistence logic,
 * whether that be in-memory, via a database, or other storage mechanism.
 * </p>
 */

public interface IPlayerDAO {

    /**
     * Fetches all players from the data source.
     *
     * @return a list of {@link Player} objects representing all players.
     */

    List<Player> fetchAllPlayers();

    /**
     * Fetches a specific player by their unique player ID.
     *
     * @param playerId the unique ID of the player to retrieve.
     * @return the {@link Player} object with the specified player ID, or {@code null}
     *         if no player is found with that ID.
     */

    Player fetchPlayer(Long playerId);

    /**
     * Saves a new player or updates an existing player in the data source.
     *
     * @param player the {@link Player} object to save or update.
     * @return the saved or updated {@link Player} object, with any changes persisted.
     */

    Player savePlayer(Player player);

    /**
     * Updates the details of an existing player in the data source.
     *
     * @param player the {@link Player} object with updated details.
     * @return the updated {@link Player} object, or {@code null} if the player
     *         does not exist in the data source.
     */

    Player updatePlayer(Player player);

    /**
     * Deletes a player by their unique player ID.
     *
     * @param playerId the ID of the player to delete.
     */

    void deletePlayer(Long playerId);

    /**
     * Updates the handicap of a player.
     *
     * @param playerId the ID of the player whose handicap is to be updated.
     * @param newHandicap the new handicap value to assign to the player.
     */

    void updateHandicap(Long playerId, double newHandicap);
}
