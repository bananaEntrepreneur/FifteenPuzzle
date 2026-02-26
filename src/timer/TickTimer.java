package timer;

import listeners.TickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TickTimer extends MyTimer {
    private final int _tickInterval;
    private int _currentTickCount;
    private TimerTask _scheduledTask;
    private final List<TickListener> _listeners;

    public TickTimer(int tickInterval) {
        super();
        _tickInterval = tickInterval;
        _currentTickCount = 0;
        _listeners = new ArrayList<>();
    }

    public void addTickListener(TickListener listener) {
        if (listener != null) {
            _listeners.add(listener);
        }
    }

    public void removeTickListener(TickListener listener) {
        _listeners.remove(listener);
    }

    @Override
    public void schedule(TimerTask task, long period) {
        if (!isRunning && task != null) {
            _scheduledTask = task;
            isRunning = true;
        }
    }

    public void tick() {
        if (!isRunning) {
            return;
        }

        _currentTickCount++;
        fireTick();
        if (_currentTickCount >= _tickInterval) {
            _currentTickCount = 0;
            if (_scheduledTask != null) {
                _scheduledTask.run();
            }
        }
    }

    private void fireTick() {
        for (TickListener listener : _listeners) {
            listener.onTick();
        }
    }

    @Override
    public void stop() {
        if (isRunning) {
            internalTimer.cancel();
            internalTimer = new Timer();
            isRunning = false;
            _currentTickCount = 0;
        }
    }

    public int getTickInterval() {
        return _tickInterval;
    }
}
