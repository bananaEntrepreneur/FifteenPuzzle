package saboteurs;

import cell.Cell;
import cell.CellPosition;
import game.GameField;
import game.Saboteur;
import listeners.MineStateListener;
import listeners.TileStateListener;
import timer.TimerFactory;
import units.FreezeMine;
import units.Mine;
import units.Tile;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleSaboteur extends Saboteur {

    public SimpleSaboteur(GameField field, int width, int height, TimerFactory timerFactory) {
        super(field, width, height, 10, timerFactory);
    }

    @Override
    protected void equipTiles() {
        List<TileConfiguration> tiles = generateSolvableConfiguration();
        placeTiles(tiles);
    }

    private List<TileConfiguration> generateSolvableConfiguration() {
        List<Integer> values = createShuffledValues();
        List<TileConfiguration> configurations = new ArrayList<>();

        int index = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int value = values.get(index++);
                if (value != 0) {
                    configurations.add(new TileConfiguration(row, col, value));
                }
            }
        }
        return configurations;
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
                Tile tile = new Tile(config.value);
                tile.addListener(new TileStateListener(this));
                cell.putUnit(tile);
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
        CellPosition position = randomPosition();
        Cell cell = field.getCell(position.getRow(), position.getColumn());

        if (isValidMinePosition(cell)) {
            Mine mine = createMine();
            mines.add(mine);
            cell.putUnit(mine);
            mine.addListener(new MineStateListener(timerFactory));
            mine.startTimer(timerFactory);
        } else {
            placeRandomMine();
        }
    }

    private boolean isValidMinePosition(Cell cell) {
        return cell != null && !hasMine(cell) && cell.getUnit(Tile.class) != null;
    }

    private boolean hasMine(Cell cell) {
        return cell.getUnit(Mine.class) != null;
    }

    private CellPosition randomPosition() {
        int row = random.nextInt(height);
        int col = random.nextInt(width);
        return new CellPosition(row, col);
    }

    private Mine createMine() {
        int delay = 10 + random.nextInt(20);
        return new FreezeMine(delay, 10);
    }

    @Override
    protected void periodicalEquipMines() {
        if (random.nextInt(100) < mineProbability) {
            placeRandomMine();
        }
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
