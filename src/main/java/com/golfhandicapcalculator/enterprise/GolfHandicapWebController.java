package com.golfhandicapcalculator.enterprise;

import com.golfhandicapcalculator.enterprise.dto.Player;
import com.golfhandicapcalculator.enterprise.dto.Score;
import com.golfhandicapcalculator.enterprise.service.IPlayerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GolfHandicapWebController {

    private final GolfHandicapCalculator calculator;
    private final IPlayerServices playerServices;

    @Autowired
    public GolfHandicapWebController(GolfHandicapCalculator calculator,
                                     IPlayerServices playerServices) {
        this.calculator = calculator;
        this.playerServices = playerServices;
    }

    @GetMapping("/")
    public String defaultRoute() {
        return "redirect:/golf-handicap";
    }

    @GetMapping("/golf-handicap")
    public String home(Model model) {
        List<Player> players = playerServices.getAllPlayers();
        model.addAttribute("players", players);
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

    @GetMapping("/add-player")
    public String showAddPlayerForm() {
        return "add-player";
    }

    @PostMapping("/add-player")
    public String addPlayer(@RequestParam String name,
                            @RequestParam double handicap,
                            Model model) {
        Player player = new Player();
        player.setName(name);
        player.setHandicap(handicap);
        playerServices.createPlayer(player);
        model.addAttribute("message", "Player added successfully!");
        return "add-player";
    }

    @GetMapping("/add-score")
    public String showAddScoreForm() {
        return "add-scores";
    }

    @PostMapping("/add-score")
    public String addScore(@RequestParam Long playerId,
                           @RequestParam int score,
                           @RequestParam int par,
                           @RequestParam int slope,
                           Model model) {
        try {
            Score newScore = new Score();
            newScore.setScore(score);
            newScore.setPar(par);
            newScore.setSlope(slope);
            playerServices.addScoreToPlayer(playerId, newScore);
            model.addAttribute("message", "Score added successfully!");
        } catch (Exception e) {
            model.addAttribute("message", "Error adding score: " + e.getMessage());
        }
        return "add-scores";
    }
}
