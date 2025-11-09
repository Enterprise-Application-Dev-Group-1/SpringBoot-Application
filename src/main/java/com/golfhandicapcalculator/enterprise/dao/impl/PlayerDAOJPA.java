package com.golfhandicapcalculator.enterprise.dao.impl;

import com.golfhandicapcalculator.enterprise.dao.IPlayerDAO;
import com.golfhandicapcalculator.enterprise.dao.PlayerRepository;
import com.golfhandicapcalculator.enterprise.dto.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class PlayerDAOJPA implements IPlayerDAO {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public List<Player> fetchAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player fetchPlayer(Long playerId) {
        return playerRepository.findById(playerId).orElse(null);
    }

    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Player player) {
        if (playerRepository.existsById(player.getPlayerId())) {
            return playerRepository.save(player);
        }
        return null;
    }

    @Override
    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    @Override
    public void updateHandicap(Long playerId, double newHandicap) {
        playerRepository.updateHandicap(playerId, newHandicap);
    }
}
