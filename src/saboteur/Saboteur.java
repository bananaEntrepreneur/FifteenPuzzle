package saboteur;

import java.awt.geom.Dimension2D;

public abstract class Saboteur {
    public void start() {
        equipTiles();
        startEquipMines();
    }

    protected abstract void equipTiles();

    protected abstract void startEquipMines();

    protected abstract void periodicalEquipMines();

    protected abstract boolean areTilesInFinishConfiguration();

    protected abstract Dimension2D getFieldSize();

    public void deactivate() {}
}
