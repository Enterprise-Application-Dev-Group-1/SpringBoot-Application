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

import java.io.IOException;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"player", "handicap"})
public class PlayerServices implements IPlayerServices {

    private final IPlayerDAO playerDAO;
    private final IScoreDAO scoreDAO;

    @Autowired
    public PlayerServices(IPlayerDAO playerDAO, IScoreDAO scoreDAO) {
        this.playerDAO = playerDAO;
        this.scoreDAO = scoreDAO;
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerDAO.fetchAllPlayers();  // Keep the existing method name
    }

    @Override
    @Cacheable(cacheNames = "player", key = "#playerId")
    @Transactional(readOnly = true)
    public Player getPlayerById(Long playerId) {
        return playerDAO.fetchPlayer(playerId);  // Keep the existing method name
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "player", key = "#result.id")
    public Player createPlayer(Player player) {
        return playerDAO.savePlayer(player);  // Keep the existing method name
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "player", key = "#player.id")  // Use player's ID for cache key
    public Player updatePlayer(Long playerId, Player player) {
        return playerDAO.updatePlayer(player);  // Keep the existing method name
    }

    @Cacheable(cacheNames = "handicap", key = "#playerId")
    @Transactional(readOnly = true)
    public double getPlayerHandicap(Long playerId) {
        // Fetch the Player object first
        Player player = playerDAO.fetchPlayer(playerId);

        // Ensure the player is not null and return the handicap
        if (player != null) {
            return player.getHandicap();  // Assuming the Player object has a 'getHandicap()' method
        }

        // Optionally, handle the case where the player is not found
        throw new IllegalArgumentException("Player not found with ID: " + playerId);
    }


    @Override
    @Transactional
    @CacheEvict(cacheNames = "player", key = "#playerId")
    public void deletePlayer(Long playerId) {
        playerDAO.deletePlayer(playerId);  // No changes needed here
    }

    @Override
    @Transactional(readOnly = true)
    public List<Score> getPlayerScores(Long playerId) {
        return scoreDAO.fetchScoresByPlayerId(playerId);  // Keep the existing method name
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "player", key = "#playerId")
    public Score addScoreToPlayer(Long playerId, Score score) throws IOException {
        return scoreDAO.saveScore(score);  // Keep the existing method name
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "player", key = "#playerId")
    public Score updatePlayerScore(Long playerId, Long scoreId, Score score) throws IOException {
        return scoreDAO.updateScore(score);  // Keep the existing method name
    }
}
