package com.golfhandicapcalculator.enterprise.dao;

import com.golfhandicapcalculator.enterprise.dto.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Score} entity.
 * <p>
 * This interface extends the {@link JpaRepository} to provide basic CRUD operations
 * for the {@link Score} entity, as well as custom queries for specific operations.
 * The methods in this interface will be automatically implemented by Spring Data JPA.
 * </p>
 * <p>
 * The custom method {@link #findByPlayer_PlayerId(Long)} allows fetching scores for a
 * specific player based on the player's unique ID.
 * </p>
 */

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    /**
     * Finds all {@link Score} entries associated with a specific player by their player ID.
     * <p>
     * This method uses Spring Data JPA's query generation mechanism to fetch all scores
     * for a given player by filtering scores based on the player's ID.
     * </p>
     *
     * @param playerId the ID of the player whose scores are to be retrieved.
     * @return a list of {@link Score} objects associated with the given player ID.
     */

    List<Score> findByPlayer_PlayerId(Long playerId);
}
