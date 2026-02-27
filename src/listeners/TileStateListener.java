package listeners;

import game.Saboteur;

import java.util.EventObject;

public class TileStateListener implements EventListener<EventObject> {
    private final Saboteur _saboteur;

    public TileStateListener(Saboteur saboteur) {
        _saboteur = saboteur;
    }

    @Override
    public void onEvent(EventObject event) {
        _saboteur.onTileMoved();
    }
}
