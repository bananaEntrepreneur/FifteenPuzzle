package units;

import cell.Cell;

public class FreezeMine extends Mine {
    private final int _freezeDuration;

    public FreezeMine(int explosionDelay, int freezeDuration) {
        super(explosionDelay);
        _freezeDuration = Math.max(0, freezeDuration);
    }

    @Override
    protected void applyEffect() {

    }

    private void freeze(Cell cell) {
        if (cell == null) {
            return;
        }

        for (Unit unit : cell.getAllUnits()) {
            if (unit != null && unit.isActive()) {
                unit.deactivate();
            }
        }
    }
}
