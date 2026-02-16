package field;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class GameField implements Iterable<Cell> {
    private int _height;
    private int _width;
    private final Map<CellPosition, Cell> _cells;

    public GameField() {
        _cells = new HashMap<>();
        _width = 0;
        _height = 0;
    }

    public GameField(int width, int height) {
        _cells = new HashMap<>();
        setSize(new Dimension(width, height));
    }

    @Override
    public Iterator<Cell> iterator() {
        return new GameFieldIterator();
    }

    private class GameFieldIterator implements Iterator<Cell> {
        private int row = 0;
        private int col = 0;

        @Override
        public boolean hasNext() {
            return row < _height && col < _width;
        }

        @Override
        public Cell next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Cell cell = _cells.get(new CellPosition(row, col));
            col++;
            if (col >= _width) {
                col = 0;
                row++;
            }
            return cell;
        }
    }

    void setSize(Dimension size) {
        this._width = (int) size.getWidth();
        this._height = (int) size.getHeight();

        _cells.clear();

        for (int row = 0; row < _height; row++) {
            for (int col = 0; col < _width; col++) {
                _cells.put(new CellPosition(row, col), new Cell(row, col));
            }
        }

        for (int row = 0; row < _height; row++) {
            for (int col = 0; col < _width; col++) {
                CellPosition pos = new CellPosition(row, col);
                Cell currentCell = _cells.get(pos);

                if (row > 0) {
                    currentCell.addNeighbor(Direction.north(), _cells.get(new CellPosition(row - 1, col)));
                }

                if (row < _height - 1) {
                    currentCell.addNeighbor(Direction.south(), _cells.get(new CellPosition(row + 1, col)));
                }

                if (col > 0) {
                    currentCell.addNeighbor(Direction.west(), _cells.get(new CellPosition(row, col - 1)));
                }

                if (col < _width - 1) {
                    currentCell.addNeighbor(Direction.east(), _cells.get(new CellPosition(row, col + 1)));
                }
            }
        }
    }

    public Cell getCell(int row, int col) {
        return _cells.get(new CellPosition(row, col));
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public void deactivate() {
        for (Cell cell : _cells.values()) {
            for (var unit : cell.getAllUnits()) {
                unit.deactivate();
            }
        }
    }
}
