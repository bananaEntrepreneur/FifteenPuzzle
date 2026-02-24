package timer;

import java.util.Timer;
import java.util.TimerTask;

public class TickTimer extends MyTimer {
    private final int _tickInterval;
    private int _currentTickCount;
    private TimerTask _scheduledTask;

    public TickTimer(int tickInterval) {
        super();
        _tickInterval = tickInterval;
        _currentTickCount = 0;
    }

    @Override
    public void schedule(TimerTask task, long period) {
        if (!isRunning && task != null) {
            _scheduledTask = task;
            internalTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    tick();
                }
            }, 0, period);
            isRunning = true;
        }
    }

    public void tick() {
        if (!isRunning) {
            return;
        }

        _currentTickCount++;
        if (_currentTickCount >= _tickInterval) {
            _currentTickCount = 0;
            if (_scheduledTask != null) {
                _scheduledTask.run();
            }
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
