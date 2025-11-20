package com.golfhandicapcalculator.enterprise.dao;

import com.golfhandicapcalculator.enterprise.dto.Player;
import java.util.List;

public interface IPlayerDAO {
    List<Player> fetchAllPlayers();
    Player fetchPlayer(Long playerId);
    Player savePlayer(Player player);
    Player updatePlayer(Player player);
    void deletePlayer(Long playerId);
    void updateHandicap(Long playerId, double newHandicap);
}
