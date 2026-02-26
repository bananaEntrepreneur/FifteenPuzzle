package game;

import cell.Cell;
import listeners.GameListener;
import org.junit.jupiter.api.Test;
import saboteurs.SimpleSaboteur;
import timer.TimerFactory;
import units.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class SaboteurFinishTest {

    @Test
    public void testSaboteurDetectsFinishConfiguration() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        placeTilesInOrder(field, 3, 3);

        assertTrue(saboteur.areTilesInFinishConfiguration());
    }

    @Test
    public void testSaboteurDetectsIncorrectConfiguration() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        placeTilesInReverseOrder(field, 3, 3);

        assertFalse(saboteur.areTilesInFinishConfiguration());
    }

    @Test
    public void testSaboteurFiresTilesInFinishConfiguration() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        placeTilesInOrder(field, 3, 3);

        saboteur.checkAndFireFinish();

        assertTrue(listener.tilesInFinishCalled.get());
    }

    @Test
    public void testSaboteurMultipleListenersReceiveFinishEvent() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        List<AtomicListener> listeners = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            AtomicListener listener = new AtomicListener();
            saboteur.addGameListener(listener);
            listeners.add(listener);
        }

        placeTilesInOrder(field, 3, 3);
        saboteur.checkAndFireFinish();

        for (AtomicListener listener : listeners) {
            assertTrue(listener.tilesInFinishCalled.get());
        }
    }

    @Test
    public void testSaboteurGetFieldSize() {
        GameField field = new GameField(4, 4);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 4, 4, timerFactory);

        assertEquals(4, saboteur.getFieldWidth());
        assertEquals(4, saboteur.getFieldHeight());
    }

    @Test
    public void testSaboteurAddGameListener() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);
        AtomicListener listener = new AtomicListener();

        saboteur.addGameListener(listener);

        placeTilesInOrder(field, 3, 3);
        saboteur.checkAndFireFinish();

        assertTrue(listener.tilesInFinishCalled.get());
    }

    @Test
    public void testSaboteurRemoveGameListener() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);
        AtomicListener listener = new AtomicListener();

        saboteur.addGameListener(listener);
        saboteur.removeGameListener(listener);

        placeTilesInOrder(field, 3, 3);
        saboteur.checkAndFireFinish();

        assertFalse(listener.tilesInFinishCalled.get());
    }

    @Test
    public void testSaboteurGetGameListeners() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        GameListener listener1 = new DummyGameListener();
        GameListener listener2 = new DummyGameListener();

        saboteur.addGameListener(listener1);
        saboteur.addGameListener(listener2);

        assertEquals(2, saboteur.getGameListeners().size());
        assertTrue(saboteur.getGameListeners().contains(listener1));
        assertTrue(saboteur.getGameListeners().contains(listener2));
    }

    @Test
    public void testFinishConfigurationWithEmptyLastCell() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        int value = 1;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                // Last cell (2,2) should be empty
                if (row == 2 && col == 2) {
                    continue;
                }
                Cell cell = field.getCell(row, col);
                Tile tile = new Tile(value++);
                cell.putUnit(tile);
            }
        }

        assertTrue(saboteur.areTilesInFinishConfiguration());
    }

    @Test
    public void testFinishConfigurationWrongLastCell() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        int value = 1;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Cell cell = field.getCell(row, col);
                Tile tile = new Tile(value++);
                cell.putUnit(tile);
            }
        }

        assertFalse(saboteur.areTilesInFinishConfiguration());
    }

    @Test
    public void testOnTileMovedTriggersFinishCheck() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        placeTilesInOrder(field, 3, 3);

        saboteur.checkAndFireFinish();

        assertTrue(listener.tilesInFinishCalled.get());
    }

    private void placeTilesInOrder(GameField field, int width, int height) {
        int value = 1;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == height - 1 && col == width - 1) {
                    continue;
                }
                Cell cell = field.getCell(row, col);
                Tile tile = new Tile(value++);
                cell.putUnit(tile);
            }
        }
    }

    private void placeTilesInReverseOrder(GameField field, int width, int height) {
        int totalTiles = width * height - 1;
        int value = totalTiles;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // Last cell should be empty
                if (row == height - 1 && col == width - 1) {
                    continue;
                }
                Cell cell = field.getCell(row, col);
                Tile tile = new Tile(value--);
                cell.putUnit(tile);
            }
        }
    }

    private static class TestSaboteur extends Saboteur {
        public TestSaboteur(GameField f, int w, int h, TimerFactory timerFactory) {
            super(f, w, h, 10, timerFactory);
        }

        @Override
        protected void equipTiles() {
        }

        @Override
        protected void startEquipMines() {
        }

        @Override
        protected void periodicalEquipMines() {
        }

        @Override
        protected boolean areTilesInFinishConfiguration() {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    if (!isCellInCorrectPosition(row, col)) {
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean isCellInCorrectPosition(int row, int col) {
            Cell cell = field.getCell(row, col);
            if (cell == null) {
                return false;
            }

            Tile tile = cell.getUnit(Tile.class);
            int expectedValue = row * width + col + 1;
            boolean isLastCell = (row * width + col + 1) == (width * height);

            if (isLastCell) {
                return tile == null;
            }
            return tile != null && tile.getNumber() == expectedValue;
        }

        @Override
        protected java.awt.Dimension getFieldSize() {
            return new java.awt.Dimension(width, height);
        }

        public void checkAndFireFinish() {
            if (areTilesInFinishConfiguration()) {
                fireTilesInFinishConfiguration();
            }
        }

        public int getFieldWidth() {
            return width;
        }

        public int getFieldHeight() {
            return height;
        }
    }

    private static class AtomicListener implements GameListener {
        AtomicBoolean tilesInFinishCalled = new AtomicBoolean(false);
        AtomicBoolean gameIsOverCalled = new AtomicBoolean(false);

        @Override
        public void tilesInFinishConfiguration() {
            tilesInFinishCalled.set(true);
        }

        @Override
        public void gameIsOver() {
            gameIsOverCalled.set(true);
        }
    }

    private static class DummyGameListener implements GameListener {
        @Override
        public void tilesInFinishConfiguration() {
        }

        @Override
        public void gameIsOver() {
        }
    }
}
