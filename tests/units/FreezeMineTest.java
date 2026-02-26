package units;

import cell.Cell;
import game.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FreezeMineTest {

    @Test
    public void testFreezeMineCreation() {
        FreezeMine mine = new FreezeMine(5, 10);

        assertNotNull(mine);
        assertEquals(5, mine.getExplosionDelay());
        assertEquals(10, mine.getFreezeDuration());
        assertTrue(mine.isActive());
    }

    @Test
    public void testFreezeMineIsNotFreezingInitially() {
        FreezeMine mine = new FreezeMine(5, 10);

        assertFalse(mine.isFreezing());
    }

    @Test
    public void testFreezeMineApplyEffectFreezesUnitInOwnerCell() {
        FreezeMine mine = new FreezeMine(1, 10);
        Cell cell = new Cell(0, 0);
        Tile tile = new Tile(1);
        cell.putUnit(tile);
        cell.putUnit(mine);

        mine.explode();

        assertFalse(tile.isActive());
    }

    @Test
    public void testFreezeMineApplyEffectFreezesUnitsInNeighborCells() {
        FreezeMine mine = new FreezeMine(1, 10);
        Cell center = new Cell(0, 0);
        Cell neighbor = new Cell(0, 1);
        center.addNeighbor(Direction.east(), neighbor);
        Tile neighborTile = new Tile(2);
        neighbor.putUnit(neighborTile);
        center.putUnit(mine);

        mine.explode();

        assertFalse(neighborTile.isActive());
    }

    @Test
    public void testFreezeMineWithZeroFreezeDuration() {
        FreezeMine mine = new FreezeMine(5, 0);

        assertEquals(0, mine.getFreezeDuration());
    }

    @Test
    public void testFreezeMineWithNegativeFreezeDuration() {
        FreezeMine mine = new FreezeMine(5, -5);

        assertEquals(0, mine.getFreezeDuration());
    }

    @Test
    public void testFreezeMineSetOwner() {
        FreezeMine mine = new FreezeMine(5, 10);
        Cell cell = new Cell(0, 0);

        boolean result = mine.setOwner(cell);

        assertTrue(result);
        assertEquals(cell, mine.owner());
    }

    @Test
    public void testFreezeMineCanBelongToCell() {
        FreezeMine mine = new FreezeMine(5, 10);
        Cell cell = new Cell(0, 0);

        assertTrue(mine.canBelongTo(cell));
    }

    @Test
    public void testFreezeMineCanBelongToNullCell() {
        FreezeMine mine = new FreezeMine(5, 10);

        assertFalse(mine.canBelongTo(null));
    }
}
