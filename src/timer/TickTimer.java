package timer;

import java.util.TimerTask;

public class TickTimer extends MyTimer {
    private final int tickInterval;
    private int currentTickCount;
    private TimerTask scheduledTask;

    public TickTimer(int tickInterval) {
        super();
        this.tickInterval = tickInterval;
        this.currentTickCount = 0;
    }

    @Override
    public void schedule(TimerTask task, long period) {
        if (!isRunning && task != null) {
            scheduledTask = task;
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

        currentTickCount++;
        if (currentTickCount >= tickInterval) {
            currentTickCount = 0;
            if (scheduledTask != null) {
                scheduledTask.run();
            }
        }
    }

    @Override
    public void stop() {
        if (isRunning) {
            internalTimer.cancel();
            internalTimer = new java.util.Timer();
            isRunning = false;
            currentTickCount = 0;
        }
    }

    public int getTickInterval() {
        return tickInterval;
    }
}
