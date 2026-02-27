package game;

import listeners.GameListener;
import timer.TimerFactory;
import units.Mine;

import java.awt.Dimension;
import java.util.*;

public abstract class Saboteur {
    protected final GameField field;
    protected final int width;
    protected final int height;
    protected final Random random;
    protected final HashSet<Mine> mines = new HashSet<>();
    protected final int mineProbability;
    protected final List<GameListener> gameListeners;
    protected final TimerFactory timerFactory;

    protected Saboteur(GameField f, int w, int h, int mineProbability, TimerFactory timerFactory) {
        field = f;
        width = w;
        height = h;
        random = new Random();
        this.mineProbability = mineProbability;
        this.timerFactory = timerFactory;
        gameListeners = new ArrayList<>();
    }

    public void addGameListener(GameListener listener) {
        if (listener != null) {
            gameListeners.add(listener);
        }
    }

    public void removeGameListener(GameListener listener) {
        gameListeners.remove(listener);
    }

    public List<GameListener> getGameListeners() {
        return Collections.unmodifiableList(gameListeners);
    }

    public void start() {
        Dimension fieldSize = getFieldSize();
        field.setSize(fieldSize);

        equipTiles();

        startEquipMines();
    }

    public void deactivate() {
        field.deactivate();
    }

    protected void fireTilesInFinishConfiguration() {
        for (GameListener listener : gameListeners) {
            listener.tilesInFinishConfiguration();
        }
    }

    public void onTileMoved() {
        if (areTilesInFinishConfiguration()) {
            fireTilesInFinishConfiguration();
        }
        periodicalEquipMines();
    }

    protected abstract void equipTiles();

    protected abstract void startEquipMines();

    protected abstract void periodicalEquipMines();

    protected abstract boolean areTilesInFinishConfiguration();

    protected abstract Dimension getFieldSize();
}
