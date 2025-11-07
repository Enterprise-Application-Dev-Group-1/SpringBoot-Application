package com.golfhandicapcalculator.enterprise.dao;

import com.golfhandicapcalculator.enterprise.dto.Score;

import java.util.List;

/**
 * Data Access Object (DAO) interface for interacting with {@link Score} entities.
 * <p>
 * This interface defines the CRUD operations (Create, Read, Update, Delete) and other methods for managing {@link Score} objects in the system.
 * Implementations of this interface will handle the persistence logic, which could include database access, mock data, or other storage mechanisms.
 * </p>
 */
public interface IScoreDAO {

    /**
     * Fetches a specific score by its unique score ID.
     * <p>
     * This method retrieves a {@link Score} object based on the provided score ID.
     * </p>
     *
     * @param scoreId the ID of the score to retrieve.
     * @return the {@link Score} with the specified score ID, or {@code null} if not found.
     */
    Score fetchScoreById(Long scoreId);

    /**
     * Fetches all scores associated with a specific player.
     * <p>
     * This method retrieves all {@link Score} objects for the player identified by the given player ID.
     * </p>
     *
     * @param playerId the ID of the player whose scores are to be fetched.
     * @return a list of {@link Score} objects for the specified player, or an empty list if no scores are found.
     */
    List<Score> fetchScoresByPlayerId(Long playerId);

    /**
     * Saves a new {@link Score} to the data source.
     * <p>
     * This method stores the given {@link Score} object. If the score does not have a valid score ID, a new ID is typically assigned by the data source.
     * </p>
     *
     * @param score the {@link Score} object to save.
     * @return the saved {@link Score} object with an assigned ID if it was newly created.
     */
    Score saveScore(Score score);

    /**
     * Updates an existing score in the data source.
     * <p>
     * This method updates the data for a given score identified by the score ID. If the score does not exist, the method may return {@code null}.
     * </p>
     *
     * @param score the {@link Score} object with updated data.
     * @return the updated {@link Score} object, or {@code null} if the score with the specified ID doesn't exist.
     */
    Score updateScore(Score score);

    /**
     * Deletes all scores associated with a specific player.
     * <p>
     * This method removes all scores linked to the player identified by the given player ID from the system.
     * </p>
     *
     * @param playerId the ID of the player whose scores are to be deleted.
     */
    void deleteScoresByPlayerId(Long playerId);
}
