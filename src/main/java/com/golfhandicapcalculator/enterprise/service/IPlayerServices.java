package com.golfhandicapcalculator.enterprise.service;

import com.golfhandicapcalculator.enterprise.dto.Player;
import com.golfhandicapcalculator.enterprise.dto.Score;

import java.util.List;
import java.io.IOException;

public interface IPlayerServices {

    List<Player> getAllPlayers();

    Player getPlayerById(Long playerId);

    Player createPlayer(Player player);

    Player updatePlayer(Long playerId, Player player);

    void deletePlayer(Long playerId);

    List<Score> getPlayerScores(Long playerId);

    Score addScoreToPlayer(Long playerId, Score score) throws IOException;

    Score updatePlayerScore(Long playerId, Long scoreId, Score score) throws IOException;
}
