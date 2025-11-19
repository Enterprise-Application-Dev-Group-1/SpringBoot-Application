package com.golfhandicapcalculator.enterprise;

import com.golfhandicapcalculator.enterprise.dto.Player;
import com.golfhandicapcalculator.enterprise.dto.Score;
import com.golfhandicapcalculator.enterprise.service.IPlayerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private IPlayerServices playerServices;

    @Autowired
    private GolfHandicapCalculator calculator;

    @GetMapping({"/", "/sign-in"})
    public String showSignIn() {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestParam String username) {
        return "redirect:/golf-handicap";
    }

    @GetMapping("/golf-handicap")
    public String showGolfHandicap() {
        return "golf-handicap";
    }

    @PostMapping("/calculate-handicap")
    public String calculateHandicap(@RequestParam("scores") double[] scores,
                                   @RequestParam("pars") double[] pars,
                                   Model model) {
        model.addAttribute("handicap", calculator.calculateHandicap(scores, pars, null));
        return "golf-handicap";
    }

    @GetMapping("/add-player")
    public String showAddPlayer() {
        return "add-player";
    }

    @PostMapping("/add-player")
    public String addPlayer(@RequestParam String name, @RequestParam double handicap, Model model) {
        try {
            Player player = new Player();
            player.setName(name);
            player.setHandicap(handicap);
            playerServices.createPlayer(player);
            model.addAttribute("message", "Player added! ID: " + player.getPlayerId());
        } catch (Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        return "add-player";
    }

    @GetMapping("/add-score")
    public String showAddScore() {
        return "add-scores";
    }

    @PostMapping("/add-score")
    public String addScore(@RequestParam Long playerId, @RequestParam int score,
                          @RequestParam int par, @RequestParam int slope, Model model) {
        try {
            Score newScore = new Score();
            newScore.setScore(score);
            newScore.setPar(par);
            newScore.setSlope(slope);
            playerServices.addScoreToPlayer(playerId, newScore);
            model.addAttribute("message", "Score added successfully!");
        } catch (Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        return "add-scores";
    }

    @GetMapping("/player-scores")
    public String showPlayerScores(@RequestParam(required = false) Long playerId, Model model) {
        if (playerId == null) return "player-scores";
        
        try {
            List<Score> scores = playerServices.getPlayerScores(playerId);
            model.addAttribute("scores", scores);
            model.addAttribute("playerId", playerId);

            if (scores != null && scores.size() >= 3) {
                double[] scoreVals = scores.stream().mapToDouble(Score::getScore).toArray();
                double[] parVals = scores.stream().mapToDouble(Score::getPar).toArray();
                double[] slopeVals = scores.stream().mapToDouble(Score::getSlope).toArray();
                Double handicap = calculator.calculateHandicap(scoreVals, parVals, slopeVals);
                model.addAttribute("handicap", handicap != null ? handicap : "Unable to calculate");
            } else {
                model.addAttribute("handicap", "Need at least 3 rounds");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error: " + e.getMessage());
        }
        return "player-scores";
    }
}