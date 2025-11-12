package com.golfhandicapcalculator.enterprise;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "redirect:/golf-handicap";
    }

    @GetMapping("/golf-handicap")
    public String showGolfHandicap() {
        return "golf-handicap";
    }

    @GetMapping("/add-player")
    public String showAddPlayer() {
        return "add-player";
    }

    @GetMapping("/add-score")
    public String showAddScore() {
        return "add-scores";
    }

    @GetMapping("/player-scores")
    public String showPlayerScores() {
        return "player-scores";
    }
}