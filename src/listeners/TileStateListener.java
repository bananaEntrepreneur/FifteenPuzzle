package listeners;

import game.Saboteur;

import java.util.EventObject;

public class TileStateListener implements StateChangeListeners {
    private final Saboteur _saboteur;

    public TileStateListener(Saboteur saboteur) {
        _saboteur = saboteur;
    }

    @Override
    public void stateChanged(EventObject event) {
        _saboteur.onTileMoved();
    }
}
