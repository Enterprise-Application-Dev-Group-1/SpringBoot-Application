package com.golfhandicapcalculator.enterprise;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GolfHandicapController {
    @GetMapping("/golf-handicap")
    public String showForm() {
        return "golf-handicap";
    }

    @PostMapping("/calculate-handicap")
    public String calculateHandicap(@RequestParam("scores") double[] scores,
                                    @RequestParam("pars") double[] pars,
                                    Model model) {
        double totalHandicap = 0;
        int count = Math.min(scores.length, pars.length);
        for (int i = 0; i < count; i++) {
            totalHandicap += (scores[i] - pars[i]);
        }
        Double handicap = count > 0 ? totalHandicap / count : null;
        model.addAttribute("handicap", handicap);
        return "golf-handicap";
    }
}
