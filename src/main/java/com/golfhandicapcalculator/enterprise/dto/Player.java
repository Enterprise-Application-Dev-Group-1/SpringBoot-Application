package com.golfhandicapcalculator.enterprise.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Player {
    // Getters and Setters
    private Long playerId;
    private String name;
    private double handicap;

}
