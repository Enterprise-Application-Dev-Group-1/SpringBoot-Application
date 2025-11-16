package com.golfhandicapcalculator.enterprise.service;

import com.golfhandicapcalculator.enterprise.dto.Player;
import com.golfhandicapcalculator.enterprise.dto.Score;

import java.util.List;
import java.io.IOException;

/**
 * Interface that defines the operations for managing player data and their associated golf scores.
 * <p>
 * This interface provides methods to fetch, create, update, and delete players, as well as manage their scores.
 * Implementing classes will define the behavior of these operations, such as interacting with a database or in-memory storage.
 * </p>
 */

public interface IPlayerServices {

    /**
     * Retrieves a list of all players in the system.
     *
     * @return a list of all {@link Player} objects
     */

    List<Player> getAllPlayers();

    /**
     * Retrieves a player by their unique identifier (playerId).
     *
     * @param playerId the unique identifier of the player
     * @return the {@link Player} object with the given ID, or null if no player is found
     */

    Player getPlayerById(Long playerId);

    /**
     * Creates a new player in the system.
     *
     * @param player the {@link Player} object to be created
     * @return the created {@link Player} object, including the assigned ID
     */

    Player createPlayer(Player player);

    /**
     * Updates the details of an existing player.
     *
     * @param playerId the unique identifier of the player to be updated
     * @param player the {@link Player} object containing the updated data
     * @return the updated {@link Player} object, or null if the player with the given ID doesn't exist
     */

    Player updatePlayer(Long playerId, Player player);

    /**
     * Deletes a player from the system based on their unique identifier (playerId).
     *
     * @param playerId the unique identifier of the player to be deleted
     */

    void deletePlayer(Long playerId);

    /**
     * Retrieves all the scores associated with a particular player.
     *
     * @param playerId the unique identifier of the player
     * @return a list of {@link Score} objects associated with the player
     */

    List<Score> getPlayerScores(Long playerId);

    /**
     * Adds a new score to a specific player.
     *
     * @param playerId the unique identifier of the player to whom the score will be added
     * @param score the {@link Score} object to be added for the player
     * @return the added {@link Score} object
     */

    Score addScoreToPlayer(Long playerId, Score score);

    /**
     * Updates an existing score for a player.
     *
     * @param playerId the unique identifier of the player whose score is to be updated
     * @param scoreId the unique identifier of the score to be updated
     * @param score the updated {@link Score} object
     * @return the updated {@link Score} object, or null if the score with the given ID doesn't exist
     */

    Score updatePlayerScore(Long playerId, Long scoreId, Score score);
}
