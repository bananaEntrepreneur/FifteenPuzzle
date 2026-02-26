package units;

import cell.Cell;
import game.Direction;
import org.junit.jupiter.api.Test;

import java.util.EventObject;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    @Test
    public void testTileCreation() {
        Tile tile = new Tile(5);

        assertNotNull(tile);
        assertEquals(5, tile.getNumber());
        assertTrue(tile.isActive());
    }

    @Test
    public void testTileToString() {
        Tile tile = new Tile(42);

        assertEquals("42", tile.toString());
    }

    @Test
    public void testTileSetOwner() {
        Tile tile = new Tile(1);
        Cell cell = new Cell(0, 0);

        boolean result = tile.setOwner(cell);

        assertTrue(result);
        assertEquals(cell, tile.owner());
    }

    @Test
    public void testTileCanBelongToEmptyCell() {
        Tile tile = new Tile(1);
        Cell emptyCell = new Cell(0, 0);

        assertTrue(tile.canBelongTo(emptyCell));
    }

    @Test
    public void testTileCanBelongToNullCell() {
        Tile tile = new Tile(1);

        assertTrue(tile.canBelongTo(null));
    }

    @Test
    public void testTileCanBelongToOccupiedCell() {
        Tile tile1 = new Tile(1);
        Tile tile2 = new Tile(2);
        Cell cell = new Cell(0, 0);
        cell.putUnit(tile1);

        assertFalse(tile2.canBelongTo(cell));
    }

    @Test
    public void testTilePushToEmptyNeighbor() {
        Tile tile = new Tile(1);
        Cell cell1 = new Cell(0, 0);
        Cell cell2 = new Cell(0, 1);
        cell1.addNeighbor(Direction.east(), cell2);
        cell1.putUnit(tile);

        boolean result = tile.push();

        assertTrue(result);
        assertEquals(cell2, tile.owner());
        assertTrue(cell2.hasUnits());
        assertTrue(cell1.isEmpty());
    }

    @Test
    public void testTilePushBlockedByAnotherTile() {
        Tile tile1 = new Tile(1);
        Tile tile2 = new Tile(2);
        Cell cell1 = new Cell(0, 0);
        Cell cell2 = new Cell(0, 1);
        cell1.addNeighbor(Direction.east(), cell2);
        cell1.putUnit(tile1);
        cell2.putUnit(tile2);

        boolean result = tile1.push();

        assertFalse(result);
        assertEquals(cell1, tile1.owner());
    }

    @Test
    public void testTilePushInactive() {
        Tile tile = new Tile(1);
        Cell cell = new Cell(0, 0);
        cell.putUnit(tile);
        tile.deactivate();

        boolean result = tile.push();

        assertFalse(result);
    }

    @Test
    public void testTilePushNoOwner() {
        Tile tile = new Tile(1);

        boolean result = tile.push();

        assertFalse(result);
    }

    @Test
    public void testTileActivate() {
        Tile tile = new Tile(1);
        tile.deactivate();

        tile.activate();

        assertTrue(tile.isActive());
    }

    @Test
    public void testTileDeactivate() {
        Tile tile = new Tile(1);

        tile.deactivate();

        assertFalse(tile.isActive());
    }
}
