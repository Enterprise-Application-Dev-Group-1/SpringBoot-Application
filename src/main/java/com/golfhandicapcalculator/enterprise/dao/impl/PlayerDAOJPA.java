package com.golfhandicapcalculator.enterprise.dao.impl;

import com.golfhandicapcalculator.enterprise.dao.IPlayerDAO;
import com.golfhandicapcalculator.enterprise.dao.PlayerRepository;
import com.golfhandicapcalculator.enterprise.dto.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of the {@link IPlayerDAO} interface using Spring Data JPA for database operations.
 * This class provides CRUD (Create, Read, Update, Delete) operations for managing {@link Player} entities.
 */

@Repository
@Primary
public class PlayerDAOJPA implements IPlayerDAO {

    @Autowired
    private PlayerRepository playerRepository;

    /**
     * Fetches all players from the repository.
     *
     * @return a list of all players.
     */

    @Override
    public List<Player> fetchAllPlayers() {
        return playerRepository.findAll();
    }

    /**
     * Fetches a specific player by their unique player ID.
     *
     * @param playerId the ID of the player to fetch.
     * @return the {@link Player} object if found, or {@code null} if no player exists with the given ID.
     */

    @Override
    public Player fetchPlayer(Long playerId) {
        return playerRepository.findById(playerId).orElse(null);
    }

    /**
     * Saves a new player or updates an existing player in the repository.
     *
     * @param player the {@link Player} object to be saved or updated.
     * @return the saved or updated {@link Player} object.
     */

    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    /**
     * Updates the information of an existing player.
     * If the player with the given ID does not exist, the update will not occur.
     *
     * @param player the {@link Player} object to be updated.
     * @return the updated {@link Player} object if the player exists, or {@code null} if the player does not exist.
     */

    @Override
    public Player updatePlayer(Player player) {
        if (playerRepository.existsById(player.getPlayerId())) {
            return playerRepository.save(player);
        }
        return null;
    }

    /**
     * Deletes a player by their unique player ID.
     *
     * @param playerId the ID of the player to delete.
     */

    @Override
    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    /**
     * Updates the handicap of a player by their player ID.
     * The new handicap value is updated directly in the repository.
     *
     * @param playerId the ID of the player whose handicap needs to be updated.
     * @param newHandicap the new handicap value to set for the player.
     */

    @Override
    public void updateHandicap(Long playerId, double newHandicap) {
        playerRepository.updateHandicap(playerId, newHandicap);
    }
}
