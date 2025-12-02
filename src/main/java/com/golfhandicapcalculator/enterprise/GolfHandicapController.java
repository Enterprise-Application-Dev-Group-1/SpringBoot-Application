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
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GolfHandicapController {

    private final IPlayerServices playerServices;
    private final WeatherService weatherService;

    @Autowired
    public GolfHandicapController(IPlayerServices playerServices, WeatherService weatherService) {
        this.playerServices = playerServices;
        this.weatherService = weatherService;
    }

    // Player Management Endpoints

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerServices.getAllPlayers());
    }

    @GetMapping("/players/{playerId}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long playerId) {
        Player player = playerServices.getPlayerById(playerId);
        return player != null ? ResponseEntity.ok(player) : ResponseEntity.notFound().build();
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        return new ResponseEntity<>(playerServices.createPlayer(player), HttpStatus.CREATED);
    }

    @PutMapping("/players/{playerId}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long playerId, @RequestBody Player player) {
        return ResponseEntity.ok(playerServices.updatePlayer(playerId, player));
    }

    @DeleteMapping("/players/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long playerId) {
        playerServices.deletePlayer(playerId);
        return ResponseEntity.noContent().build();
    }

    // Score Management Endpoints

    @GetMapping("/players/{playerId}/scores")
    public ResponseEntity<List<Score>> getPlayerScores(@PathVariable Long playerId) {
        return ResponseEntity.ok(playerServices.getPlayerScores(playerId));
    }

    @PostMapping("/players/{playerId}/scores")
    public ResponseEntity<Score> addScore(@PathVariable Long playerId, @RequestBody Score score) {
        return new ResponseEntity<>(playerServices.addScoreToPlayer(playerId, score), HttpStatus.CREATED);
    }

    @PutMapping("/players/{playerId}/scores/{scoreId}")
    public ResponseEntity<Score> updateScore(@PathVariable Long playerId,
                                             @PathVariable Long scoreId,
                                             @RequestBody Score score) {
        return ResponseEntity.ok(playerServices.updatePlayerScore(playerId, scoreId, score));
    }

    // Weather Endpoint

    @GetMapping("/weather")
    public ResponseEntity<String> getWeather(@RequestParam String lat, @RequestParam String lon) {
        return ResponseEntity.ok(weatherService.getWeather(lat, lon));
    }
}
