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

@Controller
@RequestMapping("/api")
public class GolfHandicapController {

    private final GolfHandicapCalculator calculator;
    private final IPlayerServices playerServices;

    @Autowired
    public GolfHandicapController(GolfHandicapCalculator calculator, IPlayerServices playerServices) {
        this.calculator = calculator;
        this.playerServices = playerServices;
    }

    // Web UI endpoints
    @GetMapping("/golf-handicap")
    public String showForm() {
        return "golf-handicap";
    }

    @PostMapping("/calculate-handicap")
    public String calculateHandicap(@RequestParam("scores") double[] scores,
                                  @RequestParam("pars") double[] pars,
                                  @RequestParam(value = "slopes", required = false) double[] slopes,
                                  Model model) {
        Double handicap = calculator.calculateHandicap(scores, pars, slopes);
        model.addAttribute("handicap", handicap);
        return "golf-handicap";
    }

    // REST API endpoints
    @GetMapping("/players")
    @ResponseBody
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerServices.getAllPlayers());
    }

    @GetMapping("/players/{playerId}")
    @ResponseBody
    public ResponseEntity<Player> getPlayer(@PathVariable Long playerId) {
        Player player = playerServices.getPlayerById(playerId);
        return player != null ? ResponseEntity.ok(player) : ResponseEntity.notFound().build();
    }

    @PostMapping("/players")
    @ResponseBody
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playerServices.createPlayer(player));
    }

    @PutMapping("/players/{playerId}")
    @ResponseBody
    public ResponseEntity<Player> updatePlayer(@PathVariable Long playerId, @RequestBody Player player) {
        Player updatedPlayer = playerServices.updatePlayer(playerId, player);
        return updatedPlayer != null ? ResponseEntity.ok(updatedPlayer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/players/{playerId}")
    @ResponseBody
    public ResponseEntity<Void> deletePlayer(@PathVariable Long playerId) {
        playerServices.deletePlayer(playerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/players/{playerId}/scores")
    @ResponseBody
    public ResponseEntity<List<Score>> getPlayerScores(@PathVariable Long playerId) {
        return ResponseEntity.ok(playerServices.getPlayerScores(playerId));
    }

    @PostMapping("/players/{playerId}/scores")
    @ResponseBody
    public ResponseEntity<Score> addScore(@PathVariable Long playerId, @RequestBody Score score) {
        Score savedScore = playerServices.addScoreToPlayer(playerId, score);
        return savedScore != null ? 
            ResponseEntity.status(HttpStatus.CREATED).body(savedScore) : 
            ResponseEntity.badRequest().build();
    }

    @PutMapping("/players/{playerId}/scores/{scoreId}")
    @ResponseBody
    public ResponseEntity<Score> updateScore(
            @PathVariable Long playerId,
            @PathVariable Long scoreId,
            @RequestBody Score score) {
        Score updatedScore = playerServices.updatePlayerScore(playerId, scoreId, score);
        return updatedScore != null ? ResponseEntity.ok(updatedScore) : ResponseEntity.notFound().build();
    }
}
