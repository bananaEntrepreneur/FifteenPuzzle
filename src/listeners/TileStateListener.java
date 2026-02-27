package listeners;

import game.Saboteur;

import java.util.EventObject;

/**
 * Listener that notifies the saboteur when a tile moves.
 */
public class TileStateListener extends UnitStateListener {
    private final Saboteur _saboteur;

    public TileStateListener(Saboteur saboteur) {
        _saboteur = saboteur;
    }

    @Override
    public void onEvent(EventObject event) {
        _saboteur.onTileMoved();
    }
}
