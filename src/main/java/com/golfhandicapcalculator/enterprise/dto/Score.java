package com.golfhandicapcalculator.enterprise.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entity representing a golf score for a player.
 * <p>
 * This class is a JPA entity that maps to the "scores" table in the database and is associated
 * with a specific player. It contains information about the score, the par, the slope, and
 * the player to whom the score belongs.
 * </p>
 */

@Entity
@Table(name = "scores")
@Getter
@Setter
public class Score {

    /**
     * Unique identifier for the score.
     * <p>
     * This is the primary key for the "scores" table. The value is generated automatically
     * by the database with an auto-increment strategy.
     * </p>
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    /**
     * The score achieved by the player.
     * <p>
     * This is the actual score a player achieved during a round of golf. It is a required
     * field and cannot be null in the database.
     * </p>
     */

    @Column(nullable = false)
    private int score;

    /**
     * The par for the course or hole.
     * <p>
     * The par is the standard number of strokes set for a golf course or a specific hole.
     * This value is also a required field in the database.
     * </p>
     */

    @Column(nullable = false)
    private int par;

    /**
     * The slope rating for the golf course.
     * <p>
     * The slope is a measure of the difficulty of the course. It adjusts the player's
     * handicap based on the difficulty of the course. This is a required field.
     * </p>
     */

    @Column(nullable = false)
    private int slope;

    /**
     * The player associated with this score.
     * <p>
     * This is a many-to-one relationship with the {@link Player} entity, meaning each
     * score is linked to exactly one player. The player is fetched lazily to optimize
     * performance, and the field is marked with `@JsonIgnore` to prevent the player
     * information from being serialized in JSON responses.
     * </p>
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    @JsonIgnore
    private Player player;

    /**
     * Gets the player's ID.
     * <p>
     * This is a helper method to return the player's ID without directly exposing the
     * `Player` entity. It is marked as `@Transient`, so it will not be persisted in the
     * database but can be used in the application logic.
     * </p>
     *
     * @return the ID of the player associated with this score, or {@code null} if no player exists.
     */

    @Transient
    public Long getPlayerId() {
        return player != null ? player.getPlayerId() : null;
    }

    /**
     * Sets the player's ID.
     * <p>
     * This method is used to set the player's ID, which will initialize a new `Player`
     * object if it is not already associated with this score.
     * </p>
     *
     * @param playerId the ID of the player to set
     */

    public void setPlayerId(Long playerId) {
        if (this.player == null) {
            this.player = new Player();
        }
        this.player.setPlayerId(playerId);
    }
}
