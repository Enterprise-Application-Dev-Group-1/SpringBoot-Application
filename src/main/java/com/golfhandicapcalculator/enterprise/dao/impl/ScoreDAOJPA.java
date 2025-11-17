package com.golfhandicapcalculator.enterprise.dao.impl;

import com.golfhandicapcalculator.enterprise.dao.IScoreDAO;
import com.golfhandicapcalculator.enterprise.dao.ScoreRepository;
import com.golfhandicapcalculator.enterprise.dto.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of the {@link IScoreDAO} interface using JPA for database access.
 * This class provides CRUD operations for the {@link Score} entity, allowing
 * retrieval, insertion, updating, and deletion of scores in a database.
 *
 * <p>This implementation uses the {@link ScoreRepository} to interact with the database
 * and perform the actual data access operations.</p>
 *
 * @see IScoreDAO
 * @see ScoreRepository
 */

@Repository
@Primary
public class ScoreDAOJPA implements IScoreDAO {

    @Autowired
    private ScoreRepository scoreRepository;

    /**
     * Fetches a score by its unique score ID.
     *
     * @param scoreId the ID of the score to retrieve.
     * @return the {@link Score} object with the specified ID, or {@code null} if no score is found.
     */

    @Override
    public Score fetchScoreById(Long scoreId) {
        return scoreRepository.findById(scoreId).orElse(null);
    }

    /**
     * Fetches a list of scores associated with a specific player ID.
     *
     * @param playerId the ID of the player whose scores are to be retrieved.
     * @return a list of {@link Score} objects associated with the given player ID.
     */

    @Override
    public List<Score> fetchScoresByPlayerId(Long playerId) {
        return scoreRepository.findByPlayer_PlayerId(playerId);
    }

    /**
     * Saves a {@link Score} object to the database.
     * If the score has an existing ID, it will be updated; otherwise, a new record will be created.
     *
     * @param score the {@link Score} object to save.
     * @return the saved {@link Score} object.
     */

    @Override
    public Score saveScore(Score score) {
        return scoreRepository.save(score);
    }

    /**
     * Updates an existing score in the database.
     *
     * @param score the {@link Score} object with updated information.
     * @return the updated {@link Score} object, or {@code null} if no score was found with the given ID.
     */

    @Override
    public Score updateScore(Score score) {
        if (scoreRepository.existsById(score.getScoreId())) {
            return scoreRepository.save(score);
        }
        return null;
    }

    /**
     * Deletes all scores associated with a specific player ID.
     *
     * @param playerId the ID of the player whose scores are to be deleted.
     */

    @Override
    public void deleteScoresByPlayerId(Long playerId) {
        List<Score> scores = scoreRepository.findByPlayer_PlayerId(playerId);
        scoreRepository.deleteAll(scores);
    }
}
