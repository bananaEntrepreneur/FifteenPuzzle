package game;

import org.junit.jupiter.api.Test;
import timer.TimerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testGameCreation() {
        Game game = new Game(4, 4);

        assertNotNull(game);
        assertNotNull(game.getField());
        assertEquals(4, game.getField().getWidth());
        assertEquals(4, game.getField().getHeight());
    }

    @Test
    public void testGameIsNotOverInitially() {
        Game game = new Game(4, 4);

        assertFalse(game.isOver());
    }

    @Test
    public void testGameHasTimerFactory() {
        Game game = new Game(4, 4);

        assertNotNull(game.getTimerFactory());
        assertTrue(game.getTimerFactory() instanceof TimerFactory);
    }

    @Test
    public void testGameStart() {
        Game game = new Game(4, 4);

        game.start();

        assertFalse(game.isOver());
    }

    @Test
    public void testGameFieldNotNullAfterCreation() {
        Game game = new Game(4, 4);

        assertNotNull(game.getField());
    }

    @Test
    public void testGameGameOverOnTilesInFinishConfiguration() {
        Game game = new Game(4, 4);

        game.tilesInFinishConfiguration();

        assertTrue(game.isOver());
    }

    @Test
    public void testGameWithDifferentSizes() {
        Game game3x3 = new Game(3, 3);
        Game game5x5 = new Game(5, 5);

        assertEquals(3, game3x3.getField().getWidth());
        assertEquals(3, game3x3.getField().getHeight());

        assertEquals(5, game5x5.getField().getWidth());
        assertEquals(5, game5x5.getField().getHeight());
    }
}
