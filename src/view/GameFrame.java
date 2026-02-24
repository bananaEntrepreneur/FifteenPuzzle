package view;

import game.Game;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        Game game = new Game(4, 4);
        game.start();
        
        GameFieldView mainBox = new GameFieldView(game.get_field());

        setContentPane(mainBox);
        pack();
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}