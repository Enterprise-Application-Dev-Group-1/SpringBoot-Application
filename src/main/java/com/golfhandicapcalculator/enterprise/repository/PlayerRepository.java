package com.golfhandicapcalculator.enterprise.repository;

import com.golfhandicapcalculator.enterprise.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

}