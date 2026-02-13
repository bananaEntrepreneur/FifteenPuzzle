package timer;

public class TimerFactory {
    public MillisecondTimer getMillisecondTimer(long intervalMs) {
        return new MillisecondTimer(intervalMs);
    }

    public TickTimer getTickTimer(int tickInterval) {
        return new TickTimer(tickInterval);
    }
}
