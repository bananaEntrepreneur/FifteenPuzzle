package units;

import field.Cell;

public class Mine extends Unit {
    private final int _explosionDelay;
    private int _timeToExplosion;

    public Mine(int explosionDelay) {
        super();
        this._explosionDelay = Math.max(1, explosionDelay);
        this._timeToExplosion = explosionDelay;
    }

    @Override
    public boolean canBelongTo(Cell owner) {
        return owner != null;
    }

    public void tick() {
        if (!isActive()) {
            return;
        }
        
        _timeToExplosion--;
        if (_timeToExplosion <= 0) {
            explode();
        }
    }

    protected void explode() {
        if (!isActive()) {
            return;
        }

        Cell currentCell = owner();
        if (currentCell == null) {
            return;
        }

        applyEffect();

        deactivate();
    }

    protected void applyEffect() {
        Cell currentCell = owner();
        if (currentCell == null) {
            return;
        }

        affectCell(currentCell);

        for (Cell neighbor : currentCell.getNeighborCells()) {
            if (neighbor != null) {
                affectCell(neighbor);
            }
        }
    }

    protected void affectCell(Cell cell) {

    }

    public int getTimeToExplosion() {
        return _timeToExplosion;
    }

    public int getExplosionDelay() {
        return _explosionDelay;
    }
}
