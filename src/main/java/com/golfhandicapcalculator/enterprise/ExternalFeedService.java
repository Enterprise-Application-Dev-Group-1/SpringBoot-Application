package com.golfhandicapcalculator.enterprise;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@Service
public class ExternalFeedService {

    private final RestTemplate restTemplate;
    private final Map<Integer, Player> players = new HashMap<>();
    private final Map<Integer, List<Score>> scoresByPlayer = new HashMap<>();
    private int playerIdCounter = 1;
    private int scoreIdCounter = 1;

    @Value("${external.feed.url}")
    private String externalFeedUrl;

    public ExternalFeedService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getExternalFeed() {
        ResponseEntity<String> response = restTemplate.getForEntity(externalFeedUrl, String.class);
        return response.getBody();
    }
    public void savePlayer(String name, double handicap) {
        Player player = new Player(playerIdCounter++, name, handicap);
        players.put(player.getPlayerId(), player);
    }

    public void saveScore(int playerId, int score, int par, int slope) {
        Score newScore = new Score(scoreIdCounter++, score, par, slope);
        scoresByPlayer.computeIfAbsent(playerId, k -> new ArrayList<>()).add(newScore);
    }

    public List<Score> fetchScores(int playerId) {
        return scoresByPlayer.getOrDefault(playerId, new ArrayList<>());
    }

    public double fetchPlayerHandicap(int playerId) {
        List<Score> scores = fetchScores(playerId);
        return calculatePlayerHandicap(scores, playerId);
    }

    public double calculatePlayerHandicap(List<Score> scores, int playerId) {
        if (scores.size() == 0) return 0.0;

        double total = 0;
        for (Score s : scores) {
            total += (s.getScore() - s.getPar());
        }
        return total / scores.size();
    }
}
