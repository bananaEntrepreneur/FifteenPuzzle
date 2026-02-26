package units;

import cell.Cell;
import timer.TickTimer;
import timer.TimerFactory;

public abstract class Mine extends Unit {
    private final int _explosionDelay;
    private TickTimer _explosionTimer;

    public Mine(int explosionDelay) {
        super();
        _explosionDelay = Math.max(1, explosionDelay);
    }

    public void startTimer(TimerFactory timerFactory) {
        _explosionTimer = timerFactory.getTickTimer(_explosionDelay);
        _explosionTimer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                explode();
            }
        }, 1);
    }

    @Override
    public boolean canBelongTo(Cell owner) {
        return owner != null;
    }

    public int getTimeToExplosion() {
        return _explosionTimer != null ? _explosionTimer.getTickInterval() : _explosionDelay;
    }

    public int getExplosionDelay() {
        return _explosionDelay;
    }

    protected void onExploded() {
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
        onExploded();
    }

    protected abstract void applyEffect();

    @Override
    public String toString() {
        return "M";
    }
}
