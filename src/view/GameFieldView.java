package view;

import field.Cell;
import field.GameField;
import units.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameFieldView extends JPanel {

    private final GameField _field;

    public GameFieldView(GameField field) {
        _field = field;

        setLayout(new GridLayout(_field.getHeight(), _field.getWidth()));

        Dimension fieldDimension = new Dimension(CellWidget.CELL_SIZE*_field.getWidth(), CellWidget.CELL_SIZE*_field.getHeight());

        setPreferredSize(fieldDimension);
        setMinimumSize(fieldDimension);
        setMaximumSize(fieldDimension);

        for (Cell c: _field) {
            add( new CellWidget( c ) );
        }

        addMouseListener(new MouseController());
        setFocusable(true);
    }

    private class MouseController extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            CellWidget clickedWidget = (CellWidget) e.getSource();
            Cell clickedCell = clickedWidget.getCell();
            Tile tile = clickedCell.getUnit(Tile.class);

            if (tile != null) {
                tile.push();
                repaint();
            }
        }
    }
}