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
        this._active = true;
    }

    public void addListener(StateChangeListeners listener) {
        if (listener != null) {
            _listeners.add(listener);
        }
    }

    public void removeListener(StateChangeListeners listener) {
        _listeners.remove(listener);
    }

    protected void fireStateChanged() {
        for (StateChangeListeners listener : _listeners) {
            listener.stateChanged(new EventObject(this));
        }
    }

    protected abstract boolean canBelongTo(Cell owner);

    public Cell owner() {
        return _cell;
    }

    public boolean setOwner(Cell cell) {
        if(!canBelongTo(cell)) {
            throw new IllegalArgumentException("Cannot belong to this cell!");
        }
        this._cell = cell;
        return true;
    }

    public boolean isActive() {
        return _active;
    }

    public void activate() {
        this._active = true;
        fireStateChanged();
    }

    public void deactivate() {
        this._active = false;
        fireStateChanged();
    }
}
