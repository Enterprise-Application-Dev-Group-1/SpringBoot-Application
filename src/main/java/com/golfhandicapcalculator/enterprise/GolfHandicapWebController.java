package com.golfhandicapcalculator.enterprise;

import com.golfhandicapcalculator.enterprise.dto.Player;
import com.golfhandicapcalculator.enterprise.dto.Score;
import com.golfhandicapcalculator.enterprise.service.IPlayerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Web controller for golf handicap web UI operations.
 * Provides endpoints for rendering web pages and handling form submissions
 * related to players, scores, and handicap calculations.
 */
@Controller
public class GolfHandicapWebController {

    private final GolfHandicapCalculator calculator;
    private final IPlayerServices playerServices;

    /**
     * Constructs a new GolfHandicapWebController with the required services.
     *
     * @param calculator the handicap calculator for computing golf handicaps
     * @param playerServices service for managing player and score operations
     */
    @Autowired
    public GolfHandicapWebController(GolfHandicapCalculator calculator,
                                     IPlayerServices playerServices) {
        this.calculator = calculator;
        this.playerServices = playerServices;
    }

    /**
     * Redirects the default root URL to the main golf handicap page.
     *
     * @return redirect string to `/golf-handicap`
     */
    @GetMapping("/")
    public String defaultRoute() {
        return "redirect:/golf-handicap";
    }

    /**
     * Displays the main golf handicap page with a list of all players.
     *
     * @param model the Model object to pass data to the view
     * @return the name of the view template `golf-handicap`
     */
    @GetMapping("/golf-handicap")
    public String home(Model model) {
        List<Player> players = playerServices.getAllPlayers();
        model.addAttribute("players", players);
        return "golf-handicap";
    }

    /**
     * Processes handicap calculation based on submitted scores, pars, and slopes.
     * Adds the calculated handicap to the model and returns to the golf handicap page.
     *
     * @param scores array of golf scores
     * @param pars array of par values for each round
     * @param slopes optional array of slope ratings for each round
     * @param model the Model object to pass data to the view
     * @return the name of the view template `golf-handicap`
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

    /**
     * Displays the add player form page.
     *
     * @return the name of the view template `add-player`
     */
    @GetMapping("/add-player")
    public String showAddPlayerForm() {
        return "add-player";
    }

    /**
     * Processes the add player form submission.
     * Creates a new player with the provided name and handicap.
     *
     * @param name the name of the player to add
     * @param handicap the handicap value for the player
     * @param model the Model object to pass data to the view
     * @return the name of the view template `add-player`
     */
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

    /**
     * Displays the add score form page.
     *
     * @return the name of the view template `add-scores`
     */
    @GetMapping("/add-score")
    public String showAddScoreForm() {
        return "add-scores";
    }

    /**
     * Processes the add score form submission.
     * Creates a new score for the specified player with the provided score, par, and slope values.
     *
     * @param playerId the unique identifier of the player
     * @param score the golf score value
     * @param par the par value for the round
     * @param slope the slope rating for the round
     * @param model the Model object to pass data to the view
     * @return the name of the view template `add-scores`
     */
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

    /**
     * Displays the delete player form page.
     *
     * @return the name of the view template `delete-player`
     */
    @GetMapping("/delete-player")
    public String showDeletePlayerForm() {
        return "delete-player";
    }

    /**
     * Processes the delete player form submission.
     * Deletes the player with the specified ID and displays a success or error message.
     *
     * @param playerId the unique identifier of the player to delete
     * @param model the Model object to pass data to the view
     * @return the name of the view template `delete-player`
     */
    @PostMapping("/delete-player")
    public String deletePlayer(@RequestParam Long playerId, Model model) {
        try {
            playerServices.deletePlayer(playerId);
            model.addAttribute("message", "Player deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("message", "Error deleting player: " + e.getMessage());
        }
        return "delete-player";
    }
}
