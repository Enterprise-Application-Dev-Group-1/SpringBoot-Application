package com.golfhandicapcalculator.enterprise.dto;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "scores")
@Setter
@Getter
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
    
    @Column(nullable = false)
    private int score;
    
    @Column(nullable = false)
    private int par;
    
    @Column(nullable = false)
    private int slope;
}
