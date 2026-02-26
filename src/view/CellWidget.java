package view;

import cell.Cell;
import listeners.CellStateListener;
import listeners.StateChangeListeners;
import units.Mine;
import units.Tile;
import units.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CellWidget extends JPanel implements CellStateListener {

    public static final int CELL_SIZE = 65;
    private static final Color BACKGROUND_COLOR = new Color(252, 252, 196);
    private static final Color RECT_COLOR = new Color(100, 100, 200);
    private static final Color FONT_COLOR = new Color(54, 190, 82);
    private static final Color DEACTIVATED_COLOR = new Color(150, 150, 150);
    private static final Color MINE_COLOR = new Color(255, 100, 100);
    private static final Color FROZEN_COLOR = new Color(100, 200, 255);

    private final Cell _cell;
    private final GameFieldView _parent;
    private final UnitStateListener _unitStateListener;

    public CellWidget(Cell cell, GameFieldView parent) {
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        setBackground(BACKGROUND_COLOR);
        setOpaque(true);

        _cell = cell;
        _parent = parent;
        _unitStateListener = new UnitStateListener();
        _cell.addListener(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                _parent.handleCellClick(CellWidget.this);
                repaint();
            }
        });
    }

    private class UnitStateListener implements StateChangeListeners {
        @Override
        public void stateChanged(java.util.EventObject event) {
            repaint();
        }
    }

    public Cell getCell() {
        return _cell;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D gr2d = (Graphics2D) g;
        gr2d.setStroke( new BasicStroke(2) );
        gr2d.setPaint( RECT_COLOR );

        gr2d.drawRect(0, 0, CELL_SIZE, CELL_SIZE);

        Tile tile = _cell.getUnit(Tile.class);
        Mine mine = _cell.getUnit(Mine.class);

        if (tile != null) {
            String msg = tile.toString();
            gr2d.setColor(tile.isActive() ? FONT_COLOR : DEACTIVATED_COLOR);
            FontMetrics fm = g.getFontMetrics();
            int msgWidth = fm.stringWidth(msg);
            int msgHeight = fm.getHeight();
            gr2d.drawString(msg, (CELL_SIZE - msgWidth) / 2, CELL_SIZE / 2 + msgHeight / 4);
        }

        if (mine != null) {
            drawMine(gr2d, mine);
        }
    }

    private void drawMine(Graphics2D gr2d, Mine mine) {
        gr2d.setColor(MINE_COLOR);
        FontMetrics fm = gr2d.getFontMetrics();

        if (mine.isActive()) {
            String m = mine.toString();
            int mWidth = fm.stringWidth(m);
            gr2d.drawString(m, (CELL_SIZE - mWidth) / 2, CELL_SIZE - 10);
        }
    }

    @Override
    public void unitPlaced(Cell cell, Unit unit) {
        if (unit != null) {
            unit.addListener(_unitStateListener);
        }
        repaint();
    }

    @Override
    public void unitExtracted(Cell cell, Unit unit) {
        if (unit != null) {
            unit.removeListener(_unitStateListener);
        }
        repaint();
    }
}