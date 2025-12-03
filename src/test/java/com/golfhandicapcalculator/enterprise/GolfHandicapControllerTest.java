package com.golfhandicapcalculator.enterprise;

import com.golfhandicapcalculator.enterprise.dto.Player;
import com.golfhandicapcalculator.enterprise.dto.Score;
import com.golfhandicapcalculator.enterprise.service.IPlayerServices;
import com.golfhandicapcalculator.enterprise.service.WeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GolfHandicapControllerTest {

    @Mock
    private IPlayerServices playerServices;

    @Mock
    private WeatherService weatherService;

    private GolfHandicapController controller;

    @Before
    public void setUp() {
        controller = new GolfHandicapController(playerServices, weatherService);
    }

    @Test
    public void testGetAllPlayers_returnsListOfPlayers() {
        List<Player> players = Arrays.asList(
                createPlayer(1L, "John Doe", 15.5),
                createPlayer(2L, "Jane Smith", 12.3)
        );
        when(playerServices.getAllPlayers()).thenReturn(players);

        ResponseEntity<List<Player>> response = controller.getAllPlayers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(playerServices, times(1)).getAllPlayers();
    }

    @Test
    public void testGetPlayer_withExistingId_returnsPlayer() {
        Player player = createPlayer(1L, "John Doe", 15.5);
        when(playerServices.getPlayerById(1L)).thenReturn(player);

        ResponseEntity<Player> response = controller.getPlayer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        verify(playerServices, times(1)).getPlayerById(1L);
    }

    @Test
    public void testGetPlayer_withNonExistingId_returnsNotFound() {
        when(playerServices.getPlayerById(999L)).thenReturn(null);

        ResponseEntity<Player> response = controller.getPlayer(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(playerServices, times(1)).getPlayerById(999L);
    }

    @Test
    public void testCreatePlayer_returnsCreatedPlayer() {
        Player newPlayer = createPlayer(null, "New Player", 0.0);
        Player savedPlayer = createPlayer(1L, "New Player", 0.0);
        when(playerServices.createPlayer(any(Player.class))).thenReturn(savedPlayer);

        ResponseEntity<Player> response = controller.createPlayer(newPlayer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getPlayerId().longValue());
        verify(playerServices, times(1)).createPlayer(newPlayer);
    }

    @Test
    public void testUpdatePlayer_returnsUpdatedPlayer() {
        Player updatedData = createPlayer(null, "Updated Name", 18.0);
        Player savedPlayer = createPlayer(1L, "Updated Name", 18.0);
        when(playerServices.updatePlayer(eq(1L), any(Player.class))).thenReturn(savedPlayer);

        ResponseEntity<Player> response = controller.updatePlayer(1L, updatedData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Name", response.getBody().getName());
        verify(playerServices, times(1)).updatePlayer(1L, updatedData);
    }

    @Test
    public void testDeletePlayer_returnsNoContent() {
        doNothing().when(playerServices).deletePlayer(1L);

        ResponseEntity<Void> response = controller.deletePlayer(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(playerServices, times(1)).deletePlayer(1L);
    }

    @Test
    public void testGetPlayerScores_returnsListOfScores() {
        List<Score> scores = Arrays.asList(
                createScore(1L, 85, 72, 113),
                createScore(2L, 90, 72, 120)
        );
        when(playerServices.getPlayerScores(1L)).thenReturn(scores);

        ResponseEntity<List<Score>> response = controller.getPlayerScores(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(playerServices, times(1)).getPlayerScores(1L);
    }

    @Test
    public void testAddScore_returnsCreatedScore() {
        Score newScore = createScore(null, 88, 72, 113);
        Score savedScore = createScore(1L, 88, 72, 113);
        when(playerServices.addScoreToPlayer(eq(1L), any(Score.class))).thenReturn(savedScore);

        ResponseEntity<Score> response = controller.addScore(1L, newScore);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getScoreId().longValue());
        verify(playerServices, times(1)).addScoreToPlayer(1L, newScore);
    }

    @Test
    public void testUpdateScore_returnsUpdatedScore() {
        Score updatedScore = createScore(null, 92, 72, 120);
        Score savedScore = createScore(1L, 92, 72, 120);
        when(playerServices.updatePlayerScore(eq(1L), eq(1L), any(Score.class))).thenReturn(savedScore);

        ResponseEntity<Score> response = controller.updateScore(1L, 1L, updatedScore);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(92, response.getBody().getScore());
        verify(playerServices, times(1)).updatePlayerScore(1L, 1L, updatedScore);
    }

    @Test
    public void testGetWeather_returnsWeatherData() {
        String weatherData = "{\"temp\": 72, \"conditions\": \"sunny\"}";
        when(weatherService.getWeather("40.7128", "-74.0060")).thenReturn(weatherData);

        ResponseEntity<String> response = controller.getWeather("40.7128", "-74.0060");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(weatherData, response.getBody());
        verify(weatherService, times(1)).getWeather("40.7128", "-74.0060");
    }

    private Player createPlayer(Long id, String name, double handicap) {
        Player player = new Player();
        player.setPlayerId(id);
        player.setName(name);
        player.setHandicap(handicap);
        return player;
    }

    private Score createScore(Long id, int score, int par, int slope) {
        Score scoreObj = new Score();
        scoreObj.setScoreId(id);
        scoreObj.setScore(score);
        scoreObj.setPar(par);
        scoreObj.setSlope(slope);
        return scoreObj;
    }
}
