package units;

import cell.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MineTest {

    @Test
    public void testMineCreation() {
        TestMine mine = new TestMine(5);

        assertNotNull(mine);
        assertEquals(5, mine.getExplosionDelay());
        assertEquals(5, mine.getTimeToExplosion());
        assertTrue(mine.isActive());
    }

    @Test
    public void testMineCreationWithInvalidDelay() {
        TestMine mine = new TestMine(-1);

        assertEquals(1, mine.getExplosionDelay());
        assertEquals(1, mine.getTimeToExplosion());
    }

    @Test
    public void testMineToString() {
        TestMine mine = new TestMine(3);

        assertEquals("M", mine.toString());
    }

    @Test
    public void testMineSetOwner() {
        TestMine mine = new TestMine(5);
        Cell cell = new Cell(0, 0);

        boolean result = mine.setOwner(cell);

        assertTrue(result);
        assertEquals(cell, mine.owner());
    }

    @Test
    public void testMineCanBelongToCell() {
        TestMine mine = new TestMine(5);
        Cell cell = new Cell(0, 0);

        assertTrue(mine.canBelongTo(cell));
    }

    @Test
    public void testMineCanBelongToNullCell() {
        TestMine mine = new TestMine(5);

        assertFalse(mine.canBelongTo(null));
    }

    @Test
    public void testMineExplodeDeactivates() {
        TestMine mine = new TestMine(1);
        Cell cell = new Cell(0, 0);
        cell.putUnit(mine);

        mine.explode();

        assertFalse(mine.isActive());
    }

    @Test
    public void testMineActivate() {
        TestMine mine = new TestMine(5);
        mine.deactivate();

        mine.activate();

        assertTrue(mine.isActive());
    }

    // Test implementation of Mine
    private static class TestMine extends Mine {
        private boolean exploded = false;

        public TestMine(int explosionDelay) {
            super(explosionDelay);
        }

        @Override
        protected void applyEffect() {
            exploded = true;
        }

        public boolean isExploded() {
            return exploded;
        }
    }
}
