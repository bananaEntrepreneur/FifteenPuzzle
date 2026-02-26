package units;

import cell.Cell;
import listeners.StateChangeListeners;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public abstract class Unit {
    private Cell _cell;
    private boolean _active;
    private final List<StateChangeListeners> _listeners = new ArrayList<>();

    public Unit() {
        _active = true;
    }

    public void addListener(StateChangeListeners listener) {
        if (listener != null) {
            _listeners.add(listener);
        }
    }

    public void removeListener(StateChangeListeners listener) {
        _listeners.remove(listener);
    }

    public Cell owner() {
        return _cell;
    }

    public boolean setOwner(Cell cell) {
        if (!canBelongTo(cell)) {
            throw new IllegalArgumentException("Cannot belong to this cell!");
        }
        _cell = cell;
        return true;
    }

    public boolean isActive() {
        return _active;
    }

    public void activate() {
        _active = true;
        fireStateChanged();
    }

    public void deactivate() {
        _active = false;
        fireStateChanged();
    }

    protected void fireStateChanged() {
        for (StateChangeListeners listener : _listeners) {
            listener.stateChanged(new EventObject(this));
        }
    }

    protected abstract boolean canBelongTo(Cell owner);
}
