package field;

import units.Unit;

import java.util.*;

public class Cell {
    private final Set<Unit> _units;
    private final Map<Direction, Cell> _neighbors;

    public Cell() {
        this._units = new HashSet<>();
        this._neighbors = new HashMap<>();
    }

    public boolean putUnit(Unit u) {
        if (u == null) {
            return false;
        }

        boolean added = u.setOwner(this);

        if(added) {
            _units.add(u);
            return true;
        }
        return false;
    }

    public <T extends Unit> T getUnit(Class<T> cl) {
        if (cl == null) {
            return null;
        }
        
        for (Unit unit : _units) {
            if (cl.isInstance(unit)) {
                return cl.cast(unit);
            }
        }
        return null;
    }

    public <T extends Unit> Collection<T> getUnits(Class<T> cl) {
        if (cl == null) {
            return Collections.emptyList();
        }
        
        List<T> result = new ArrayList<>();
        for (Unit unit : _units) {
            if (cl.isInstance(unit)) {
                result.add(cl.cast(unit));
            }
        }
        return result;
    }

    public boolean extractUnit(Unit u) {
        if (u == null) {
            return false;
        }
        
        boolean removed = _units.remove(u);
        if(removed) {
            u.setOwner(null);
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return _units.isEmpty();
    }

    public boolean hasUnits() {
        return !_units.isEmpty();
    }

    public Collection<Unit> getAllUnits() {
        return Collections.unmodifiableCollection(_units);
    }

    public int getUnitCount() {
        return _units.size();
    }

    public Map<Direction, Cell> getNeighbors() {
        return Collections.unmodifiableMap(_neighbors);
    }

    public void addNeighbor(Direction direction, Cell neighbor) {
        if (direction != null && neighbor != null) {
            _neighbors.put(direction, neighbor);
        }
    }

    public Cell getNeighbor(Direction direction) {
        if (direction == null) {
            return null;
        }
        return _neighbors.get(direction);
    }

    public boolean isNeighbor(Cell other) {
        if (other == null) {
            return false;
        }
        return _neighbors.containsValue(other);
    }

    public Collection<Cell> getNeighborCells() {
        return Collections.unmodifiableCollection(_neighbors.values());
    }

    public boolean hasNeighbors() {
        return !_neighbors.isEmpty();
    }
}
