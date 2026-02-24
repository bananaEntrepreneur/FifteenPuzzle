package timer;

import java.util.TimerTask;

public class MillisecondTimer extends MyTimer {
    private final long _intervalMs;

    public MillisecondTimer(long intervalMs) {
        super();
        _intervalMs = intervalMs;
    }

    public long getInterval() {
        return _intervalMs;
    }

    @Override
    public void schedule(TimerTask task, long period) {

    }

    @Override
    public void stop() {

    }
}
