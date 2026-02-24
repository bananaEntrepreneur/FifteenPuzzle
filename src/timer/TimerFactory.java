package timer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TimerFactory {
    private final HashSet<MyTimer> _timers = new HashSet<>();

    public MillisecondTimer getMillisecondTimer(long msInterval) {
        MillisecondTimer msTimer = new MillisecondTimer(msInterval);
        _timers.add(msTimer);
        return msTimer;
    }

    public TickTimer getTickTimer(int tickInterval) {
        TickTimer msTimer = new TickTimer(tickInterval);
        _timers.add(msTimer);
        return msTimer;
    }

    public List<TickTimer> getTickTimers() {
        List<TickTimer> tickTimers = new ArrayList<>();
        for (MyTimer timer : _timers) {
            if (timer instanceof TickTimer) {
                tickTimers.add((TickTimer) timer);
            }
        }
        return tickTimers;
    }

    public void tickTimers() {
        for (TickTimer tickTimer : getTickTimers()) {
            tickTimer.tick();
        }
    }
}
