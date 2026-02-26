package game;

import listeners.GameListener;
import saboteurs.SimpleSaboteur;
import timer.TimerFactory;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements GameListener {
    private final GameField _field;
    private final TimerFactory _timerFactory;
    private final SimpleSaboteur _saboteur;
    private final List<GameListener> _listeners;
    private boolean _gameOver;

    public Game(int width, int height) {
        _timerFactory = new TimerFactory();
        _field = new GameField();
        _field.setSize(new Dimension(width, height));
        _saboteur = new SimpleSaboteur(_field, width, height, _timerFactory);
        _gameOver = false;
        _listeners = new ArrayList<>();
        _saboteur.addGameListener(this);
    }

    public void addGameListener(GameListener listener) {
        if (listener != null) {
            _listeners.add(listener);
        }
    }

    public void removeGameListener(GameListener listener) {
        _listeners.remove(listener);
    }

    public List<GameListener> getGameListeners() {
        return Collections.unmodifiableList(_listeners);
    }

    public void start() {
        _saboteur.start();
    }

    @Override
    public void tilesInFinishConfiguration() {
        _gameOver = true;
        _field.deactivate();
        fireGameIsOver();
    }

    @Override
    public void gameIsOver() {
        fireGameIsOver();
    }

    private void fireGameIsOver() {
        for (GameListener listener : _listeners) {
            listener.gameIsOver();
        }
    }

    public boolean isOver() {
        return _gameOver;
    }

    public GameField getField() {
        return _field;
    }

    public TimerFactory getTimerFactory() { return _timerFactory; }

    public Saboteur getSaboteur() {
        return _saboteur;
    }
}
