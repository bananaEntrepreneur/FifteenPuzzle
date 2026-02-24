package game;

import cell.Cell;
import listeners.GameListener;
import timer.TickTimer;
import timer.TimerFactory;
import units.Mine;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

public abstract class Saboteur {
    protected final GameField _field;
    protected final int _width;
    protected final int _height;
    protected final Random _random;
    protected final int _mineProbability;
    protected final static int TICK_INTERVAL = 10;
    protected final TickTimer _saboteurTime;
    protected final List<GameListener> _gameListeners;

    protected Saboteur(GameField field, int width, int height, int mineProbability, TimerFactory timerFactory) {
        _field = field;
        _width = width;
        _height = height;
        _random = new Random();
        _mineProbability = mineProbability;
        _saboteurTime = timerFactory.getTickTimer(TICK_INTERVAL);
        _gameListeners = new ArrayList<>();
    }

    public void addGameListener(GameListener listener) {
        if (listener != null) {
            _gameListeners.add(listener);
        }
    }

    public void removeGameListener(GameListener listener) {
        _gameListeners.remove(listener);
    }

    public void start() {
        equipTiles();

        startEquipMines();

        _saboteurTime.schedule(new TimerTask() {
            @Override
            public void run() {
                onTick();
            }
        }, TICK_INTERVAL);
    }

    public void deactivate() {
        _saboteurTime.stop();
        _field.deactivate();
    }

    protected void onTick() {
        tickMines();
        periodicalEquipMines();
    }

    protected void fireTilesInFinishConfiguration() {
        for (GameListener listener : _gameListeners) {
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
        for (Cell cell : _field) {
            for (var unit : cell.getAllUnits()) {
                if (unit instanceof Mine) {
                    ((Mine) unit).tick();
                }
            }
        }
    }
}
