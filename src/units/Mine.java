package units;

import cell.Cell;

public class Mine extends Unit {
    private final int _explosionDelay;
    private int _timeToExplosion;

    public Mine(int explosionDelay) {
        super();
        this._explosionDelay = Math.max(1, explosionDelay);
        this._timeToExplosion = explosionDelay;
    }

    protected void mineExploded() {
        fireStateChanged();
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

        mineExploded();
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
        Tile tile = cell.getUnit(Tile.class);
        if (tile != null) {
            tile.deactivate();
        }
    }

    public int getTimeToExplosion() {
        return _timeToExplosion;
    }

    public int getExplosionDelay() {
        return _explosionDelay;
    }

    @Override
    public String toString() {
        return "M" + _timeToExplosion;
    }
}
