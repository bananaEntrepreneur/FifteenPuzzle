package field;

import saboteur.SimpleSaboteur;

import java.awt.Dimension;

public class Game {
    private final GameField field;
    private final SimpleSaboteur saboteur;
    private boolean gameOver;

    public Game(int width, int height) {
        this.field = new GameField();
        this.field.setSize(new Dimension(width, height));
        this.saboteur = new SimpleSaboteur(field, width, height);
        this.gameOver = false;
    }

    public void start() {
        saboteur.start();
    }

    public boolean isOver() {
        return gameOver;
    }

    public GameField getField() {
        return field;
    }
}
