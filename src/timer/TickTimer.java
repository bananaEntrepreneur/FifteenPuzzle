package timer;

public class TickTimer extends MyTimer {
    private final int tickInterval;
    private int currentTickCount;

    public TickTimer(int tickInterval) {
        super();
        this.tickInterval = tickInterval;
        this.currentTickCount = 0;
    }

    public void tick() {
        if (!isRunning) {
            return;
        }
        
        currentTickCount++;
        if (currentTickCount >= tickInterval) {
            currentTickCount = 0;
            onTick();
        }
    }

    protected void onTick() {

    }

    public int getTickInterval() {
        return tickInterval;
    }
}
