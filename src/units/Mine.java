package units;

import field.Cell;

public class Mine extends Unit {
    @Override
    protected boolean canBelongTo(Cell owner) {}

    protected void explode() {}

    protected void applyEffect() {}
}
