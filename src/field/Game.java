package field;

import java.awt.Dimension;

public class Game {
    private final GameField _field;
    private final SimpleSaboteur _saboteur;
    private boolean _gameOver;

    public Game(int width, int height) {
        this._field = new GameField();
        this._field.setSize(new Dimension(width, height));
        this._saboteur = new SimpleSaboteur(_field, width, height);
        this._gameOver = false;
    }

    public void start() {
        _saboteur.start();
    }

    public boolean isOver() {
        return _gameOver;
    }

    public GameField get_field() {
        return _field;
    }
}
