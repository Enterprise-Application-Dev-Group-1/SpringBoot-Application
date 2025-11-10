package com.golfhandicapcalculator.enterprise;

import com.golfhandicapcalculator.enterprise.dto.Player;
import com.golfhandicapcalculator.enterprise.dto.Score;
import com.golfhandicapcalculator.enterprise.service.IPlayerServices;
import com.golfhandicapcalculator.enterprise.service.IHandicapService;
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

    private final IHandicapService handicapService;
    private final IPlayerServices playerServices;
 
    public GolfHandicapController(IHandicapService handicapService, IPlayerServices playerServices) {
        this.handicapService = handicapService;
        this.playerServices = playerServices;
    }

    @GetMapping("/golf-handicap")
    public String showForm() {
        return "golf-handicap";
    }

    @PostMapping("/calculate-handicap")
    public String calculateHandicap(@RequestParam("scores") double[] scores,
                                    @RequestParam("pars") double[] pars,
                                    @RequestParam(value = "slopes", required = false) double[] slopes,
                                    Model model) {
        double handicap = handicapService.calculateHandicap(scores, pars, slopes);
        model.addAttribute("handicap", handicap);
        return "golf-handicap";
    }

    // Player Management Endpoints
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
        return new ResponseEntity<>(playerServices.createPlayer(player), HttpStatus.CREATED);
    }

    @PutMapping("/players/{playerId}")
    @ResponseBody
    public ResponseEntity<Player> updatePlayer(@PathVariable Long playerId, @RequestBody Player player) {
        return ResponseEntity.ok(playerServices.updatePlayer(playerId, player));
    }

    @DeleteMapping("/players/{playerId}")
    @ResponseBody
    public ResponseEntity<Void> deletePlayer(@PathVariable Long playerId) {
        playerServices.deletePlayer(playerId);
        return ResponseEntity.noContent().build();
    }

    // Score Management Endpoints
    @GetMapping("/players/{playerId}/scores")
    @ResponseBody
    public ResponseEntity<List<Score>> getPlayerScores(@PathVariable Long playerId) {
        return ResponseEntity.ok(playerServices.getPlayerScores(playerId));
    }

    @PostMapping("/players/{playerId}/scores")
    @ResponseBody
    public ResponseEntity<Score> addScore(@PathVariable Long playerId, @RequestBody Score score) {
        return new ResponseEntity<>(playerServices.addScoreToPlayer(playerId, score), HttpStatus.CREATED);
    }

    @PutMapping("/players/{playerId}/scores/{scoreId}")
    @ResponseBody
    public ResponseEntity<Score> updateScore(@PathVariable Long playerId,
                                           @PathVariable Long scoreId,
                                           @RequestBody Score score) {
        return ResponseEntity.ok(playerServices.updatePlayerScore(playerId, scoreId, score));
    }
}
