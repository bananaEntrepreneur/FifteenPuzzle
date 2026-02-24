package game;

import listeners.GameListener;
import saboteurs.SimpleSaboteur;
import timer.TimerFactory;

import java.awt.Dimension;

public class Game implements GameListener {
    private final GameField _field;
    final TimerFactory _timerFactory = new TimerFactory();
    private final SimpleSaboteur _saboteur;
    private boolean _gameOver;

    public Game(int width, int height) {
        _field = new GameField();
        _field.setSize(new Dimension(width, height));
        _saboteur = new SimpleSaboteur(_field, width, height);
        _gameOver = false;
        _saboteur.addGameListener(this);
    }

    public void start() {
        _saboteur.start();
    }

    public boolean isOver() {
        return _gameOver;
    }

    public GameField getField() {
        return _field;
    }

    public TimerFactory getTimerFactory() { return _timerFactory; }

    @Override
    public void tilesInFinishConfiguration() {
        _gameOver = true;
    }
}
