package timer;

import org.junit.jupiter.api.Test;

import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;

public class MyTimerTest {

    @Test
    public void testTimerInitialState() {
        MyTimer timer = new ConcreteTimer();

        assertFalse(timer.isRunning());
        assertNotNull(timer);
    }

    @Test
    public void testTimerStartAfterSchedule() {
        MyTimer timer = new ConcreteTimer();

        timer.schedule(new DummyTask(), 1000);

        assertTrue(timer.isRunning());
    }

    @Test
    public void testTimerStop() {
        MyTimer timer = new ConcreteTimer();
        timer.schedule(new DummyTask(), 1000);

        timer.stop();

        assertFalse(timer.isRunning());
    }

    private static class ConcreteTimer extends MyTimer {
        @Override
        public void schedule(TimerTask task, long period) {
            if (!isRunning && task != null) {
                isRunning = true;
            }
        }

        @Override
        public void stop() {
            if (isRunning) {
                internalTimer.cancel();
                internalTimer = new java.util.Timer();
                isRunning = false;
            }
        }
    }

    private static class DummyTask extends TimerTask {
        @Override
        public void run() {

        }
    }
}
