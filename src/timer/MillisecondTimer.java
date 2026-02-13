package timer;

public class MillisecondTimer extends MyTimer {
    private final long intervalMs;

    public MillisecondTimer(long intervalMs) {
        super();
        this.intervalMs = intervalMs;
    }

    public long getInterval() {
        return intervalMs;
    }
}
