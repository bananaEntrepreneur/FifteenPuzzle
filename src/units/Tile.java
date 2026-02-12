package units;

import java.util.Iterator;

import field.Cell;

public class Tile extends Unit {
    public boolean push() {}

    @Override
    protected boolean canBelongTo(Cell owner) {}

    private boolean moveTo(Cell neighbor) {}
}
