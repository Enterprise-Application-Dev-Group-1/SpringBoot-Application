package com.golfhandicapcalculator.enterprise.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "scores")
@Getter
@Setter
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int par;

    @Column(nullable = false)
    private int slope;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    @JsonIgnore
    private Player player;

    @Transient
    public Long getPlayerId() {
        return player != null ? player.getPlayerId() : null;
    }

    public void setPlayerId(Long playerId) {
        if (this.player == null) {
            this.player = new Player();
        }
        this.player.setPlayerId(playerId);
    }
}
