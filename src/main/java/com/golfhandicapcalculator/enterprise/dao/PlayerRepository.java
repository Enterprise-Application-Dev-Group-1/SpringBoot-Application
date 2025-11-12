package com.golfhandicapcalculator.enterprise.dao;

import com.golfhandicapcalculator.enterprise.dto.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Player} entity.
 * <p>
 * This interface extends the {@link JpaRepository} to provide basic CRUD operations
 * for the {@link Player} entity, as well as custom queries for specific operations.
 * The methods in this interface will be automatically implemented by Spring Data JPA.
 * </p>
 * <p>
 * The custom method {@link #updateHandicap(Long, double)} allows updating the player's
 * handicap directly in the database.
 * </p>
 */

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    /**
     * Updates the handicap for a specific player identified by their player ID.
     * <p>
     * This is a custom update query that modifies the player's handicap in the database.
     * It uses the {@link Modifying} annotation to indicate that the operation
     * will modify data in the database.
     * </p>
     *
     * @param playerId the ID of the player whose handicap is to be updated.
     * @param newHandicap the new handicap value to set for the player.
     */

    @Modifying
    @Query("UPDATE Player p SET p.handicap = :newHandicap WHERE p.playerId = :playerId")
    void updateHandicap(Long playerId, double newHandicap);
}
