package com.golfhandicapcalculator.enterprise.dao.impl;

import com.golfhandicapcalculator.enterprise.dao.IScoreDAO;
import com.golfhandicapcalculator.enterprise.dto.Score;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * A mock implementation of the {@link IScoreDAO} interface for simulating database operations.
 * This class uses an in-memory {@link ConcurrentHashMap} to store {@link Score} objects
 * and provides CRUD operations to manage scores in a mock database environment.
 *
 * <p>This mock implementation is useful for testing purposes or when a real database
 * is not yet available.</p>
 *
 * @see IScoreDAO
 */

@Repository
public class ScoreDAOMock implements IScoreDAO {

    // Mock database table: Map<ScoreId, ScoreObject>
    private final Map<Long, Score> scoreTable = new ConcurrentHashMap<>();
    private Long nextId = 200L; // Starting ID for mock data

    /**
     * Constructor to seed mock data.
     * Initializes mock data by adding a sample {@link Score} object with a placeholder
     * {@link com.golfhandicapcalculator.enterprise.dto.Player} object (linked to Player ID 100).
     */

    public ScoreDAOMock() {
        Score s1 = new Score();
        s1.setScoreId(nextId++);
        // Link to Alice Jones by creating a minimal Player placeholder with the same id
        com.golfhandicapcalculator.enterprise.dto.Player p = new com.golfhandicapcalculator.enterprise.dto.Player();
        p.setPlayerId(100L);
        s1.setPlayer(p);
        s1.setScore(85);
        s1.setPar(72);
        s1.setSlope(120);
        scoreTable.put(s1.getScoreId(), s1);
    }

    /**
     * Fetches a {@link Score} by its unique score ID.
     *
     * @param scoreId the ID of the score to retrieve.
     * @return the {@link Score} object with the specified score ID, or {@code null} if not found.
     */

    @Override
    public Score fetchScoreById(Long scoreId) {
        return scoreTable.get(scoreId);
    }

    /**
     * Fetches all {@link Score} objects associated with a specific player ID.
     * Simulates a database query by filtering the in-memory map of scores.
     *
     * @param playerId the ID of the player whose scores are to be retrieved.
     * @return a list of {@link Score} objects associated with the given player ID.
     */

    @Override
    public List<Score> fetchScoresByPlayerId(Long playerId) {
        // Simulate a database query by filtering the map; use the Player association
        return scoreTable.values().stream()
                .filter(s -> s.getPlayer() != null
                        && s.getPlayer().getPlayerId() != null
                        && s.getPlayer().getPlayerId().equals(playerId))
                .collect(Collectors.toList());
    }

    /**
     * Saves a {@link Score} object to the mock database.
     * If the score does not already have an ID, a new one is generated and assigned.
     *
     * @param score the {@link Score} object to save.
     * @return the saved {@link Score} object, with an assigned ID.
     */

    @Override
    public Score saveScore(Score score) {
        // Simulates DB generating a new ID
        if (score.getScoreId() == null || score.getScoreId() == 0) {
            score.setScoreId(nextId++);
        }
        scoreTable.put(score.getScoreId(), score);
        return score;
    }

    /**
     * Updates an existing {@link Score} in the mock database.
     *
     * @param score the {@link Score} object with updated information.
     * @return the updated {@link Score} object, or {@code null} if the score was not found.
     */

    @Override
    public Score updateScore(Score score) {
        if (scoreTable.containsKey(score.getScoreId())) {
            scoreTable.put(score.getScoreId(), score);
            return score;
        }
        return null; // Simulate record not found
    }

    /**
     * Deletes all scores associated with a specific player ID from the mock database.
     *
     * @param playerId the ID of the player whose scores should be deleted.
     */

    @Override
    public void deleteScoresByPlayerId(Long playerId) {
        // Remove all scores linked to the player
        scoreTable.values().removeIf(score -> score.getPlayer() != null
                && score.getPlayer().getPlayerId() != null
                && score.getPlayer().getPlayerId().equals(playerId));
        System.out.println("DAO Mock: Deleted all scores for Player " + playerId);
    }
}
