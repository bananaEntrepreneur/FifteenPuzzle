package field;

import units.Unit;

import java.util.Set;

public class Cell {
    private Set<Unit> _units;

    public boolean putUnit(Unit u) {}

    public Unit getUnits(Class cl) {}

    public boolean extractUnit(Unit u) {}

    public boolean isEmpty() {}

    public Map<Direction, Cell> getNeighbors() {}

    public boolean isNeighbor(Cell other) {}
}
