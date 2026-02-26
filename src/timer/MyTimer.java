package timer;

import java.util.TimerTask;
import java.util.Timer;

public abstract class MyTimer {
    protected Timer internalTimer;
    protected boolean isRunning;

    public MyTimer() {
        internalTimer = new Timer();
        isRunning = false;
    }

    public abstract void schedule(TimerTask task, long period);

    public abstract void stop();

    public boolean isRunning() {
        return isRunning;
    }
}
