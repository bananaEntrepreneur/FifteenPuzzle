package units;

import field.Cell;

public abstract class Unit {
    private Cell _cell;
    private boolean _active;

    public Unit() {
        this._active = true;
    }

    protected abstract boolean canBelongTo(Cell owner);

    public Cell owner() {
        return _cell;
    }

    public void setOwner(Cell cell) {
        if(!canBelongTo(cell)) {
            throw new IllegalArgumentException("Cannot belong to this cell!");
        }
        this._cell = cell;
    }

    public boolean isActive() {
        return _active;
    }

    public void activate() {
        this._active = true;
    }

    public void deactivate() {
        this._active = false;
    }
}
