package timer;

import java.util.Timer;
import java.util.TimerTask;

public class MillisecondTimer extends MyTimer {
    private final long _intervalMs;
    private TimerTask _scheduledTask;

    public MillisecondTimer(long intervalMs) {
        super();
        _intervalMs = intervalMs;
    }

    public long getInterval() {
        return _intervalMs;
    }

    @Override
    public void schedule(TimerTask task, long period) {
        if (!isRunning && task != null) {
            _scheduledTask = task;
            internalTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    tick();
                }
            }, period);
            isRunning = true;
        }
    }

    public void tick() {
        if (!isRunning) {
            return;
        }

        if (_scheduledTask != null) {
            _scheduledTask.run();
        }
    }

    @Override
    public void stop() {
        if (isRunning) {
            internalTimer.cancel();
            internalTimer = new Timer();
            isRunning = false;
        }
    }
}
