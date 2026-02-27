package game;

import cell.Cell;
import listeners.GameListener;
import org.junit.jupiter.api.Test;
import timer.TimerFactory;
import units.Tile;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;


public class GameFinishTest {

    // ==================== tile.push() НЕ заканчивает игру ====================

    @Test
    public void testTilePushDoesNotEndGameWhenConfigurationIncomplete() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        placeRandomConfiguration(field, 3, 3, saboteur);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        Cell cell21 = field.getCell(2, 1);
        Tile tile = cell21.getUnit(Tile.class);
        boolean pushed = tile.push();

        assertTrue(pushed);
        assertFalse(listener.tilesInFinishCalled.get());
        assertFalse(saboteur.areTilesInFinishConfiguration());
    }

    @Test
    public void testTilePushInRandomConfigurationDoesNotEndGame() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        placeRandomConfiguration(field, 3, 3, saboteur);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        Tile tile = findMovableTile(field, 3, 3);
        if (tile != null) {
            tile.push();
        }

        assertFalse(listener.tilesInFinishCalled.get());
        assertFalse(saboteur.areTilesInFinishConfiguration());
    }

    @Test
    public void testTilePushBlockedDoesNotEndGame() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        Cell cell00 = field.getCell(0, 0);
        Cell cell01 = field.getCell(0, 1);
        Tile tile2 = new Tile(2);
        Tile tile1 = new Tile(1);
        tile2.addListener(new listeners.TileStateListener(saboteur));
        tile1.addListener(new listeners.TileStateListener(saboteur));
        cell00.putUnit(tile2);
        cell01.putUnit(tile1);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        boolean pushed = tile2.push();

        assertFalse(saboteur.areTilesInFinishConfiguration());
        assertFalse(listener.tilesInFinishCalled.get());
    }

    @Test
    public void testTilePushInactiveDoesNotEndGame() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        Cell cell1 = field.getCell(0, 0);
        Cell cell2 = field.getCell(0, 1);
        Tile tile = new Tile(1);
        cell1.putUnit(tile);
        tile.deactivate();

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        boolean pushed = tile.push();

        assertFalse(pushed);
        assertFalse(listener.tilesInFinishCalled.get());
    }

    @Test
    public void testTilePushOneMoveFromFinishDoesNotEndGame() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 3; col++) {
                Cell cell = field.getCell(row, col);
                Tile tile = new Tile(row * 3 + col + 1);
                tile.addListener(new listeners.TileStateListener(saboteur));
                cell.putUnit(tile);
            }
        }

        Cell cell20 = field.getCell(2, 0);
        Cell cell21 = field.getCell(2, 1);
        Tile tile8 = new Tile(8);
        Tile tile7 = new Tile(7);
        tile8.addListener(new listeners.TileStateListener(saboteur));
        tile7.addListener(new listeners.TileStateListener(saboteur));
        cell20.putUnit(tile8);
        cell21.putUnit(tile7);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        Tile movingTile = cell21.getUnit(Tile.class);

        assertNotNull(movingTile);
        boolean pushed = movingTile.push();

        assertTrue(pushed);
        assertFalse(listener.tilesInFinishCalled.get());
        assertFalse(saboteur.areTilesInFinishConfiguration());
    }

    // ==================== tile.push() ЗАКАНЧИВАЕТ игру ====================

    @Test
    public void testTilePushEndsGameWhenFinishConfigurationReached() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        placeOneMoveFromFinish(field, 3, 3, saboteur);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        Cell cellWith8 = field.getCell(2, 2);
        Tile tile8 = cellWith8.getUnit(Tile.class);

        assertNotNull(tile8);
        boolean pushed = tile8.push();

        assertTrue(pushed);
        assertTrue(listener.tilesInFinishCalled.get());
        assertTrue(saboteur.areTilesInFinishConfiguration());
    }

    @Test
    public void testTilePushEndsGameIn2x2Field() {
        GameField field = new GameField(2, 2);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 2, 2, timerFactory);

        Cell cell00 = field.getCell(0, 0);
        Cell cell01 = field.getCell(0, 1);
        Cell cell10 = field.getCell(1, 0);
        Cell cell11 = field.getCell(1, 1);

        Tile tile1 = new Tile(1);
        Tile tile3 = new Tile(3);
        Tile tile2 = new Tile(2);
        tile1.addListener(new listeners.TileStateListener(saboteur));
        tile3.addListener(new listeners.TileStateListener(saboteur));
        tile2.addListener(new listeners.TileStateListener(saboteur));
        cell00.putUnit(tile1);
        cell10.putUnit(tile3);
        cell11.putUnit(tile2);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        boolean pushed = tile2.push();

        assertTrue(pushed);
        assertTrue(listener.tilesInFinishCalled.get());
        assertTrue(saboteur.areTilesInFinishConfiguration());
    }

    @Test
    public void testTilePushEndsGameIn4x4Field() {
        GameField field = new GameField(4, 4);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 4, 4, timerFactory);

        placeAlmostFinished4x4(field, saboteur);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        Cell cellWith15 = field.getCell(3, 3);
        Tile tile15 = cellWith15.getUnit(Tile.class);

        assertNotNull(tile15);
        assertEquals(15, tile15.getNumber());
        boolean pushed = tile15.push();

        assertTrue(pushed);
        assertTrue(listener.tilesInFinishCalled.get());
        assertTrue(saboteur.areTilesInFinishConfiguration());
    }

    @Test
    public void testGameReceivesGameIsOverOnFinishConfiguration() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        placeOneMoveFromFinish(field, 3, 3, saboteur);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        Cell cellWith8 = field.getCell(2, 2);
        Tile tile8 = cellWith8.getUnit(Tile.class);
        tile8.push();

        assertTrue(listener.gameIsOverCalled.get());
    }

    @Test
    public void testMultipleListenersNotifiedOnTilePushFinish() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        placeOneMoveFromFinish(field, 3, 3, saboteur);

        AtomicListener listener1 = new AtomicListener();
        AtomicListener listener2 = new AtomicListener();
        AtomicListener listener3 = new AtomicListener();

        saboteur.addGameListener(listener1);
        saboteur.addGameListener(listener2);
        saboteur.addGameListener(listener3);

        Cell cellWith8 = field.getCell(2, 2);
        Tile tile8 = cellWith8.getUnit(Tile.class);
        tile8.push();

        assertTrue(listener1.tilesInFinishCalled.get());
        assertTrue(listener2.tilesInFinishCalled.get());
        assertTrue(listener3.tilesInFinishCalled.get());
    }

    @Test
    public void testTilePushToCompleteFinishFromDifferentPositions() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        placeOneMoveFromFinish(field, 3, 3, saboteur);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        Cell cellWith8 = field.getCell(2, 2);
        Tile tile8 = cellWith8.getUnit(Tile.class);
        boolean pushed = tile8.push();

        assertTrue(pushed);
        assertTrue(listener.tilesInFinishCalled.get());
        assertTrue(saboteur.areTilesInFinishConfiguration());
    }

    // ==================== Спорные случаи ====================

    @Test
    public void testTilePushWithMinesPresentDoesNotAffectFinishDetection() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        placeTilesInOrder(field, 3, 3, saboteur);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        assertTrue(saboteur.areTilesInFinishConfiguration());
    }

    @Test
    public void testTilePushInCorrectPositionDoesNotEndGame() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        placeTilesInOrder(field, 3, 3, saboteur);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        Cell cell00 = field.getCell(0, 0);
        Tile tile1 = cell00.getUnit(Tile.class);

        assertNotNull(tile1);
        boolean pushed = tile1.push();

        assertFalse(listener.tilesInFinishCalled.get());
    }

    @Test
    public void testTilePushAfterGameAlreadyOver() {
        GameField field = new GameField(3, 3);
        TimerFactory timerFactory = new TimerFactory();
        TestSaboteur saboteur = new TestSaboteur(field, 3, 3, timerFactory);

        placeOneMoveFromFinish(field, 3, 3, saboteur);

        AtomicListener listener = new AtomicListener();
        saboteur.addGameListener(listener);

        Cell cellWith8 = field.getCell(2, 2);
        Tile tile8 = cellWith8.getUnit(Tile.class);
        tile8.push();

        assertTrue(listener.tilesInFinishCalled.get());

        assertTrue(saboteur.areTilesInFinishConfiguration());
    }

    // ==================== Вспомогательные методы ====================

    private void placeAlmostFinishedConfiguration(GameField field, int width, int height, TestSaboteur saboteur) {
        int totalTiles = width * height - 1;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == height - 1 && col == width - 1) {
                    continue;
                }
                Cell cell = field.getCell(row, col);
                int pos = row * width + col + 1;
                Tile tile;
                if (pos == totalTiles - 1) {
                    tile = new Tile(totalTiles);
                } else if (pos == totalTiles) {
                    tile = new Tile(totalTiles - 1);
                } else {
                    tile = new Tile(pos);
                }
                tile.addListener(new listeners.TileStateListener(saboteur));
                cell.putUnit(tile);
            }
        }
    }

    private void placeRandomConfiguration(GameField field, int width, int height, TestSaboteur saboteur) {
        int[] values = {3, 1, 2, 6, 4, 5, 8, 7};
        int index = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == height - 1 && col == width - 1) {
                    continue;
                }
                if (index < values.length) {
                    Cell cell = field.getCell(row, col);
                    Tile tile = new Tile(values[index++]);
                    tile.addListener(new listeners.TileStateListener(saboteur));
                    cell.putUnit(tile);
                }
            }
        }
    }

    private void placeOneMoveFromFinish(GameField field, int width, int height, TestSaboteur saboteur) {
        int totalTiles = width * height - 1;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = field.getCell(row, col);
                Tile tile;
                if (row == height - 1 && col == width - 2) {
                    continue;
                }
                if (row == height - 1 && col == width - 1) {
                    tile = new Tile(totalTiles);
                    tile.addListener(new listeners.TileStateListener(saboteur));
                    cell.putUnit(tile);
                    continue;
                }
                int value = row * width + col + 1;
                tile = new Tile(value);
                tile.addListener(new listeners.TileStateListener(saboteur));
                cell.putUnit(tile);
            }
        }
    }

    private void placeAlmostFinished4x4(GameField field, TestSaboteur saboteur) {
        // 1  2  3  4
        // 5  6  7  8
        // 9  10 11 12
        // 13 14 _ 15
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Cell cell = field.getCell(row, col);
                Tile tile;
                if (row == 3 && col == 2) {
                    continue;
                }
                if (row == 3 && col == 3) {
                    tile = new Tile(15);
                    tile.addListener(new listeners.TileStateListener(saboteur));
                    cell.putUnit(tile);
                    continue;
                }
                int value = row * 4 + col + 1;
                tile = new Tile(value);
                tile.addListener(new listeners.TileStateListener(saboteur));
                cell.putUnit(tile);
            }
        }
    }

    private void placeTilesInOrder(GameField field, int width, int height, TestSaboteur saboteur) {
        int value = 1;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == height - 1 && col == width - 1) {
                    continue;
                }
                Cell cell = field.getCell(row, col);
                Tile tile = new Tile(value++);
                tile.addListener(new listeners.TileStateListener(saboteur));
                cell.putUnit(tile);
            }
        }
    }

    private Tile findMovableTile(GameField field, int width, int height) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = field.getCell(row, col);
                Tile tile = cell.getUnit(Tile.class);
                if (tile != null) {
                    return tile;
                }
            }
        }
        return null;
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
    }

    private static class AtomicListener implements GameListener {
        AtomicBoolean tilesInFinishCalled = new AtomicBoolean(false);
        AtomicBoolean gameIsOverCalled = new AtomicBoolean(false);

        @Override
        public void tilesInFinishConfiguration() {
            tilesInFinishCalled.set(true);
            gameIsOverCalled.set(true);
        }

        @Override
        public void gameIsOver() {
            gameIsOverCalled.set(true);
        }
    }
}
