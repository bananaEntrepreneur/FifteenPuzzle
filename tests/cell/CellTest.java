package cell;

import game.Direction;
import org.junit.jupiter.api.Test;
import units.Mine;
import units.Tile;
import units.Unit;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    @Test
    public void testCellCreation() {
        Cell cell = new Cell(2, 3);

        assertNotNull(cell);
        assertEquals(2, cell.getRow());
        assertEquals(3, cell.getCol());
        assertTrue(cell.isEmpty());
    }

    @Test
    public void testPutUnit() {
        Cell cell = new Cell(0, 0);
        Tile tile = new Tile(1);

        boolean result = cell.putUnit(tile);

        assertTrue(result);
        assertFalse(cell.isEmpty());
        assertEquals(tile, cell.getUnit(Tile.class));
    }

    @Test
    public void testPutNullUnit() {
        Cell cell = new Cell(0, 0);

        boolean result = cell.putUnit(null);

        assertFalse(result);
        assertTrue(cell.isEmpty());
    }

    @Test
    public void testGetUnit() {
        Cell cell = new Cell(0, 0);
        Tile tile = new Tile(5);
        cell.putUnit(tile);

        Unit unit = cell.getUnit();

        assertNotNull(unit);
        assertEquals(tile, unit);
    }

    @Test
    public void testGetUnitFromEmptyCell() {
        Cell cell = new Cell(0, 0);

        Unit unit = cell.getUnit();

        assertNull(unit);
    }

    @Test
    public void testGetUnitByClass() {
        Cell cell = new Cell(0, 0);
        Tile tile = new Tile(3);
        cell.putUnit(tile);

        Tile retrieved = cell.getUnit(Tile.class);

        assertNotNull(retrieved);
        assertEquals(3, retrieved.getNumber());
    }

    @Test
    public void testGetUnitByClassNotFound() {
        Cell cell = new Cell(0, 0);
        Tile tile = new Tile(3);
        cell.putUnit(tile);

        // Assuming Mine is a different type than Tile
        Unit mine = cell.getUnit(Mine.class);

        assertNull(mine);
    }

    @Test
    public void testExtractUnit() {
        Cell cell = new Cell(0, 0);
        Tile tile = new Tile(1);
        cell.putUnit(tile);

        boolean result = cell.extractUnit(tile);

        assertTrue(result);
        assertTrue(cell.isEmpty());
        assertNull(cell.getUnit());
    }

    @Test
    public void testExtractNullUnit() {
        Cell cell = new Cell(0, 0);

        boolean result = cell.extractUnit(null);

        assertFalse(result);
    }

    @Test
    public void testExtractUnitFromEmptyCell() {
        Cell cell = new Cell(0, 0);
        Tile tile = new Tile(1);

        boolean result = cell.extractUnit(tile);

        assertFalse(result);
    }

    @Test
    public void testGetAllUnits() {
        Cell cell = new Cell(0, 0);
        Tile tile = new Tile(1);
        cell.putUnit(tile);

        Collection<Unit> units = cell.getAllUnits();

        assertNotNull(units);
        assertEquals(1, units.size());
        assertTrue(units.contains(tile));
    }

    @Test
    public void testGetAllUnitsEmpty() {
        Cell cell = new Cell(0, 0);

        Collection<Unit> units = cell.getAllUnits();

        assertNotNull(units);
        assertTrue(units.isEmpty());
    }

    @Test
    public void testGetUnitCount() {
        Cell cell = new Cell(0, 0);

        assertEquals(0, cell.getUnitCount());

        cell.putUnit(new Tile(1));
        assertEquals(1, cell.getUnitCount());
    }

    @Test
    public void testAddNeighbor() {
        Cell cell1 = new Cell(0, 0);
        Cell cell2 = new Cell(0, 1);

        cell1.addNeighbor(Direction.east(), cell2);

        assertEquals(cell2, cell1.getNeighbor(Direction.east()));
    }

    @Test
    public void testAddNullNeighbor() {
        Cell cell = new Cell(0, 0);

        cell.addNeighbor(Direction.east(), null);

        assertNull(cell.getNeighbor(Direction.east()));
    }

    @Test
    public void testGetNeighbors() {
        Cell cell = new Cell(1, 1);
        Cell north = new Cell(0, 1);
        Cell south = new Cell(2, 1);

        cell.addNeighbor(Direction.north(), north);
        cell.addNeighbor(Direction.south(), south);

        Collection<Cell> neighbors = cell.getNeighbors().values();

        assertNotNull(neighbors);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(north));
        assertTrue(neighbors.contains(south));
    }

    @Test
    public void testIsNeighbor() {
        Cell cell1 = new Cell(0, 0);
        Cell cell2 = new Cell(0, 1);

        cell1.addNeighbor(Direction.east(), cell2);

        assertTrue(cell1.isNeighbor(cell2));
        assertFalse(cell2.isNeighbor(cell1));
    }

    @Test
    public void testIsNeighborNull() {
        Cell cell = new Cell(0, 0);

        assertFalse(cell.isNeighbor(null));
    }

    @Test
    public void testGetNeighborCells() {
        Cell cell = new Cell(0, 0);
        Cell east = new Cell(0, 1);

        cell.addNeighbor(Direction.east(), east);

        Collection<Cell> neighborCells = cell.getNeighborCells();

        assertNotNull(neighborCells);
        assertEquals(1, neighborCells.size());
        assertTrue(neighborCells.contains(east));
    }

    @Test
    public void testHasNeighbors() {
        Cell cell = new Cell(0, 0);

        assertFalse(cell.hasNeighbors());

        cell.addNeighbor(Direction.east(), new Cell(0, 1));

        assertTrue(cell.hasNeighbors());
    }

    @Test
    public void testHasUnits() {
        Cell cell = new Cell(0, 0);

        assertFalse(cell.hasUnits());

        cell.putUnit(new Tile(1));

        assertTrue(cell.hasUnits());
    }
}
