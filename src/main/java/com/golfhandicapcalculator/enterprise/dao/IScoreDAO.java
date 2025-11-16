package com.golfhandicapcalculator.enterprise.dao;

import com.golfhandicapcalculator.enterprise.dto.Score;

import java.util.List;

/**
 * Interface for data access operations related to {@link Score} entities.
 * <p>
 * This interface defines the contract for CRUD operations that can be performed
 * on the {@link Score} data. The methods in this interface are intended to be
 * implemented by a data access object (DAO) that handles persistence logic,
 * whether that be in-memory, via a database, or other storage mechanism.
 * </p>
 */

public interface IScoreDAO {

    /**
     * Fetches a specific score by its unique score ID.
     *
     * @param scoreId the unique ID of the score to retrieve.
     * @return the {@link Score} object with the specified score ID, or {@code null}
     *         if no score is found with that ID.
     */

    Score fetchScoreById(Long scoreId);

    /**
     * Fetches all scores associated with a specific player ID.
     *
     * @param playerId the ID of the player whose scores are to be fetched.
     * @return a list of {@link Score} objects for the specified player, or an empty list
     *         if no scores are found for that player.
     */

    List<Score> fetchScoresByPlayerId(Long playerId);

    /**
     * Saves a new score or updates an existing score in the data source.
     *
     * @param score the {@link Score} object to save or update.
     * @return the saved or updated {@link Score} object, with any changes persisted.
     */

    Score saveScore(Score score);

    /**
     * Updates the details of an existing score in the data source.
     *
     * @param score the {@link Score} object with updated details.
     * @return the updated {@link Score} object, or {@code null} if the score does not exist
     *         in the data source.
     */

    Score updateScore(Score score);

    /**
     * Deletes all scores associated with a specific player ID.
     *
     * @param playerId the ID of the player whose scores are to be deleted.
     */

    void deleteScoresByPlayerId(Long playerId);
}
