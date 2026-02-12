package units;

import field.Cell;

public abstract class Unit {
    private Cell _cell;

    protected boolean canBelongTo(Cell owner) {}

    public Cell owner() {
        return _cell;
    }

    public void activate() {}

    public void deactivate() {}
}
