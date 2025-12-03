package com.golfhandicapcalculator.enterprise;

import com.golfhandicapcalculator.enterprise.dto.Player;
import com.golfhandicapcalculator.enterprise.dto.Score;
import com.golfhandicapcalculator.enterprise.service.IPlayerServices;
import com.golfhandicapcalculator.enterprise.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API controller for golf handicap operations.
 * Provides endpoints for managing players, scores, and weather information.
 * All endpoints are prefixed with `/api` and support cross-origin requests.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GolfHandicapController {

    private final IPlayerServices playerServices;
    private final WeatherService weatherService;

    /**
     * Constructs a new GolfHandicapController with the required services.
     *
     * @param playerServices service for managing player and score operations
     * @param weatherService service for retrieving weather information
     */
    @Autowired
    public GolfHandicapController(IPlayerServices playerServices, WeatherService weatherService) {
        this.playerServices = playerServices;
        this.weatherService = weatherService;
    }

    // Player Management Endpoints

    /**
     * Retrieves all players in the system.
     *
     * @return ResponseEntity containing a list of all Player objects
     */
    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerServices.getAllPlayers());
    }

    /**
     * Retrieves a specific player by their unique identifier.
     *
     * @param playerId the unique identifier of the player
     * @return ResponseEntity containing the Player object if found, 404 Not Found otherwise
     */
    @GetMapping("/players/{playerId}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long playerId) {
        Player player = playerServices.getPlayerById(playerId);
        return player != null ? ResponseEntity.ok(player) : ResponseEntity.notFound().build();
    }

    /**
     * Creates a new player in the system.
     *
     * @param player the Player object to create
     * @return ResponseEntity containing the created Player object with HTTP status 201 Created
     */
    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        return new ResponseEntity<>(playerServices.createPlayer(player), HttpStatus.CREATED);
    }

    /**
     * Updates an existing player's information.
     *
     * @param playerId the unique identifier of the player to update
     * @param player the Player object containing updated information
     * @return ResponseEntity containing the updated Player object
     */
    @PutMapping("/players/{playerId}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long playerId, @RequestBody Player player) {
        return ResponseEntity.ok(playerServices.updatePlayer(playerId, player));
    }

    /**
     * Deletes a player from the system.
     *
     * @param playerId the unique identifier of the player to delete
     * @return ResponseEntity with HTTP status 204 No Content
     */
    @DeleteMapping("/players/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long playerId) {
        playerServices.deletePlayer(playerId);
        return ResponseEntity.noContent().build();
    }

    // Score Management Endpoints

    /**
     * Retrieves all scores for a specific player.
     *
     * @param playerId the unique identifier of the player
     * @return ResponseEntity containing a list of Score objects for the player
     */
    @GetMapping("/players/{playerId}/scores")
    public ResponseEntity<List<Score>> getPlayerScores(@PathVariable Long playerId) {
        return ResponseEntity.ok(playerServices.getPlayerScores(playerId));
    }

    /**
     * Adds a new score for a specific player.
     *
     * @param playerId the unique identifier of the player
     * @param score the Score object to add
     * @return ResponseEntity containing the created Score object with HTTP status 201 Created
     */
    @PostMapping("/players/{playerId}/scores")
    public ResponseEntity<Score> addScore(@PathVariable Long playerId, @RequestBody Score score) {
        return new ResponseEntity<>(playerServices.addScoreToPlayer(playerId, score), HttpStatus.CREATED);
    }

    /**
     * Updates an existing score for a specific player.
     *
     * @param playerId the unique identifier of the player
     * @param scoreId the unique identifier of the score to update
     * @param score the Score object containing updated information
     * @return ResponseEntity containing the updated Score object
     */
    @PutMapping("/players/{playerId}/scores/{scoreId}")
    public ResponseEntity<Score> updateScore(@PathVariable Long playerId,
                                             @PathVariable Long scoreId,
                                             @RequestBody Score score) {
        return ResponseEntity.ok(playerServices.updatePlayerScore(playerId, scoreId, score));
    }

    // Weather Endpoint

    /**
     * Retrieves weather information for a specific geographic location.
     *
     * @param lat the latitude coordinate
     * @param lon the longitude coordinate
     * @return ResponseEntity containing weather information as a String
     */
    @GetMapping("/weather")
    public ResponseEntity<String> getWeather(@RequestParam String lat, @RequestParam String lon) {
        return ResponseEntity.ok(weatherService.getWeather(lat, lon));
    }
}
