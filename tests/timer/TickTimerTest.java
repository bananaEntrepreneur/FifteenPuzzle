package timer;

import listeners.TickListener;
import org.junit.jupiter.api.Test;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class TickTimerTest {

    @Test
    public void testTimerCreation() {
        TickTimer timer = new TickTimer(10);

        assertNotNull(timer);
        assertEquals(10, timer.getTickInterval());
        assertFalse(timer.isRunning());
    }

    @Test
    public void testTimerScheduleStartsTimer() {
        TickTimer timer = new TickTimer(10);

        timer.schedule(new DummyTask(), 1000);

        assertTrue(timer.isRunning());
    }

    @Test
    public void testTimerTickIncrementsCount() {
        TickTimer timer = new TickTimer(1);
        AtomicInteger tickCount = new AtomicInteger(0);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tickCount.incrementAndGet();
            }
        }, 1000);

        timer.tick();

        assertEquals(1, tickCount.get());
    }

    @Test
    public void testTimerTickFiresListener() {
        TickTimer timer = new TickTimer(1);
        AtomicBoolean listenerCalled = new AtomicBoolean(false);

        timer.addTickListener(() -> listenerCalled.set(true));
        timer.schedule(new DummyTask(), 1000);

        timer.tick();

        assertTrue(listenerCalled.get());
    }

    @Test
    public void testTimerTickFiresMultipleListeners() {
        TickTimer timer = new TickTimer(1);
        AtomicInteger listenerCount = new AtomicInteger(0);

        timer.addTickListener(() -> listenerCount.incrementAndGet());
        timer.addTickListener(() -> listenerCount.incrementAndGet());
        timer.addTickListener(() -> listenerCount.incrementAndGet());
        timer.schedule(new DummyTask(), 1000);

        timer.tick();

        assertEquals(3, listenerCount.get());
    }

    @Test
    public void testTimerTickWhenNotRunning() {
        TickTimer timer = new TickTimer(10);
        AtomicBoolean listenerCalled = new AtomicBoolean(false);

        timer.addTickListener(() -> listenerCalled.set(true));
        timer.schedule(new DummyTask(), 1000);
        timer.stop();

        timer.tick();

        assertFalse(listenerCalled.get());
    }

    @Test
    public void testTimerTickResetsCountAfterInterval() {
        TickTimer timer = new TickTimer(2);
        AtomicInteger executionCount = new AtomicInteger(0);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                executionCount.incrementAndGet();
            }
        }, 1000);

        timer.tick(); // count = 1, no execution
        timer.tick(); // count = 2, executes and resets

        assertEquals(1, executionCount.get());
    }

    @Test
    public void testTimerTickMultipleIntervals() {
        TickTimer timer = new TickTimer(2);
        AtomicInteger executionCount = new AtomicInteger(0);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                executionCount.incrementAndGet();
            }
        }, 1000);

        timer.tick(); // count = 1
        timer.tick(); // count = 2, executes (1)
        timer.tick(); // count = 1
        timer.tick(); // count = 2, executes (2)

        assertEquals(2, executionCount.get());
    }

    @Test
    public void testTimerStop() {
        TickTimer timer = new TickTimer(10);
        timer.schedule(new DummyTask(), 1000);

        timer.stop();

        assertFalse(timer.isRunning());
    }

    @Test
    public void testTimerStopResetsTickCount() {
        TickTimer timer = new TickTimer(5);
        timer.schedule(new DummyTask(), 1000);

        timer.tick();
        timer.tick();
        timer.stop();
        timer.schedule(new DummyTask(), 1000);
        timer.tick();

        assertTrue(timer.isRunning());
    }

    @Test
    public void testRemoveTickListener() {
        TickTimer timer = new TickTimer(1);
        AtomicInteger listenerCount = new AtomicInteger(0);

        TickListener listener = () -> listenerCount.incrementAndGet();
        timer.addTickListener(listener);
        timer.removeTickListener(listener);
        timer.schedule(new DummyTask(), 1000);

        timer.tick();

        assertEquals(0, listenerCount.get());
    }

    @Test
    public void testAddNullListener() {
        TickTimer timer = new TickTimer(1);

        timer.addTickListener(null);

        assertNotNull(timer);
    }

    @Test
    public void testScheduleWithNullTask() {
        TickTimer timer = new TickTimer(10);

        timer.schedule(null, 1000);

        assertFalse(timer.isRunning());
    }

    private static class DummyTask extends TimerTask {
        @Override
        public void run() {
        }
    }
}
