package com.golfhandicapcalculator.enterprise.service;

import com.golfhandicapcalculator.enterprise.dao.IPlayerDAO;
import com.golfhandicapcalculator.enterprise.dao.IScoreDAO;
import com.golfhandicapcalculator.enterprise.dto.Player;
import com.golfhandicapcalculator.enterprise.dto.Score;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServicesTest {

    @Mock
    private IPlayerDAO playerDAO;

    @Mock
    private IScoreDAO scoreDAO;

    private PlayerServices playerServices;

    @Before
    public void setUp() {
        playerServices = new PlayerServices(playerDAO, scoreDAO);
    }

    // Player CRUD Tests

    @Test
    public void testGetAllPlayers_returnsListOfPlayers() {
        // Arrange
        List<Player> expectedPlayers = Arrays.asList(
                createPlayer(1L, "John Doe", 15.5),
                createPlayer(2L, "Jane Smith", 12.3)
        );
        when(playerDAO.fetchAllPlayers()).thenReturn(expectedPlayers);

        // Act
        List<Player> result = playerServices.getAllPlayers();

        // Assert
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(playerDAO, times(1)).fetchAllPlayers();
    }

    @Test
    public void testCreatePlayer_savesAndReturnsPlayer() {
        // Arrange
        Player newPlayer = createPlayer(null, "New Player", 0.0);
        Player savedPlayer = createPlayer(1L, "New Player", 0.0);
        when(playerDAO.savePlayer(any(Player.class))).thenReturn(savedPlayer);

        // Act
        Player result = playerServices.createPlayer(newPlayer);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getPlayerId().longValue());
        assertEquals("New Player", result.getName());
        verify(playerDAO, times(1)).savePlayer(newPlayer);
    }

    @Test
    public void testUpdatePlayer_withValidId_updatesAndReturnsPlayer() {
        // Arrange
        Player updatedPlayerData = createPlayer(null, "Updated Name", 18.0);
        Player savedPlayer = createPlayer(1L, "Updated Name", 18.0);
        when(playerDAO.updatePlayer(any(Player.class))).thenReturn(savedPlayer);

        // Act
        Player result = playerServices.updatePlayer(1L, updatedPlayerData);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getPlayerId().longValue());
        assertEquals("Updated Name", result.getName());
        assertEquals(18.0, result.getHandicap(), 0.01);
        verify(playerDAO, times(1)).updatePlayer(any(Player.class));
    }

    @Test
    public void testGetPlayerScores_returnsListOfScores() {
        // Arrange
        List<Score> expectedScores = Arrays.asList(
                createScore(1L, 85, 72, 113),
                createScore(2L, 90, 72, 120)
        );
        when(scoreDAO.fetchScoresByPlayerId(1L)).thenReturn(expectedScores);

        // Act
        List<Score> result = playerServices.getPlayerScores(1L);

        // Assert
        assertEquals(2, result.size());
        assertEquals(85, result.get(0).getScore());
        verify(scoreDAO, times(1)).fetchScoresByPlayerId(1L);
    }

    @Test
    public void testAddScoreToPlayer_savesScoreWithPlayerId() {
        // Arrange
        Score newScore = createScore(null, 88, 72, 113);
        Score savedScore = createScore(1L, 88, 72, 113);
        when(scoreDAO.saveScore(any(Score.class))).thenReturn(savedScore);

        // Act
        Score result = playerServices.addScoreToPlayer(10L, newScore);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getScoreId().longValue());
        assertEquals(88, result.getScore());
        verify(scoreDAO, times(1)).saveScore(newScore);
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
