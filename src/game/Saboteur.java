package game;

import listeners.GameListener;
import timer.TickTimer;
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
    protected final static int TICK_INTERVAL = 10;
    protected final TickTimer saboteurTime;
    protected final List<GameListener> gameListeners;

    protected Saboteur(GameField f, int w, int h, int mineProbability, TimerFactory timerFactory) {
        field = f;
        width = w;
        height = h;
        random = new Random();
        this.mineProbability = mineProbability;
        saboteurTime = timerFactory.getTickTimer(TICK_INTERVAL);
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

    public void start() {
        equipTiles();

        startEquipMines();

        saboteurTime.schedule(new TimerTask() {
            @Override
            public void run() {
                onTick();
            }
        }, TICK_INTERVAL);
    }

    public void deactivate() {
        saboteurTime.stop();
        field.deactivate();
    }

    protected void onTick() {
        tickMines();
        periodicalEquipMines();
    }

    protected void fireTilesInFinishConfiguration() {
        for (GameListener listener : gameListeners) {
            listener.tilesInFinishConfiguration();
        }
    }

    protected void onTileMoved() {
        if (areTilesInFinishConfiguration()) {
            fireTilesInFinishConfiguration();
        }
    }

    protected abstract void equipTiles();

    protected abstract void startEquipMines();

    protected abstract void periodicalEquipMines();

    protected abstract boolean areTilesInFinishConfiguration();

    protected abstract Dimension getFieldSize();

    private void tickMines() {
        for (Mine mine : mines) {
            mine.tick();
        }
    }
}
