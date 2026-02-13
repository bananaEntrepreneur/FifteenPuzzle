package timer;

import java.util.TimerTask;
import java.util.Timer;

public abstract class MyTimer {
    protected Timer internalTimer;
    protected boolean isRunning;

    public MyTimer() {
        this.internalTimer = new java.util.Timer();
        this.isRunning = false;
    }

    public void schedule(TimerTask task, long period) {
        if (!isRunning) {
            internalTimer.scheduleAtFixedRate(task, 0, period);
            isRunning = true;
        }
    }

    public void stop() {
        if (isRunning) {
            internalTimer.cancel();
            internalTimer = new java.util.Timer();
            isRunning = false;
        }
    }
}
