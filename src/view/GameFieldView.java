package view;

import cell.Cell;
import game.GameField;
import units.Tile;

import javax.swing.*;
import java.awt.*;

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
            add( new CellWidget( c, this ) );
        }

        setFocusable(true);
    }

    public void handleCellClick(CellWidget cellWidget) {
        Cell clickedCell = cellWidget.getCell();
        Tile tile = clickedCell.getUnit(Tile.class);

        if (tile != null) {
            if(tile.push()) {
                repaint();
            }
        }
    }
}