package listeners;

import timer.TimerFactory;
import units.FreezeMine;

import java.util.EventObject;

public class MineStateListener implements StateChangeListeners {
    private final TimerFactory _timerFactory;

    public MineStateListener(TimerFactory timerFactory) {
        _timerFactory = timerFactory;
    }

    @Override
    public void stateChanged(EventObject event) {
        if (event.getSource() instanceof FreezeMine) {
            FreezeMine freezeMine = (FreezeMine) event.getSource();
            freezeMine.startFreezeTimer(_timerFactory);
        }
    }
}
