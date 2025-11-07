package com.golfhandicapcalculator.enterprise.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a golf player.
 * <p>
 * This class serves as a Data Transfer Object (DTO) for carrying player data across different layers
 * of the application (e.g., between the service and DAO layers, or through an API).
 * </p>
 */

@Getter
@Setter
public class Player {

    /**
     * Unique identifier for the player.
     */

    private Long playerId;

    /**
     * Full name of the player.
     */
    private String name;

    /**
     * The player's handicap, representing their skill level in golf.
     */
    private double handicap;
}
