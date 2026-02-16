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
    private final GameField field;
    private final int width;
    private final int height;
    private final Random random;
    private final int mineProbability;

    public SimpleSaboteur(GameField field, int width, int height) {
        this(field, width, height, 10, new Random());
    }

    SimpleSaboteur(GameField field, int width, int height, int mineProbability, Random random) {
        this.field = field;
        this.width = width;
        this.height = height;
        this.random = random;
        this.mineProbability = mineProbability;
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
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
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
        for (int i = 1; i < width * height; i++) {
            values.add(i);
        }
        values.add(0);
        Collections.shuffle(values, random);
        return values;
    }

    private void placeTiles(List<TileConfiguration> configurations) {
        for (TileConfiguration config : configurations) {
            Cell cell = field.getCell(config.row, config.col);
            if (cell != null) {
                cell.putUnit(new Tile(config.value));
            }
        }
    }

    @Override
    protected void startEquipMines() {
        int mineCount = (width * height) / mineProbability;
        for (int i = 0; i < mineCount; i++) {
            placeRandomMine();
        }
    }

    private void placeRandomMine() {
        CellPosition pos = randomPosition();
        Cell cell = field.getCell(pos.getRow(), pos.getColumn());
        if (cell != null && cell.isEmpty()) {
            cell.putUnit(createMine());
        } else {
            placeRandomMine();
        }
    }

    private CellPosition randomPosition() {
        int row = random.nextInt(height);
        int col = random.nextInt(width);
        return new CellPosition(row, col);
    }

    private Mine createMine() {
        int delay = 10 + random.nextInt(20);
        return new Mine(delay);
    }

    @Override
    protected void periodicalEquipMines() {
    }

    @Override
    protected boolean areTilesInFinishConfiguration() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!isCellInCorrectPosition(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isCellInCorrectPosition(int row, int col) {
        Cell cell = field.getCell(row, col);
        if (cell == null) {
            return false;
        }

        Tile tile = cell.getUnit(Tile.class);
        int expectedValue = row * width + col + 1;
        boolean isLastCell = (row * width + col + 1) == (width * height);

        if (isLastCell) {
            return tile == null;
        }
        return tile != null && tile.getNumber() == expectedValue;
    }

    @Override
    protected Dimension getFieldSize() {
        return new Dimension(width, height);
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
