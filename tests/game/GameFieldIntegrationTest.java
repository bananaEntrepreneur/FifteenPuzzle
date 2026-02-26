package game;

import cell.Cell;
import org.junit.jupiter.api.Test;
import units.Tile;

import static org.junit.jupiter.api.Assertions.*;

public class GameFieldIntegrationTest {

    @Test
    public void testGameFieldAccessibleFromGame() {
        Game game = new Game(4, 4);
        GameField field = game.getField();

        assertNotNull(field);
        assertEquals(4, field.getWidth());
        assertEquals(4, field.getHeight());
    }

    @Test
    public void testGameFieldCellsAccessibleFromGame() {
        Game game = new Game(3, 3);
        GameField field = game.getField();

        Cell cell = field.getCell(1, 1);

        assertNotNull(cell);
        assertEquals(1, cell.getRow());
        assertEquals(1, cell.getCol());
    }

    @Test
    public void testTilePlacementThroughGameField() {
        Game game = new Game(3, 3);
        GameField field = game.getField();

        Cell cell = field.getCell(0, 0);
        Tile tile = new Tile(1);

        boolean placed = cell.putUnit(tile);

        assertTrue(placed);
        assertEquals(tile, cell.getUnit(Tile.class));
    }

    @Test
    public void testFieldIterationThroughGame() {
        Game game = new Game(2, 2);
        GameField field = game.getField();

        int count = 0;
        for (Cell cell : field) {
            assertNotNull(cell);
            count++;
        }

        assertEquals(4, count);
    }

    @Test
    public void testCellNeighborsThroughGame() {
        Game game = new Game(3, 3);
        GameField field = game.getField();

        Cell center = field.getCell(1, 1);

        assertEquals(4, center.getNeighbors().size());
        assertNotNull(center.getNeighbor(Direction.north()));
        assertNotNull(center.getNeighbor(Direction.south()));
        assertNotNull(center.getNeighbor(Direction.east()));
        assertNotNull(center.getNeighbor(Direction.west()));
    }

    @Test
    public void testGameTimerFactoryUsedByField() {
        Game game = new Game(4, 4);

        assertNotNull(game.getTimerFactory());
        assertNotNull(game.getField());
    }
}
