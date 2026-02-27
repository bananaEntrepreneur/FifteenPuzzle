package listeners;

import units.Unit;

import java.util.EventObject;

/**
 * Base class for unit state change listeners.
 * Handles registration with units and provides type-safe event handling.
 */
public abstract class UnitStateListener implements EventListener<EventObject> {
    
    /**
     * Registers this listener with the given unit.
     */
    public void attachTo(Unit unit) {
        if (unit != null) {
            unit.addListener(this);
        }
    }
    
    /**
     * Unregisters this listener from the given unit.
     */
    public void detachFrom(Unit unit) {
        if (unit != null) {
            unit.removeListener(this);
        }
    }
}
