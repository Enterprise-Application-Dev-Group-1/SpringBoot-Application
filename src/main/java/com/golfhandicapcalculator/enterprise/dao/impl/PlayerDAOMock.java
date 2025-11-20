package com.golfhandicapcalculator.enterprise.dao.impl;

import com.golfhandicapcalculator.enterprise.dao.IPlayerDAO;
import com.golfhandicapcalculator.enterprise.dto.Player;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlayerDAOMock implements IPlayerDAO {

    private final Map<Long, Player> playerTable = new HashMap<>();
    private Long nextId = 100L;

    public PlayerDAOMock() {
        Player p1 = new Player();
        p1.setPlayerId(nextId++);
        p1.setName("Alice Jones");
        p1.setHandicap(15.4);
        playerTable.put(p1.getPlayerId(), p1);
    }

    @Override
    public List<Player> fetchAllPlayers() {
        return new ArrayList<>(playerTable.values());
    }

    @Override
    public Player fetchPlayer(Long playerId) {
        return playerTable.get(playerId);
    }

    @Override
    public Player savePlayer(Player player) {
        if (player.getPlayerId() == null || player.getPlayerId() == 0) {
            player.setPlayerId(nextId++);
        }
        playerTable.put(player.getPlayerId(), player);
        return player;
    }

    @Override
    public Player updatePlayer(Player player) {
        if (playerTable.containsKey(player.getPlayerId())) {
            playerTable.put(player.getPlayerId(), player);
            return player;
        }
        return null;
    }

    @Override
    public void deletePlayer(Long playerId) {
        playerTable.remove(playerId);
    }

    @Override
    public void updateHandicap(Long playerId, double newHandicap) {
        Player player = playerTable.get(playerId);
        if (player != null) {
            player.setHandicap(newHandicap);
            System.out.println("DAO Mock: Updated handicap for Player " + playerId + " to " + newHandicap);
        }
    }
}
