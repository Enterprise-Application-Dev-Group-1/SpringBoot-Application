package com.golfhandicapcalculator.enterprise.repository;
import java.util.List;
import com.golfhandicapcalculator.enterprise.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {
    // You can define custom queries here if necessary
    List<Score> findByPlayer_PlayerId(int playerId);
}
