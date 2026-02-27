package units;

import cell.Cell;
import timer.MillisecondTimer;
import timer.TimerFactory;

import java.util.HashSet;
import java.util.Set;

public class FreezeMine extends Mine {
    private final int _freezeDuration;
    private final Set<Unit> _frozenUnits;
    private MillisecondTimer _freezeTimer;
    private boolean _exploded;
    private TimerFactory _timerFactory;

    public FreezeMine(int explosionDelay, int freezeDuration) {
        super(explosionDelay);
        _freezeDuration = Math.max(0, freezeDuration);
        _frozenUnits = new HashSet<>();
        _exploded = false;
    }

    public int getFreezeDuration() {
        return _freezeDuration;
    }

    public boolean isFreezing() {
        return _exploded && _freezeTimer != null && _freezeTimer.isRunning();
    }

    @Override
    protected void onExploded() {
        startFreezeTimer();
    }

    private void startFreezeTimer() {
        if (_freezeDuration > 0 && _freezeTimer == null && _timerFactory != null) {
            MillisecondTimer freezeTimer = _timerFactory.getMillisecondTimer(1000);
            _freezeTimer = freezeTimer;
            freezeTimer.schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    unfreezeAll();
                }
            }, _freezeDuration * 1000L);
        }
    }

    public void setTimerFactory(TimerFactory timerFactory) {
        _timerFactory = timerFactory;
    }

    @Override
    protected void applyEffect() {
        _exploded = true;

        Cell c = owner();
        freeze(c);
        for (Cell neighbor : c.getNeighborCells()) {
            freeze(neighbor);
        }
    }

    private void freeze(Cell cell) {
        if (cell == null) {
            return;
        }

        for (Unit unit : cell.getAllUnits()) {
            if (unit != null && unit.isActive()) {
                unit.deactivate();
                _frozenUnits.add(unit);
            }
        }
    }

    private void unfreezeAll() {
        for (Unit unit : _frozenUnits) {
            if (unit != null && !unit.isActive()) {
                unit.activate();
            }
        }
        _frozenUnits.clear();
        if (_freezeTimer != null) {
            _freezeTimer.stop();
        }
    }
}
