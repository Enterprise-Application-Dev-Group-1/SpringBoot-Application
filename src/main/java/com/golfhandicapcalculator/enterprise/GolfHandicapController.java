package com.golfhandicapcalculator.enterprise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for handling golf handicap calculation requests.
 * <p>
 * This controller is responsible for presenting the golf handicap calculation form to the user
 * and processing the form submissions. It interacts with the {@link GolfHandicapCalculator} service
 * to calculate the golf handicap based on user-provided scores, pars, and slopes.
 * </p>
 */

@Controller
public class GolfHandicapController {

    private final GolfHandicapCalculator calculator;

    /**
     * Constructs a new {@link GolfHandicapController} with the given {@link GolfHandicapCalculator}.
     *
     * @param calculator the {@link GolfHandicapCalculator} service used for handicap calculations
     */

    @Autowired
    public GolfHandicapController(GolfHandicapCalculator calculator) {
        this.calculator = calculator;
    }

    /**
     * Displays the form for calculating the golf handicap.
     * <p>
     * This method handles HTTP GET requests to the "/golf-handicap" endpoint and returns the view name
     * for the golf handicap form page.
     * </p>
     *
     * @return the view name for the golf handicap form ("golf-handicap")
     */

    @GetMapping("/golf-handicap")
    public String showForm() {
        return "golf-handicap";
    }

    /**
     * Handles the submission of the golf handicap calculation form.
     * <p>
     * This method processes the form submission, calculates the golf handicap based on the provided scores,
     * pars, and optionally slopes, and returns the result to the view.
     * </p>
     *
     * @param scores the array of scores for the rounds played
     * @param pars the array of par values for the corresponding rounds
     * @param slopes the array of slope values for the corresponding courses (optional)
     * @param model the {@link Model} object used to pass attributes to the view
     * @return the view name for the golf handicap page ("golf-handicap")
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
}
