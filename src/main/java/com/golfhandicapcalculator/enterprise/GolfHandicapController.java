package com.golfhandicapcalculator.enterprise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GolfHandicapController {

    private final GolfHandicapCalculator calculator;

    @Autowired
    public GolfHandicapController(GolfHandicapCalculator calculator) {
        this.calculator = calculator;
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

        Double handicap = calculator.calculateHandicap(scores, pars, slopes);
        model.addAttribute("handicap", handicap);
        return "golf-handicap";
    }
}
