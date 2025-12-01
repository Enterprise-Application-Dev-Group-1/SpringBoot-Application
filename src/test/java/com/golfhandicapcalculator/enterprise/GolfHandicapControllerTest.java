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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class GolfHandicapControllerTest {

    @Mock
    private GolfHandicapCalculator calculator;

    @Mock
    private IPlayerServices playerServices;

    @Mock
    private WeatherService weatherService;

    private GolfHandicapController controller;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        controller = new GolfHandicapController(calculator, playerServices, weatherService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    // Handicap Calculation Tests

    @Test
    public void testCalculateHandicap_withValidInputs_returnsHandicapView() throws Exception {
        // Arrange
        when(calculator.calculateHandicap(any(double[].class), any(double[].class), any(double[].class)))
                .thenReturn(15.5);

        // Act & Assert
        mockMvc.perform(post("/api/calculate-handicap")
                        .param("scores", "85", "90", "88")
                        .param("pars", "72", "72", "72")
                        .param("slopes", "113", "120", "115"))
                .andExpect(status().isOk())
                .andExpect(view().name("golf-handicap"))
                .andExpect(model().attribute("handicap", 15.5));

        verify(calculator, times(1)).calculateHandicap(
                any(double[].class), any(double[].class), any(double[].class)
        );
    }

    // Player Management Tests

    @Test
    public void testGetAllPlayers_returnsListOfPlayers() {
        // Arrange
        List<Player> players = Arrays.asList(
                createPlayer(1L, "John Doe", 15.5),
                createPlayer(2L, "Jane Smith", 12.3)
        );
        when(playerServices.getAllPlayers()).thenReturn(players);

        // Act
        ResponseEntity<List<Player>> response = controller.getAllPlayers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(playerServices, times(1)).getAllPlayers();
    }

    @Test
    public void testGetPlayer_withExistingId_returnsPlayer() {
        // Arrange
        Player player = createPlayer(1L, "John Doe", 15.5);
        when(playerServices.getPlayerById(1L)).thenReturn(player);

        // Act
        ResponseEntity<Player> response = controller.getPlayer(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        verify(playerServices, times(1)).getPlayerById(1L);
    }

    @Test
    public void testCreatePlayer_returnsCreatedPlayer() {
        // Arrange
        Player newPlayer = createPlayer(null, "New Player", 0.0);
        Player savedPlayer = createPlayer(1L, "New Player", 0.0);
        when(playerServices.createPlayer(any(Player.class))).thenReturn(savedPlayer);

        // Act
        ResponseEntity<Player> response = controller.createPlayer(newPlayer);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getPlayerId().longValue());
        verify(playerServices, times(1)).createPlayer(newPlayer);
    }

    // Score Management Tests

    @Test
    public void testGetPlayerScores_returnsListOfScores() {
        // Arrange
        List<Score> scores = Arrays.asList(
                createScore(1L, 85, 72, 113),
                createScore(2L, 90, 72, 120)
        );
        when(playerServices.getPlayerScores(1L)).thenReturn(scores);

        // Act
        ResponseEntity<List<Score>> response = controller.getPlayerScores(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(playerServices, times(1)).getPlayerScores(1L);
    }

    @Test
    public void testAddScore_returnsCreatedScore() {
        // Arrange
        Score newScore = createScore(null, 88, 72, 113);
        Score savedScore = createScore(1L, 88, 72, 113);
        when(playerServices.addScoreToPlayer(eq(1L), any(Score.class))).thenReturn(savedScore);

        // Act
        ResponseEntity<Score> response = controller.addScore(1L, newScore);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getScoreId().longValue());
        verify(playerServices, times(1)).addScoreToPlayer(1L, newScore);
    }

    // Helper Methods

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
