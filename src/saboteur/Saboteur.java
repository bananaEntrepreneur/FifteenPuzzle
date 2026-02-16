package saboteur;

import java.awt.Dimension;

public abstract class Saboteur {
    public void start() {
        equipTiles();
        startEquipMines();
    }

    protected abstract void equipTiles();

    protected abstract void startEquipMines();

    protected abstract void periodicalEquipMines();

    protected abstract boolean areTilesInFinishConfiguration();

    protected abstract Dimension getFieldSize();

    public void deactivate() {}
}
