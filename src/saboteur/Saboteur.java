package saboteur;

import java.awt.geom.Dimension2D;

public abstract class Saboteur {
    public void start() {}

    protected void equipTiles() {}

    protected void startEquipMines() {}

    protected void periodicalEquipMines() {}

    protected boolean areTilesInFinishConfiguration() {}

    protected Dimension2D getFieldSize() {}

    public void deactivate() {}
}
