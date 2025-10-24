package com.golfhandicapcalculator.enterprise.dto;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "players")
@Setter
@Getter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private double handicap;
}
