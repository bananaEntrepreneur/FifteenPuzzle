package view;

import cell.Cell;
import game.GameField;
import listeners.TickListener;
import timer.TickTimer;
import timer.TimerFactory;
import units.Tile;

import javax.swing.*;
import java.awt.*;

public class GameFieldView extends JPanel implements TickListener {

    private final GameField _field;
    private final TimerFactory _timerFactory;

    public GameFieldView(GameField field, TimerFactory timerFactory) {
        _field = field;
        _timerFactory = timerFactory;

        setLayout(new GridLayout(_field.getHeight(), _field.getWidth()));

        Dimension fieldDimension = new Dimension(CellWidget.CELL_SIZE*_field.getWidth(), CellWidget.CELL_SIZE*_field.getHeight());

        setPreferredSize(fieldDimension);
        setMinimumSize(fieldDimension);
        setMaximumSize(fieldDimension);

        for (Cell c: _field) {
            add( new CellWidget( c, this ) );
        }

        setFocusable(true);

        for (TickTimer timer : _timerFactory.getTickTimers()) {
            timer.addTickListener(this);
        }
    }

    @Override
    public void onTick() {
        repaint();
    }

    public void handleCellClick(CellWidget cellWidget) {
        Cell clickedCell = cellWidget.getCell();
        Tile tile = clickedCell.getUnit(Tile.class);

        if (tile != null) {
            if(tile.push()) {
                _timerFactory.tickTimers();
                repaint();
            }
        }
    }
}