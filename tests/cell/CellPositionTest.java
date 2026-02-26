package cell;

import game.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellPositionTest {

    @Test
    public void testCellPositionCreation() {
        CellPosition pos = new CellPosition(2, 3);

        assertNotNull(pos);
        assertEquals(2, pos.getRow());
        assertEquals(3, pos.getColumn());
    }

    @Test
    public void testCellPositionWithNegativeRow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CellPosition(-1, 0);
        });
    }

    @Test
    public void testCellPositionWithNegativeColumn() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CellPosition(0, -1);
        });
    }

    @Test
    public void testCellPositionShift() {
        CellPosition pos = new CellPosition(2, 3);

        CellPosition shifted = pos.shift(1, -1);

        assertEquals(3, shifted.getRow());
        assertEquals(2, shifted.getColumn());
    }

    @Test
    public void testCellPositionShiftNegativeResult() {
        CellPosition pos = new CellPosition(0, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            pos.shift(-1, 0);
        });
    }

    @Test
    public void testCellPositionShiftByDirection() {
        CellPosition pos = new CellPosition(2, 2);

        CellPosition north = pos.shift(Direction.north(), 1);
        assertEquals(1, north.getRow());
        assertEquals(2, north.getColumn());

        CellPosition south = pos.shift(Direction.south(), 1);
        assertEquals(3, south.getRow());
        assertEquals(2, south.getColumn());

        CellPosition east = pos.shift(Direction.east(), 1);
        assertEquals(2, east.getRow());
        assertEquals(3, east.getColumn());

        CellPosition west = pos.shift(Direction.west(), 1);
        assertEquals(2, west.getRow());
        assertEquals(1, west.getColumn());
    }

    @Test
    public void testCellPositionShiftByDirectionInvalidDelta() {
        CellPosition pos = new CellPosition(2, 2);

        assertThrows(IllegalArgumentException.class, () -> {
            pos.shift(Direction.north(), 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            pos.shift(Direction.north(), -1);
        });
    }

    @Test
    public void testCellPositionEquals() {
        CellPosition pos1 = new CellPosition(2, 3);
        CellPosition pos2 = new CellPosition(2, 3);

        assertEquals(pos1, pos2);
    }

    @Test
    public void testCellPositionNotEquals() {
        CellPosition pos1 = new CellPosition(2, 3);
        CellPosition pos2 = new CellPosition(3, 2);

        assertNotEquals(pos1, pos2);
    }

    @Test
    public void testCellPositionEqualsNull() {
        CellPosition pos = new CellPosition(2, 3);

        assertNotEquals(pos, null);
    }

    @Test
    public void testCellPositionIsNeighbor() {
        CellPosition pos1 = new CellPosition(2, 2);
        CellPosition neighbor = new CellPosition(2, 3);

        assertTrue(pos1.isNeighbor(neighbor));
    }

    @Test
    public void testCellPositionIsNotNeighbor() {
        CellPosition pos1 = new CellPosition(2, 2);
        CellPosition notNeighbor = new CellPosition(4, 4);

        assertFalse(pos1.isNeighbor(notNeighbor));
    }

    @Test
    public void testCellPositionApproximateDirectionToSame() {
        CellPosition pos1 = new CellPosition(2, 2);
        CellPosition pos2 = new CellPosition(2, 2);

        assertTrue(pos1.approximateDirectionTo(pos2).isEmpty());
    }

    @Test
    public void testCellPositionApproximateDirectionToNorth() {
        CellPosition from = new CellPosition(2, 2);
        CellPosition to = new CellPosition(0, 2);

        var directions = from.approximateDirectionTo(to);

        assertEquals(1, directions.size());
        assertTrue(directions.contains(Direction.north()));
    }

    @Test
    public void testCellPositionApproximateDirectionToEast() {
        CellPosition from = new CellPosition(2, 2);
        CellPosition to = new CellPosition(2, 5);

        var directions = from.approximateDirectionTo(to);

        assertEquals(1, directions.size());
        assertTrue(directions.contains(Direction.east()));
    }

    @Test
    public void testCellPositionApproximateDirectionToNorthEast() {
        CellPosition from = new CellPosition(2, 2);
        CellPosition to = new CellPosition(0, 4);

        var directions = from.approximateDirectionTo(to);

        assertEquals(2, directions.size());
        assertTrue(directions.contains(Direction.north()));
        assertTrue(directions.contains(Direction.east()));
    }

    @Test
    public void testCellPositionHashCode() {
        CellPosition pos1 = new CellPosition(2, 3);
        CellPosition pos2 = new CellPosition(2, 3);

        assertEquals(pos1.hashCode(), pos2.hashCode());
    }
}
