package com.golfhandicapcalculator.enterprise;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for handling the web-based views and page redirects for the golf handicap application.
 * <p>
 * This class maps HTTP GET requests to specific web pages, providing a way for users to interact with
 * the golf handicap calculator through a UI. It handles the navigation between the various pages such as
 * the home page, golf handicap page, player management, and score management pages.
 * </p>
 */

@Controller
public class WebController {

    /**
     * Redirects the root URL ("/") to the golf handicap page.
     *
     * @return a redirect to the "/golf-handicap" page
     */

    @GetMapping("/")
    public String home() {
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
     * Displays the player scores page.
     * <p>
     * This page shows a list of all scores associated with a particular player.
     * </p>
     *
     * @return the name of the view for displaying player scores ("player-scores")
     */

    @GetMapping("/player-scores")
    public String showPlayerScores() {
        return "player-scores";
    }
}
