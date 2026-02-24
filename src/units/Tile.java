package units;

import cell.Cell;

public class Tile extends Unit {
    private final int _number;

    public Tile(int number) {
        super();
        _number = number;
    }

    public boolean push() {
        if (!isActive()) {
            return false;
        }

        Cell currentCell = owner();
        if (currentCell == null) {
            return false;
        }

        for (Cell neighbor : currentCell.getNeighborCells()) {
            if (neighbor != null && neighbor.isEmpty()) {
                boolean moved = moveTo(neighbor);
                if (moved) {
                    tileMoved();
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean canBelongTo(Cell c) {
        return c == null || c.isEmpty();
    }

    public void tileMoved() {
        fireStateChanged();
    }

    public int getNumber() {
        return _number;
    }

    @Override
    public String toString() {
        return "" + _number;
    }

    private boolean hasTile(Cell cell) {
        return cell.getUnit(Tile.class) != null;
    }

    private boolean moveTo(Cell neighbor) {
        if (!isActive()) {
            return false;
        }

        Cell currentCell = owner();
        if (currentCell == null || neighbor == null) {
            return false;
        }

        if (hasTile(neighbor)) {
            return false;
        }

        if (!currentCell.extractUnit(this)) {
            return false;
        }

        boolean placed = neighbor.putUnit(this);
        if (placed) {
            fireStateChanged();
        }
        return placed;
    }
}
