package timer;

import org.junit.jupiter.api.Test;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class MillisecondTimerTest {

    @Test
    public void testTimerCreation() {
        MillisecondTimer timer = new MillisecondTimer(1000);

        assertNotNull(timer);
        assertEquals(1000, timer.getInterval());
        assertFalse(timer.isRunning());
    }

    @Test
    public void testTimerScheduleStartsTimer() {
        MillisecondTimer timer = new MillisecondTimer(1000);

        timer.schedule(new DummyTask(), 1000);

        assertTrue(timer.isRunning());
    }

    @Test
    public void testTimerTickExecutesTask() {
        MillisecondTimer timer = new MillisecondTimer(1000);
        AtomicBoolean taskExecuted = new AtomicBoolean(false);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                taskExecuted.set(true);
            }
        }, 1000);

        timer.tick();

        assertTrue(taskExecuted.get());
    }

    @Test
    public void testTimerTickWhenNotRunning() {
        MillisecondTimer timer = new MillisecondTimer(1000);
        AtomicBoolean taskExecuted = new AtomicBoolean(false);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                taskExecuted.set(true);
            }
        }, 1000);

        timer.stop();
        timer.tick();

        assertFalse(taskExecuted.get());
    }

    @Test
    public void testTimerStop() {
        MillisecondTimer timer = new MillisecondTimer(1000);
        timer.schedule(new DummyTask(), 1000);

        timer.stop();

        assertFalse(timer.isRunning());
    }

    @Test
    public void testTimerStopCreatesNewInternalTimer() {
        MillisecondTimer timer = new MillisecondTimer(1000);
        timer.schedule(new DummyTask(), 1000);

        timer.stop();

        assertNotNull(timer);
        assertFalse(timer.isRunning());
    }

    @Test
    public void testScheduleWithNullTask() {
        MillisecondTimer timer = new MillisecondTimer(1000);

        timer.schedule(null, 1000);

        assertFalse(timer.isRunning());
    }

    @Test
    public void testScheduleWhenAlreadyRunning() {
        MillisecondTimer timer = new MillisecondTimer(1000);
        AtomicInteger executionCount = new AtomicInteger(0);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                executionCount.incrementAndGet();
            }
        }, 1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                executionCount.incrementAndGet();
            }
        }, 1000);

        timer.tick();

        assertEquals(1, executionCount.get());
    }

    private static class DummyTask extends TimerTask {
        @Override
        public void run() {

        }
    }
}
