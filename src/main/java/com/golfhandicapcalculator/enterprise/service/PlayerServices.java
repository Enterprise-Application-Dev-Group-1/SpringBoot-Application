package com.golfhandicapcalculator.enterprise.service;

import com.golfhandicapcalculator.enterprise.dao.IPlayerDAO;
import com.golfhandicapcalculator.enterprise.dao.IScoreDAO;
import com.golfhandicapcalculator.enterprise.dto.Player;
import com.golfhandicapcalculator.enterprise.dto.Score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for managing players and their scores in the golf handicap system.
 * <p>
 * This service handles the creation, retrieval, updating, and deletion of players, as well as their scores.
 * It also incorporates caching to improve performance and transaction management to ensure consistency in operations.
 * </p>
 * <p>
 * The service uses Spring's {@link Cacheable}, {@link CachePut}, and {@link CacheEvict} annotations to cache
 * player and handicap data, and uses {@link Transactional} to handle database transactions.
 * </p>
 */

@Service
@CacheConfig(cacheNames = {"player", "handicap"})
@Transactional(readOnly = true)
public class PlayerServices implements IPlayerServices {

    private final IPlayerDAO playerDAO;
    private final IScoreDAO scoreDAO;

    /**
     * Constructs a new {@link PlayerServices} instance.
     *
     * @param playerDAO the data access object used for player-related operations
     * @param scoreDAO the data access object used for score-related operations
     */

    @Autowired
    public PlayerServices(IPlayerDAO playerDAO, IScoreDAO scoreDAO) {
        this.playerDAO = playerDAO;
        this.scoreDAO = scoreDAO;
    }

    /**
     * Retrieves all players in the system.
     * <p>
     * This method fetches all players stored in the database through the playerDAO.
     * </p>
     *
     * @return a list of all players
     */

    @Override
    public List<Player> getAllPlayers() {
        return playerDAO.fetchAllPlayers();
    }

    /**
     * Retrieves a player by their unique player ID.
     * <p>
     * This method fetches a player from the database by their player ID and caches the result for future use.
     * </p>
     *
     * @param playerId the ID of the player to retrieve
     * @return the player with the specified player ID, or {@code null} if no player is found
     */

    @Override
    @Cacheable(key = "#playerId")
    public Player getPlayerById(Long playerId) {
        return playerDAO.fetchPlayer(playerId);
    }

    /**
     * Creates a new player and persists it in the database.
     * <p>
     * This method adds a new player to the system and caches the created player.
     * </p>
     *
     * @param player the player to create
     * @return the created player, including the generated player ID
     */

    @Override
    @Transactional
    @CachePut(key = "#result.playerId")
    public Player createPlayer(Player player) {
        return playerDAO.savePlayer(player);
    }

    @Override
    @Transactional
    @CachePut(key = "#playerId")
    public Player updatePlayer(Long playerId, Player player) {
        player.setPlayerId(playerId);
        return playerDAO.updatePlayer(player);
    }

    /**
     * Updates an existing player's information.
     * <p>
     * This method updates an existing player's information based on the player ID and updates the corresponding cache.
     * </p>
     *
     * @param playerId the ID of the player to update.
     * @return the updated player
     */

    @Override
    @Transactional
    @CacheEvict(key = "#playerId")
    public void deletePlayer(Long playerId) {
        playerDAO.deletePlayer(playerId);
    }

    /**
     * Retrieves all scores associated with a specific player.
     * <p>
     * This method fetches all scores from the database that are linked to the specified player ID.
     * </p>
     *
     * @param playerId the ID of the player whose scores to retrieve
     * @return a list of scores for the specified player
     */

    @Override
    public List<Score> getPlayerScores(Long playerId) {
        return scoreDAO.fetchScoresByPlayerId(playerId);
    }

    /**
     * Adds a new score to a player’s record.
     * <p>
     * This method associates a new score with a player and persists it in the database. It also evicts the player’s
     * cache entry to ensure freshness.
     * </p>
     *
     * @param playerId the ID of the player to whom the score belongs
     * @param score the score to add
     * @return the added score, including the score ID
     */

    @Override
    @Transactional
    @CacheEvict(key = "#playerId")
    public Score addScoreToPlayer(Long playerId, Score score) {
        score.setPlayerId(playerId);
        return scoreDAO.saveScore(score);
    }

    /**
     * Updates an existing score for a player.
     * <p>
     * This method updates a score based on the specified player ID and score ID. It also evicts the player’s cache
     * entry to ensure that the updated data is reflected in the cache.
     * </p>
     *
     * @param playerId the ID of the player whose score is being updated
     * @param scoreId the ID of the score to update
     * @param score the updated score data
     * @return the updated score
     */

    @Override
    @Transactional
    @CacheEvict(key = "#playerId")
    public Score updatePlayerScore(Long playerId, Long scoreId, Score score) {
        score.setPlayerId(playerId);
        score.setScoreId(scoreId);
        return scoreDAO.updateScore(score);
    }

}
