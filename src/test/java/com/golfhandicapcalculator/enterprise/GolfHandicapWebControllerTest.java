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
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class GolfHandicapWebControllerTest {

    @Mock
    private IPlayerServices playerServices;

    @Mock
    private GolfHandicapCalculator calculator;

    private GolfHandicapWebController webController;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        webController = new GolfHandicapWebController(calculator, playerServices);

        // Configure view resolver to prevent circular view path errors
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(webController)
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testDefaultRoute_redirectsToGolfHandicap() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/golf-handicap"));
    }

    @Test
    public void testGolfHandicap_loadsPlayersAndReturnsView() throws Exception {
        List<Player> players = Arrays.asList(
                createPlayer(1L, "John Doe", 15.5),
                createPlayer(2L, "Jane Smith", 12.3)
        );
        when(playerServices.getAllPlayers()).thenReturn(players);

        mockMvc.perform(get("/golf-handicap"))
                .andExpect(status().isOk())
                .andExpect(view().name("golf-handicap"))
                .andExpect(model().attributeExists("players"));

        verify(playerServices, times(1)).getAllPlayers();
    }

    @Test
    public void testCalculateHandicap_withValidData_returnsViewWithHandicap() throws Exception {
        when(calculator.calculateHandicap(any(double[].class), any(double[].class), isNull()))
                .thenReturn(15.5);

        mockMvc.perform(post("/calculate-handicap")
                        .param("scores", "85", "90")
                        .param("pars", "72", "72"))
                .andExpect(status().isOk())
                .andExpect(view().name("golf-handicap"))
                .andExpect(model().attributeExists("handicap"));

        verify(calculator, times(1)).calculateHandicap(any(double[].class), any(double[].class), isNull());
    }

    @Test
    public void testCalculateHandicap_withSlopes_usesSlopes() throws Exception {
        when(calculator.calculateHandicap(any(double[].class), any(double[].class), any(double[].class)))
                .thenReturn(14.5);

        mockMvc.perform(post("/calculate-handicap")
                        .param("scores", "85", "90")
                        .param("pars", "72", "72")
                        .param("slopes", "113", "120"))
                .andExpect(status().isOk())
                .andExpect(view().name("golf-handicap"))
                .andExpect(model().attributeExists("handicap"));

        verify(calculator, times(1)).calculateHandicap(any(double[].class), any(double[].class), any(double[].class));
    }

    @Test
    public void testShowAddPlayerForm_returnsAddPlayerView() throws Exception {
        mockMvc.perform(get("/add-player"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-player"));
    }

    @Test
    public void testAddPlayer_withValidData_createsPlayerAndShowsSuccessMessage() throws Exception {
        Player savedPlayer = createPlayer(1L, "John Doe", 15.5);
        when(playerServices.createPlayer(any(Player.class))).thenReturn(savedPlayer);

        mockMvc.perform(post("/add-player")
                        .param("name", "John Doe")
                        .param("handicap", "15.5"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-player"))
                .andExpect(model().attribute("message", "Player added successfully!"));

        verify(playerServices, times(1)).createPlayer(any(Player.class));
    }

    @Test
    public void testShowAddScoreForm_returnsAddScoresView() throws Exception {
        mockMvc.perform(get("/add-score"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-scores"));
    }

    @Test
    public void testAddScore_withValidData_addsScoreAndShowsSuccessMessage() throws Exception {
        Score savedScore = createScore(1L, 88, 72, 113);
        when(playerServices.addScoreToPlayer(eq(1L), any(Score.class))).thenReturn(savedScore);

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
        when(playerServices.addScoreToPlayer(eq(1L), any(Score.class)))
                .thenThrow(new RuntimeException("Database error"));

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
