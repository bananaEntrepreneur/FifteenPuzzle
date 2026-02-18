package saboteur;

import field.GameField;
import field.Cell;
import field.CellPosition;
import units.Tile;
import units.Mine;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimpleSaboteur extends Saboteur {

    public SimpleSaboteur(GameField field, int width, int height) {
        super(field, width, height, 10, new Random());
    }

    @Override
    protected void equipTiles() {
        List<TileConfiguration> tiles = generateSolvableConfiguration();
        placeTiles(tiles);
    }

    private List<TileConfiguration> generateSolvableConfiguration() {
        List<Integer> values = createShuffledValues();
        List<TileConfiguration> configs = new ArrayList<>();

        int index = 0;
        for (int row = 0; row < _height; row++) {
            for (int col = 0; col < _width; col++) {
                int value = values.get(index++);
                if (value != 0) {
                    configs.add(new TileConfiguration(row, col, value));
                }
            }
        }
        return configs;
    }

    private List<Integer> createShuffledValues() {
        List<Integer> values = new ArrayList<>();
        for (int i = 1; i < _width * _height; i++) {
            values.add(i);
        }
        values.add(0);
        Collections.shuffle(values, _random);
        return values;
    }

    private void placeTiles(List<TileConfiguration> configurations) {
        for (TileConfiguration config : configurations) {
            Cell cell = _field.getCell(config.row, config.col);
            if (cell != null) {
                cell.putUnit(new Tile(config.value));
            }
        }
    }

    @Override
    protected void startEquipMines() {
        int mineCount = (_width * _height) / _mineProbability;
        for (int i = 0; i < mineCount; i++) {
            placeRandomMine();
        }
    }

    private void placeRandomMine() {
        CellPosition pos = randomPosition();
        Cell cell = _field.getCell(pos.getRow(), pos.getColumn());
        if (cell != null && cell.isEmpty()) {
            cell.putUnit(createMine());
        } else {
            placeRandomMine();
        }
    }

    private CellPosition randomPosition() {
        int row = _random.nextInt(_height);
        int col = _random.nextInt(_width);
        return new CellPosition(row, col);
    }

    private Mine createMine() {
        int delay = 10 + _random.nextInt(20);
        return new Mine(delay);
    }

    @Override
    protected void periodicalEquipMines() {
    }

    @Override
    protected boolean areTilesInFinishConfiguration() {
        for (int row = 0; row < _height; row++) {
            for (int col = 0; col < _width; col++) {
                if (!isCellInCorrectPosition(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isCellInCorrectPosition(int row, int col) {
        Cell cell = _field.getCell(row, col);
        if (cell == null) {
            return false;
        }

        Tile tile = cell.getUnit(Tile.class);
        int expectedValue = row * _width + col + 1;
        boolean isLastCell = (row * _width + col + 1) == (_width * _height);

        if (isLastCell) {
            return tile == null;
        }
        return tile != null && tile.getNumber() == expectedValue;
    }

    @Override
    protected Dimension getFieldSize() {
        return new Dimension(_width, _height);
    }

    private static class TileConfiguration {
        final int row;
        final int col;
        final int value;

        TileConfiguration(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }
    }
}
