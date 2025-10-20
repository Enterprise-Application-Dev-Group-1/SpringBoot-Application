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
@Transactional(readOnly = true)
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
        return playerDAO.getAllPlayers();
    }

    @Override
    @Cacheable(key = "#playerId")
    public Player getPlayerById(Long playerId) throws IOException {
        return playerDAO.getPlayerById(playerId);
    }

    @Override
    @Transactional
    @CachePut(key = "#result.id")
    public Player createPlayer(Player player) {
        return playerDAO.createPlayer(player);
    }

    @Override
    @Transactional
    @CachePut(key = "#playerId")
    public Player updatePlayer(Long playerId, Player player) {
        return playerDAO.updatePlayer(playerId, player);
    }

    @Override
    @Cacheable(cacheNames = "handicap", key = "#playerId")
    public double getPlayerHandicap(Long playerId) {
        return playerDAO.getHandicap(playerId);
    }

    @Override
    @Transactional
    @CacheEvict(key = "#playerId")
    public void deletePlayer(Long playerId) {
        playerDAO.deletePlayer(playerId);
    }

    @Override
    public List<Score> getPlayerScores(Long playerId) {
        return scoreDAO.getScoresByPlayerId(playerId);
    }

    @Override
    @Transactional
    @CacheEvict(key = "#playerId")
    public Score addScoreToPlayer(Long playerId, Score score) throws IOException {
        return scoreDAO.addScoreToPlayer(playerId, score);
    }

    @Override
    @Transactional
    @CacheEvict(key = "#playerId")
    public Score updatePlayerScore(Long playerId, Long scoreId, Score score) throws IOException {
        return scoreDAO.updatePlayerScore(playerId, scoreId, score);
    }

}
