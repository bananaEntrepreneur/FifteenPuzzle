package view;

import game.Game;
import listeners.GameListener;

import javax.swing.*;

public class GameFrame extends JFrame implements GameListener {

    public GameFrame() {
        Game game = new Game(4, 4);
        game.start();

        GameFieldView mainBox = new GameFieldView(game.getField(), game.getTimerFactory());

        setContentPane(mainBox);
        pack();
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game.addGameListener(this);
    }

    @Override
    public void tilesInFinishConfiguration() {
        JOptionPane.showMessageDialog(this, "Congratulations! You won!");
    }

    @Override
    public void gameIsOver() {
        JOptionPane.showMessageDialog(this, "Game Over!");
    }
}