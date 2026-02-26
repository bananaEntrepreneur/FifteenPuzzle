package game;

import cell.Cell;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class GameFieldTest {

    @Test
    public void testFieldCreationWithSize() {
        GameField field = new GameField(4, 4);

        assertEquals(4, field.getWidth());
        assertEquals(4, field.getHeight());
    }

    @Test
    public void testFieldCreationDefault() {
        GameField field = new GameField();

        assertEquals(0, field.getWidth());
        assertEquals(0, field.getHeight());
    }

    @Test
    public void testSetSize() {
        GameField field = new GameField();

        field.setSize(new java.awt.Dimension(3, 5));

        assertEquals(3, field.getWidth());
        assertEquals(5, field.getHeight());
    }

    @Test
    public void testGetCell() {
        GameField field = new GameField(3, 3);

        Cell cell = field.getCell(1, 2);

        assertNotNull(cell);
        assertEquals(1, cell.getRow());
        assertEquals(2, cell.getCol());
    }

    @Test
    public void testGetCellReturnsNullForInvalidPosition() {
        GameField field = new GameField(3, 3);

        Cell cell = field.getCell(5, 5);

        assertNull(cell);
    }

    @Test
    public void testFieldIteration() {
        GameField field = new GameField(2, 3);

        int count = 0;
        for (Cell cell : field) {
            assertNotNull(cell);
            count++;
        }

        assertEquals(6, count);
    }

    @Test
    public void testFieldIteratorHasNext() {
        GameField field = new GameField(2, 2);
        Iterator<Cell> iterator = field.iterator();

        assertTrue(iterator.hasNext());
        iterator.next();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testCellNeighbors() {
        GameField field = new GameField(3, 3);

        Cell center = field.getCell(1, 1);

        assertNotNull(center.getNeighbor(game.Direction.north()));
        assertNotNull(center.getNeighbor(game.Direction.south()));
        assertNotNull(center.getNeighbor(game.Direction.east()));
        assertNotNull(center.getNeighbor(game.Direction.west()));
        assertEquals(4, center.getNeighbors().size());
    }

    @Test
    public void testCornerCellNeighbors() {
        GameField field = new GameField(3, 3);

        Cell topLeft = field.getCell(0, 0);

        assertNull(topLeft.getNeighbor(game.Direction.north()));
        assertNull(topLeft.getNeighbor(game.Direction.west()));
        assertNotNull(topLeft.getNeighbor(game.Direction.south()));
        assertNotNull(topLeft.getNeighbor(game.Direction.east()));
        assertEquals(2, topLeft.getNeighbors().size());
    }

    @Test
    public void testDeactivate() {
        GameField field = new GameField(2, 2);

        field.deactivate();

        for (Cell cell : field) {
            for (var unit : cell.getAllUnits()) {
                assertFalse(unit.isActive());
            }
        }
    }
}
