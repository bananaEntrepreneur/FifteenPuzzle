package timer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TimerFactoryTest {

    @Test
    public void testFactoryCreation() {
        TimerFactory factory = new TimerFactory();

        assertNotNull(factory);
    }

    @Test
    public void testCreateMillisecondTimer() {
        TimerFactory factory = new TimerFactory();

        MillisecondTimer timer = factory.getMillisecondTimer(1000);

        assertNotNull(timer);
        assertEquals(1000, timer.getInterval());
    }

    @Test
    public void testCreateTickTimer() {
        TimerFactory factory = new TimerFactory();

        TickTimer timer = factory.getTickTimer(10);

        assertNotNull(timer);
        assertEquals(10, timer.getTickInterval());
    }

    @Test
    public void testCreateMultipleMillisecondTimers() {
        TimerFactory factory = new TimerFactory();

        MillisecondTimer timer1 = factory.getMillisecondTimer(500);
        MillisecondTimer timer2 = factory.getMillisecondTimer(1000);

        assertNotNull(timer1);
        assertNotNull(timer2);
        assertNotSame(timer1, timer2);
        assertEquals(500, timer1.getInterval());
        assertEquals(1000, timer2.getInterval());
    }

    @Test
    public void testCreateMultipleTickTimers() {
        TimerFactory factory = new TimerFactory();

        TickTimer timer1 = factory.getTickTimer(5);
        TickTimer timer2 = factory.getTickTimer(10);

        assertNotNull(timer1);
        assertNotNull(timer2);
        assertNotSame(timer1, timer2);
        assertEquals(5, timer1.getTickInterval());
        assertEquals(10, timer2.getTickInterval());
    }

    @Test
    public void testGetTickTimersEmpty() {
        TimerFactory factory = new TimerFactory();

        List<TickTimer> timers = factory.getTickTimers();

        assertNotNull(timers);
        assertTrue(timers.isEmpty());
    }

    @Test
    public void testGetTickTimersWithMixedTimers() {
        TimerFactory factory = new TimerFactory();

        MillisecondTimer msTimer = factory.getMillisecondTimer(500);
        TickTimer tickTimer1 = factory.getTickTimer(5);
        TickTimer tickTimer2 = factory.getTickTimer(10);

        List<TickTimer> timers = factory.getTickTimers();

        assertNotNull(timers);
        assertEquals(2, timers.size());
        assertTrue(timers.contains(tickTimer1));
        assertTrue(timers.contains(tickTimer2));
        assertFalse(timers.contains(msTimer));
    }

    @Test
    public void testTickTimers() {
        TimerFactory factory = new TimerFactory();
        TickTimer timer = factory.getTickTimer(1);
        java.util.concurrent.atomic.AtomicInteger tickCount = new java.util.concurrent.atomic.AtomicInteger(0);

        timer.addTickListener(() -> tickCount.incrementAndGet());
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {

            }
        }, 1000);

        factory.tickTimers();

        assertEquals(1, tickCount.get());
    }

    @Test
    public void testTickTimersWithMultipleTimers() {
        TimerFactory factory = new TimerFactory();
        TickTimer timer1 = factory.getTickTimer(1);
        TickTimer timer2 = factory.getTickTimer(1);
        java.util.concurrent.atomic.AtomicInteger tickCount = new java.util.concurrent.atomic.AtomicInteger(0);

        timer1.addTickListener(() -> tickCount.incrementAndGet());
        timer2.addTickListener(() -> tickCount.incrementAndGet());
        timer1.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                // No-op
            }
        }, 1000);
        timer2.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                // No-op
            }
        }, 1000);

        factory.tickTimers();

        assertEquals(2, tickCount.get());
    }

    @Test
    public void testTickTimersWithMixedTimerTypes() {
        TimerFactory factory = new TimerFactory();
        MillisecondTimer msTimer = factory.getMillisecondTimer(500);
        TickTimer tickTimer = factory.getTickTimer(1);
        java.util.concurrent.atomic.AtomicInteger tickCount = new java.util.concurrent.atomic.AtomicInteger(0);

        tickTimer.addTickListener(() -> tickCount.incrementAndGet());
        tickTimer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
            }
        }, 1000);

        factory.tickTimers();

        assertEquals(1, tickCount.get());
    }
}
