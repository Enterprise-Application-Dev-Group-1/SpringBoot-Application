package com.golfhandicapcalculator.enterprise;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GolfHandicapController {

    private final ExternalFeedService externalFeedService;

    public GolfHandicapController(ExternalFeedService externalFeedService) {
        this.externalFeedService = externalFeedService;
    }


    @GetMapping("/golf-handicap")
    public String showForm() {
        return "golf-handicap"; // This must match the HTML file name in /templates
    }

    @PostMapping("/calculate-handicap")
    public String calculateHandicap(@RequestParam("scores") double[] scores,
                                    @RequestParam("pars") double[] pars,
                                    Model model) {
        if (scores.length != 10 || pars.length != 10) {
            model.addAttribute("error", "Please enter exactly 10 scores and 10 pars.");
            return "golf-handicap";
        }

        double total = 0;
        for (int i = 0; i < 10; i++) {
            total += (scores[i] - pars[i]);
        }

        double handicap = total / 10.0;
        model.addAttribute("handicap", String.format("%.2f", handicap));
        return "golf-handicap";
    }

    @GetMapping("/consume-feed")
    public String consumeFeed(Model model) {
        try {
            String json = externalFeedService.getExternalFeed();
            model.addAttribute("feedData", json);
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching external feed: " + e.getMessage());
        }
        return "golf-handicap";
    }
}
