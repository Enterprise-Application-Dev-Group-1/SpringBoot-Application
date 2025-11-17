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
 * Service class for managing player and score-related operations.
 * <p>
 * This class provides methods for handling CRUD operations related to players and their scores.
 * The operations are backed by data access objects (DAOs) and are cached for performance optimization using
 * Spring's caching annotations.
 * </p>
 * <p>
 * The service is transactional, ensuring data consistency and rollback capabilities for all update and delete operations.
 * </p>
 */

@Service
@CacheConfig(cacheNames = {"player", "handicap"})
@Transactional(readOnly = true)
public class PlayerServices implements IPlayerServices {

    private final IPlayerDAO playerDAO;
    private final IScoreDAO scoreDAO;

    /**
     * Constructs a new PlayerServices instance with the specified DAOs for player and score operations.
     *
     * @param playerDAO the data access object for player-related operations
     * @param scoreDAO the data access object for score-related operations
     */

    @Autowired
    public PlayerServices(IPlayerDAO playerDAO, IScoreDAO scoreDAO) {
        this.playerDAO = playerDAO;
        this.scoreDAO = scoreDAO;
    }

    /**
     * Fetches all players from the system.
     *
     * @return a list of all {@link Player} objects in the system
     */

    @Override
    public List<Player> getAllPlayers() {
        return playerDAO.fetchAllPlayers();
    }

    /**
     * Retrieves a player by their unique identifier (playerId) from the system.
     * This method is cached using the playerId as the cache key to improve performance.
     *
     * @param playerId the unique identifier of the player
     * @return the {@link Player} object with the specified ID, or null if no player is found
     */

    @Override
    @Cacheable(key = "#playerId")
    public Player getPlayerById(Long playerId) {
        return playerDAO.fetchPlayer(playerId);
    }

    /**
     * Creates a new player in the system and returns the created player.
     * The new player is cached using the player's ID as the cache key.
     *
     * @param player the {@link Player} object to be created
     * @return the created {@link Player} object, including the assigned ID
     */

    @Override
    @Transactional
    @CachePut(key = "#result.playerId")
    public Player createPlayer(Player player) {
        return playerDAO.savePlayer(player);
    }

    /**
     * Updates the details of an existing player in the system.
     * The player's updated data is cached using their ID as the cache key.
     *
     * @param playerId the unique identifier of the player to be updated
     * @param player the {@link Player} object containing the updated data
     * @return the updated {@link Player} object
     */

    @Override
    @Transactional
    @CachePut(key = "#playerId")
    public Player updatePlayer(Long playerId, Player player) {
        player.setPlayerId(playerId);
        return playerDAO.updatePlayer(player);
    }

    /**
     * Deletes a player from the system based on their unique identifier (playerId).
     * The cache for the player is evicted after deletion to ensure the data is not stale.
     *
     * @param playerId the unique identifier of the player to be deleted
     */

    @Override
    @Transactional
    @CacheEvict(key = "#playerId")
    public void deletePlayer(Long playerId) {
        playerDAO.deletePlayer(playerId);
    }

    /**
     * Retrieves all the scores associated with a particular player.
     *
     * @param playerId the unique identifier of the player
     * @return a list of {@link Score} objects associated with the player
     */

    @Override
    public List<Score> getPlayerScores(Long playerId) {
        return scoreDAO.fetchScoresByPlayerId(playerId);
    }

    /**
     * Adds a new score to a specific player. The cache for the player is evicted to ensure the data is updated.
     *
     * @param playerId the unique identifier of the player to whom the score will be added
     * @param score the {@link Score} object to be added for the player
     * @return the added {@link Score} object
     */

    @Override
    @Transactional
    @CacheEvict(key = "#playerId")
    public Score addScoreToPlayer(Long playerId, Score score) {
        score.setPlayerId(playerId);
        return scoreDAO.saveScore(score);
    }

    /**
     * Updates an existing score for a player. The cache for the player is evicted to reflect the updated data.
     *
     * @param playerId the unique identifier of the player whose score is to be updated
     * @param scoreId the unique identifier of the score to be updated
     * @param score the updated {@link Score} object
     * @return the updated {@link Score} object
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
