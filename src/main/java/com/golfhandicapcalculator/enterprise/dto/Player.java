package com.golfhandicapcalculator.enterprise.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a player in the golf handicap calculator system.
 * <p>
 * This class is a JPA entity that maps to the "players" table in the database. It contains
 * the player's basic details such as ID, name, handicap, and the list of scores associated
 * with the player. The class uses Lombok annotations for automatic generation of getters
 * and setters to reduce boilerplate code.
 * </p>
 */

@Entity
@Table(name = "players")
@Setter
@Getter
public class Player {

    /**
     * Unique identifier for the player.
     * <p>
     * This is the primary key for the "players" table. The value is generated automatically
     * by the database with an auto-increment strategy.
     * </p>
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    /**
     * Name of the player.
     * <p>
     * The player's name is a required field and cannot be null in the database.
     * </p>
     */

    @Column(nullable = false)
    private String name;

    /**
     * Handicap of the player.
     * <p>
     * This is a numeric value representing the player's golf handicap, which is a measure
     * of their ability relative to other golfers. It is stored as a double.
     * </p>
     */

    private double handicap;

    /**
     * List of scores associated with the player.
     * <p>
     * This is a one-to-many relationship with the {@link Score} entity, meaning one player
     * can have multiple scores. The `mappedBy` attribute indicates that the `player` field
     * in the `Score` entity is the owning side of the relationship. The `cascade = CascadeType.ALL`
     * ensures that any changes to the player entity will propagate to related scores, and
     * `orphanRemoval = true` ensures that any score removed from this list will be deleted
     * from the database as well.
     * </p>
     */

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Score> scores = new ArrayList<>();
}
