package view;

import cell.Cell;
import listeners.CellStateListener;
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

    private final Cell _cell;
    private final GameFieldView _parent;

    public CellWidget(Cell cell, GameFieldView parent) {
        setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
        setBackground(BACKGROUND_COLOR);
        setOpaque(true);

        _cell = cell;
        _parent = parent;
        _cell.addListener(this);

        setToolTipText("");
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                _parent.handleCellClick(CellWidget.this);
                repaint();
            }
        });
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
            gr2d.setColor(FONT_COLOR);
            FontMetrics fm = g.getFontMetrics();
            int msgWidth = fm.stringWidth(msg);
            int msgHeight = fm.getHeight();
            gr2d.drawString(msg, (CELL_SIZE - msgWidth) / 2, CELL_SIZE / 2 + msgHeight / 4);
        }

        if (mine != null) {
            String msg = mine.toString();
            gr2d.setColor(Color.RED);
            FontMetrics fm = g.getFontMetrics();
            int msgWidth = fm.stringWidth(msg);
            gr2d.drawString(msg, (CELL_SIZE - msgWidth) / 2, CELL_SIZE - 10);
        }
    }

    @Override
    public String getToolTipText(MouseEvent e)  {
        Tile tile = _cell.getUnit(Tile.class);
        Mine mine = _cell.getUnit(Mine.class);
        
        StringBuilder tip = new StringBuilder();
        if (tile != null) {
            tip.append(tile.toString());
        }
        if (mine != null) {
            if (tip.length() > 0) {
                tip.append(", ");
            }
            tip.append(mine.toString());
        }
        
        return tip.length() > 0 ? tip.toString() : null;
    }

    @Override
    public void unitPlaced(Cell cell, Unit unit) {
        repaint();
    }

    @Override
    public void unitExtracted(Cell cell, Unit unit) {
        repaint();
    }
}