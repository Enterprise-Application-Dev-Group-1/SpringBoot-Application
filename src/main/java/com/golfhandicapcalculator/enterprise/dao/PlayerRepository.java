package com.golfhandicapcalculator.enterprise.dao;

import com.golfhandicapcalculator.enterprise.dto.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Modifying
    @Query("UPDATE Player p SET p.handicap = :newHandicap WHERE p.playerId = :playerId")
    void updateHandicap(Long playerId, double newHandicap);
}
