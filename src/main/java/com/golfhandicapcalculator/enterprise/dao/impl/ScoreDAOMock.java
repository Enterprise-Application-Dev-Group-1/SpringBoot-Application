package com.golfhandicapcalculator.enterprise.dao.impl;

import com.golfhandicapcalculator.enterprise.dao.IScoreDAO;
import com.golfhandicapcalculator.enterprise.dto.Score;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Mock implementation of the IScoreDAO interface for testing and development purposes.
 * Uses an in-memory ConcurrentHashMap to store score data.
 * This implementation is annotated with @Repository to be detected by Spring's component scanning.
 */
@Repository
public class ScoreDAOMock implements IScoreDAO {

    private final Map<Long, Score> scoreTable = new ConcurrentHashMap<>();
    private Long nextId = 200L;

    /**
     * Constructs a new ScoreDAOMock instance and initializes it with sample data.
     * Creates one default score entry with ID 200 for Player 100.
     */
    public ScoreDAOMock() {
        Score s1 = new Score();
        s1.setScoreId(nextId++);
        com.golfhandicapcalculator.enterprise.dto.Player p = new com.golfhandicapcalculator.enterprise.dto.Player();
        p.setPlayerId(100L);
        s1.setPlayer(p);
        s1.setScore(85);
        s1.setPar(72);
        s1.setSlope(120);
        scoreTable.put(s1.getScoreId(), s1);
    }

    /**
     * Fetches a score by its unique identifier.
     *
     * @param scoreId the unique identifier of the score to retrieve
     * @return the Score object if found, null otherwise
     */
    @Override
    public Score fetchScoreById(Long scoreId) {
        return scoreTable.get(scoreId);
    }

    /**
     * Fetches all scores associated with a specific player.
     *
     * @param playerId the unique identifier of the player
     * @return a list of Score objects belonging to the specified player, empty list if none found
     */
    @Override
    public List<Score> fetchScoresByPlayerId(Long playerId) {
        return scoreTable.values().stream()
                .filter(s -> s.getPlayer() != null
                        && s.getPlayer().getPlayerId() != null
                        && s.getPlayer().getPlayerId().equals(playerId))
                .collect(Collectors.toList());
    }

    /**
     * Saves a new score to the data store.
     * If the score does not have an ID or has ID 0, a new ID is assigned.
     *
     * @param score the Score object to save
     * @return the saved Score object with its assigned ID
     */
    @Override
    public Score saveScore(Score score) {
        if (score.getScoreId() == null || score.getScoreId() == 0) {
            score.setScoreId(nextId++);
        }
        scoreTable.put(score.getScoreId(), score);
        return score;
    }

    /**
     * Updates an existing score in the data store.
     *
     * @param score the Score object with updated information
     * @return the updated Score object if found and updated, null if the score ID does not exist
     */
    @Override
    public Score updateScore(Score score) {
        if (scoreTable.containsKey(score.getScoreId())) {
            scoreTable.put(score.getScoreId(), score);
            return score;
        }
        return null;
    }

    /**
     * Deletes all scores associated with a specific player.
     * Prints a confirmation message to the console after deletion.
     *
     * @param playerId the unique identifier of the player whose scores should be deleted
     */
    @Override
    public void deleteScoresByPlayerId(Long playerId) {
        scoreTable.values().removeIf(score -> score.getPlayer() != null
                && score.getPlayer().getPlayerId() != null
                && score.getPlayer().getPlayerId().equals(playerId));
        System.out.println("DAO Mock: Deleted all scores for Player " + playerId);
    }
}
