package com.golfhandicapcalculator.enterprise.dao.impl;

import com.golfhandicapcalculator.enterprise.dao.IScoreDAO;
import com.golfhandicapcalculator.enterprise.dto.Score;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Mock implementation of the {@link IScoreDAO} interface, simulating a database for {@link Score} objects.
 * <p>
 * This class is annotated with {@link Repository}, marking it as a Spring-managed component.
 * It uses a {@link ConcurrentHashMap} as an in-memory store to simulate a database and provides CRUD operations
 * for the {@link Score} entity.
 * </p>
 */
@Repository
public class ScoreDAOMock implements IScoreDAO {

    // Mock database table: Map<ScoreId, ScoreObject>
    private final Map<Long, Score> scoreTable = new ConcurrentHashMap<>();
    private Long nextId = 200L; // Starting ID for mock data

    /**
     * Default constructor that seeds initial mock data into the "database".
     * Adds a score linked to player 100 (Alice Jones) to the mock database.
     */

    // Constructor to seed mock data (assuming player 100 exists from PlayerDAOMock)
    public ScoreDAOMock() {
        Score s1 = new Score();
        s1.setScoreId(nextId++);
        s1.setPlayerId(100L); // Link to Alice Jones
        s1.setScore(85);
        s1.setPar(72);
        s1.setSlope(120);
        scoreTable.put(s1.getScoreId(), s1);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Fetches a score from the "mock database" by its unique score ID.
     * </p>
     *
     * @param scoreId the ID of the score to fetch.
     * @return the {@link Score} with the specified score ID, or {@code null} if not found.
     */
    @Override
    public Score fetchScoreById(Long scoreId) {
        return scoreTable.get(scoreId);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Retrieves all scores from the "mock database" linked to a specific player by their player ID.
     * </p>
     *
     * @param playerId the ID of the player whose scores are to be fetched.
     * @return a list of {@link Score} objects for the specified player, or an empty list if no scores are found.
     */

    @Override
    public List<Score> fetchScoresByPlayerId(Long playerId) {
        // Simulate a database query by filtering the map
        return scoreTable.values().stream()
                .filter(s -> s.getPlayerId().equals(playerId))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Saves a new score to the "mock database". If the score has no ID (or ID equals 0), a new ID is generated and assigned.
     * </p>
     *
     * @param score the {@link Score} object to save.
     * @return the saved {@link Score} object with an assigned ID if it was newly created.
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
     * {@inheritDoc}
     * <p>
     * Updates an existing score in the "mock database". If the score does not exist, returns {@code null}.
     * </p>
     *
     * @param score the {@link Score} object with updated data.
     * @return the updated {@link Score} object, or {@code null} if the score with the specified ID doesn't exist.
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
     * {@inheritDoc}
     * <p>
     * Deletes all scores linked to a specific player from the "mock database".
     * </p>
     *
     * @param playerId the ID of the player whose scores are to be deleted.
     */

    @Override
    public void deleteScoresByPlayerId(Long playerId) {
        // Remove all scores linked to the player
        scoreTable.values().removeIf(score -> score.getPlayerId().equals(playerId));
        System.out.println("DAO Mock: Deleted all scores for Player " + playerId);
    }
}