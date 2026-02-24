package units;

import cell.Cell;

public abstract class Mine extends Unit {
    private final int _explosionDelay;
    private int _timeToExplosion;

    public Mine(int explosionDelay) {
        super();
        _explosionDelay = Math.max(1, explosionDelay);
        _timeToExplosion = explosionDelay;
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

    public int getTimeToExplosion() {
        return _timeToExplosion;
    }

    public int getExplosionDelay() {
        return _explosionDelay;
    }

    protected void mineExploded() {
        fireStateChanged();
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

    protected abstract void applyEffect();

    @Override
    public String toString() {
        return "M" + _timeToExplosion;
    }
}
