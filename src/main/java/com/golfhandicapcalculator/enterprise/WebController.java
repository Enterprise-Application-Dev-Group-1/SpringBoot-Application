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

/**
 * Controller class for handling the web-based views and page redirects for the golf handicap application.
 * <p>
 * This class maps HTTP requests to specific web pages, providing a way for users to interact with
 * the golf handicap calculator through a UI. It handles the navigation between the various pages such as
 * the sign-in page, golf handicap page, player management, and score management pages.
 * </p>
 */

@Controller
public class WebController {

    @Autowired
    private IPlayerServices playerServices;

    @Autowired
    private GolfHandicapCalculator calculator;

    /**
     * Redirects the root URL to the golf handicap calculator.
     *
     * @return a redirect to the "/golf-handicap" page
     */

    @GetMapping("/")
    public String redirectToCalculator() {
        return "redirect:/golf-handicap";
    }

    /**
     * Displays the golf handicap calculation page.
     * <p>
     * This page allows users to enter their golf scores, par values, and slopes to calculate their handicap.
     * </p>
     *
     * @return the name of the view for the golf handicap page ("golf-handicap")
     */

    @GetMapping("/golf-handicap")
    public String showGolfHandicap() {
        return "golf-handicap";
    }

    /**
     * Calculates golf handicap based on submitted scores and pars.
     *
     * @param scores an array of scores for the rounds played
     * @param pars an array of par values corresponding to the scores
     * @param model the model to add the calculated handicap to for rendering the view
     * @return the view name ("golf-handicap") that will display the calculated handicap
     */

    @PostMapping("/calculate-handicap")
    public String calculateHandicap(@RequestParam("scores") double[] scores,
                                   @RequestParam("pars") double[] pars,
                                   Model model) {
        model.addAttribute("handicap", calculator.calculateHandicap(scores, pars, null));
        return "golf-handicap";
    }

    /**
     * Displays the "Add Player" page.
     * <p>
     * This page allows users to add a new player to the system by providing the player's details.
     * </p>
     *
     * @return the name of the view for adding a player ("add-player")
     */

    @GetMapping("/add-player")
    public String showAddPlayer() {
        return "add-player";
    }

    /**
     * Handles the submission of a new player form.
     *
     * @param name the player's name
     * @param handicap the player's initial handicap
     * @param model the model to add feedback messages to
     * @return the view name "add-player" with success or error message
     */

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

    /**
     * Displays the "Add Score" page.
     * <p>
     * This page allows users to input scores for a player.
     * </p>
     *
     * @return the name of the view for adding scores ("add-scores")
     */

    @GetMapping("/add-score")
    public String showAddScore() {
        return "add-scores";
    }

    /**
     * Handles the submission of a new score for a player.
     *
     * @param playerId the ID of the player to whom the score belongs
     * @param score the score value
     * @param par the par value for the course
     * @param slope the slope rating for the course
     * @param model the model to add feedback messages to
     * @return the view name "add-scores" with success or error message
     */

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

    /**
     * Displays the player scores page and calculates handicap if player ID is provided.
     * <p>
     * This page shows a list of all scores associated with a particular player and their
     * calculated handicap (requires at least 3 rounds).
     * </p>
     *
     * @param playerId the unique identifier of the player (optional)
     * @param model the model to add score data and handicap to
     * @return the view name "player-scores"
     */

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
