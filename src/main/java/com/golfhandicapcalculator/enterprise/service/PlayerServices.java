package com.golfhandicapcalculator.enterprise.service;

import com.golfhandicapcalculator.enterprise.dao.IPlayerDAO;
import com.golfhandicapcalculator.enterprise.dao.IScoreDAO;
import com.golfhandicapcalculator.enterprise.dto.Player;
import com.golfhandicapcalculator.enterprise.dto.Score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PlayerServices implements IPlayerServices {

    @Autowired
    private IPlayerDAO playerDAO;

    @Autowired
    private IScoreDAO scoreDAO;

    public PlayerServices() {}

    public PlayerServices(IPlayerDAO playerDAO){
        this.playerDAO = playerDAO;
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerDAO.getAllPlayers();
    }

    @Override
    @CacheEvict(value = "player", key = "#playerId")
    public Player getPlayerById(Long playerId) {
        return playerDAO.getPlayerById(playerId);
    }

    @Override
    @CacheEvict(value = "player", key = "#playerId")
    public Player createPlayer(Player player) {
        return playerDAO.createPlayer(player);
    }

    @Override
    @CacheEvict(value = "player", key = "#playerId")
    public Player updatePlayer(Long playerId, Player player) {
        return playerDAO.updatePlayer(playerId, player);
    }

    @Override
    @CacheEvict(value = "player", key = "#playerId")
    public void deletePlayer(Long playerId){
        playerDAO.deletePlayer(playerId);
    }

    @Override
    public List<Score> getPlayerScores(Long playerId) {
        return scoreDAO.getScoresByPlayerId(playerId);
    }

    @Override
    @CacheEvict(value = "player", key = "#playerId")
    public Score addScoreToPlayer(Long playerId, Score score) throws IOException {
        return scoreDAO.addScoreToPlayer(playerId, score);
    }

    @Override
    @CacheEvict(value = "player", key = "#playerId")
    public Score updatePlayerScore(Long playerId, Long scoreId, Score score) throws IOException {
        return scoreDAO.updatePlayerScore(playerId, scoreId, score);
    }

}
