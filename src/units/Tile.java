package units;

import field.Cell;

public class Tile extends Unit {
    private final int _number;

    public Tile(int number) {
        super();
        this._number = number;
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
                return moveTo(neighbor);
            }
        }

        return false;
    }

    @Override
    public boolean canBelongTo(Cell owner) {
        return owner != null;
    }

    private boolean moveTo(Cell neighbor) {
        if (!isActive()) {
            return false;
        }

        Cell currentCell = owner();
        if (currentCell == null || neighbor == null) {
            return false;
        }

        if (!neighbor.isEmpty()) {
            return false;
        }

        if (!currentCell.extractUnit(this)) {
            return false;
        }

        return neighbor.putUnit(this);
    }

    public int getNumber() {
        return _number;
    }

    @Override
    public String toString() {
        return "" + _number;
    }
}
