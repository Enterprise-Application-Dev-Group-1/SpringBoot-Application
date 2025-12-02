package com.golfhandicapcalculator.enterprise;

import com.golfhandicapcalculator.enterprise.dto.Player;
import com.golfhandicapcalculator.enterprise.dto.Score;
import com.golfhandicapcalculator.enterprise.service.IPlayerServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class WebControllerTest {

    @Mock
    private IPlayerServices playerServices;

    @Mock
    private GolfHandicapCalculator calculator;

    @Mock
    private Model model;

    private WebController webController;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        webController = new WebController();
        
        // Use reflection to set private fields for testing
        try {
            java.lang.reflect.Field playerServicesField = WebController.class.getDeclaredField("playerServices");
            playerServicesField.setAccessible(true);
            playerServicesField.set(webController, playerServices);
            
            java.lang.reflect.Field calculatorField = WebController.class.getDeclaredField("calculator");
            calculatorField.setAccessible(true);
            calculatorField.set(webController, calculator);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mocks", e);
        }
        
        // Configure view resolver to avoid circular view path errors
        org.springframework.web.servlet.view.InternalResourceViewResolver viewResolver = 
                new org.springframework.web.servlet.view.InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html");
        
        mockMvc = MockMvcBuilders.standaloneSetup(webController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testRootUrl_redirectsToGolfHandicap() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/golf-handicap"));
    }

    @Test
    public void testCalculateHandicap_withScoresAndPars_returnsViewWithHandicap() throws Exception {
        // Arrange
        when(calculator.calculateHandicap(any(double[].class), any(double[].class), isNull()))
                .thenReturn(15.5);

        // Act & Assert
        mockMvc.perform(post("/calculate-handicap")
                        .param("scores", "85", "90", "88")
                        .param("pars", "72", "72", "72"))
                .andExpect(status().isOk())
                .andExpect(view().name("golf-handicap"))
                .andExpect(model().attribute("handicap", 15.5));

        verify(calculator, times(1)).calculateHandicap(any(double[].class), any(double[].class), isNull());
    }

    @Test
    public void testAddPlayer_withValidData_createsPlayerAndShowsSuccessMessage() throws Exception {
        // Arrange
        Player savedPlayer = createPlayer(1L, "John Doe", 15.5);
        when(playerServices.createPlayer(any(Player.class))).thenReturn(savedPlayer);

        // Act & Assert
        mockMvc.perform(post("/add-player")
                        .param("name", "John Doe")
                        .param("handicap", "15.5"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-player"))
                .andExpect(model().attributeExists("message"));

        verify(playerServices, times(1)).createPlayer(any(Player.class));
    }

    // Add Score Page Tests

    @Test
    public void testShowAddScore_returnsAddScoresView() throws Exception {
        mockMvc.perform(get("/add-score"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-scores"));
    }

    @Test
    public void testAddScore_withValidData_addsScoreAndShowsSuccessMessage() throws Exception {
        // Arrange
        Score savedScore = createScore(1L, 88, 72, 113);
        when(playerServices.addScoreToPlayer(eq(1L), any(Score.class))).thenReturn(savedScore);

        // Act & Assert
        mockMvc.perform(post("/add-score")
                        .param("playerId", "1")
                        .param("score", "88")
                        .param("par", "72")
                        .param("slope", "113"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-scores"))
                .andExpect(model().attribute("message", "Score added successfully!"));

        verify(playerServices, times(1)).addScoreToPlayer(eq(1L), any(Score.class));
    }

    @Test
    public void testAddScore_withException_showsErrorMessage() throws Exception {
        // Arrange
        when(playerServices.addScoreToPlayer(eq(1L), any(Score.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(post("/add-score")
                        .param("playerId", "1")
                        .param("score", "88")
                        .param("par", "72")
                        .param("slope", "113"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-scores"))
                .andExpect(model().attributeExists("message"));

        verify(playerServices, times(1)).addScoreToPlayer(eq(1L), any(Score.class));
    }

    @Test
    public void testShowPlayerScores_withPlayerId_andEnoughScores_calculatesHandicap() throws Exception {
        // Arrange
        List<Score> scores = Arrays.asList(
                createScore(1L, 85, 72, 113),
                createScore(2L, 90, 72, 120),
                createScore(3L, 88, 72, 115)
        );
        when(playerServices.getPlayerScores(1L)).thenReturn(scores);
        when(calculator.calculateHandicap(any(double[].class), any(double[].class), any(double[].class)))
                .thenReturn(15.5);

        // Act & Assert
        mockMvc.perform(get("/player-scores")
                        .param("playerId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("player-scores"))
                .andExpect(model().attribute("playerId", 1L))
                .andExpect(model().attribute("scores", scores))
                .andExpect(model().attribute("handicap", 15.5));

        verify(playerServices, times(1)).getPlayerScores(1L);
        verify(calculator, times(1)).calculateHandicap(
                any(double[].class), any(double[].class), any(double[].class)
        );
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
