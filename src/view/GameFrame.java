package view;

import field.GameField;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        GameField field = new GameField(4, 4);
        GameFieldView mainBox = new GameFieldView(field);

        setContentPane(mainBox);
        pack();
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}