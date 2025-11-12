package com.golfhandicapcalculator.enterprise;

import com.golfhandicapcalculator.enterprise.dto.Player;
import com.golfhandicapcalculator.enterprise.dto.Score;
import com.golfhandicapcalculator.enterprise.service.IPlayerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling golf handicap calculations and player/score management operations.
 * <p>
 * This class provides endpoints for calculating golf handicaps and managing players and their scores
 * through RESTful APIs. It integrates with the `GolfHandicapCalculator` for handicap calculations and
 * uses the `IPlayerServices` service layer for managing player data and scores.
 * </p>
 */

@Controller
@RequestMapping("/api")
public class GolfHandicapController {

    private final GolfHandicapCalculator calculator;
    private final IPlayerServices playerServices;

    /**
     * Constructs a new GolfHandicapController with the specified calculator and player services.
     *
     * @param calculator the {@link GolfHandicapCalculator} used for calculating handicaps
     * @param playerServices the {@link IPlayerServices} used for managing player data and scores
     */

    @Autowired
    public GolfHandicapController(GolfHandicapCalculator calculator, IPlayerServices playerServices) {
        this.calculator = calculator;
        this.playerServices = playerServices;
    }

    /**
     * Calculates the golf handicap based on the provided scores, pars, and optional slope values.
     * The result is added to the model and displayed in the "golf-handicap" view.
     *
     * @param scores an array of scores for the rounds played
     * @param pars an array of par values corresponding to the scores
     * @param slopes an optional array of slope values for each course played
     * @param model the model to add the calculated handicap to for rendering the view
     * @return the view name ("golf-handicap") that will display the calculated handicap
     */

    @PostMapping("/calculate-handicap")
    public String calculateHandicap(@RequestParam("scores") double[] scores,
                                    @RequestParam("pars") double[] pars,
                                    @RequestParam(value = "slopes", required = false) double[] slopes,
                                    Model model) {
        Double handicap = calculator.calculateHandicap(scores, pars, slopes);
        model.addAttribute("handicap", handicap);
        return "golf-handicap";
    }

    // Player Management Endpoints

    /**
     * Retrieves a list of all players from the system.
     *
     * @return a {@link ResponseEntity} containing a list of all players with an HTTP 200 status
     */

    @GetMapping("/players")
    @ResponseBody
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerServices.getAllPlayers());
    }

    /**
     * Retrieves a player by their unique identifier (playerId).
     *
     * @param playerId the unique identifier of the player
     * @return a {@link ResponseEntity} containing the player with HTTP 200 status, or HTTP 404 if not found
     */

    @GetMapping("/players/{playerId}")
    @ResponseBody
    public ResponseEntity<Player> getPlayer(@PathVariable Long playerId) {
        Player player = playerServices.getPlayerById(playerId);
        return player != null ? ResponseEntity.ok(player) : ResponseEntity.notFound().build();
    }

    /**
     * Creates a new player in the system.
     *
     * @param player the {@link Player} object containing the player details
     * @return a {@link ResponseEntity} with the created player and HTTP 201 status (Created)
     */

    @PostMapping("/players")
    @ResponseBody
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        return new ResponseEntity<>(playerServices.createPlayer(player), HttpStatus.CREATED);
    }

    /**
     * Updates the details of an existing player.
     *
     * @param playerId the unique identifier of the player to be updated
     * @param player the {@link Player} object containing the updated details
     * @return a {@link ResponseEntity} with the updated player and HTTP 200 status
     */

    @PutMapping("/players/{playerId}")
    @ResponseBody
    public ResponseEntity<Player> updatePlayer(@PathVariable Long playerId, @RequestBody Player player) {
        return ResponseEntity.ok(playerServices.updatePlayer(playerId, player));
    }

    /**
     * Deletes a player from the system based on their unique identifier (playerId).
     *
     * @param playerId the unique identifier of the player to be deleted
     * @return a {@link ResponseEntity} with HTTP 204 status (No Content) after deletion
     */

    @DeleteMapping("/players/{playerId}")
    @ResponseBody
    public ResponseEntity<Void> deletePlayer(@PathVariable Long playerId) {
        playerServices.deletePlayer(playerId);
        return ResponseEntity.noContent().build();
    }

    // Score Management Endpoints

    /**
     * Retrieves a list of scores associated with a specific player.
     *
     * @param playerId the unique identifier of the player whose scores are to be retrieved
     * @return a {@link ResponseEntity} containing a list of scores for the player with HTTP 200 status
     */

    @GetMapping("/players/{playerId}/scores")
    @ResponseBody
    public ResponseEntity<List<Score>> getPlayerScores(@PathVariable Long playerId) {
        return ResponseEntity.ok(playerServices.getPlayerScores(playerId));
    }

    /**
     * Adds a new score to a specific player.
     *
     * @param playerId the unique identifier of the player to whom the score will be added
     * @param score the {@link Score} object containing the score to be added
     * @return a {@link ResponseEntity} with the added score and HTTP 201 status (Created)
     */

    @PostMapping("/players/{playerId}/scores")
    @ResponseBody
    public ResponseEntity<Score> addScore(@PathVariable Long playerId, @RequestBody Score score) {
        return new ResponseEntity<>(playerServices.addScoreToPlayer(playerId, score), HttpStatus.CREATED);
    }

    /**
     * Updates an existing score for a specific player.
     *
     * @param playerId the unique identifier of the player whose score will be updated
     * @param scoreId the unique identifier of the score to be updated
     * @param score the updated {@link Score} object
     * @return a {@link ResponseEntity} with the updated score and HTTP 200 status
     */

    @PutMapping("/players/{playerId}/scores/{scoreId}")
    @ResponseBody
    public ResponseEntity<Score> updateScore(@PathVariable Long playerId,
                                             @PathVariable Long scoreId,
                                             @RequestBody Score score) {
        return ResponseEntity.ok(playerServices.updatePlayerScore(playerId, scoreId, score));
    }
}
