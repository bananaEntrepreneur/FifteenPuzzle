package saboteur;

import field.GameField;
import timer.MyTimer;
import timer.TickTimer;

import java.awt.Dimension;
import java.util.Random;
import java.util.TimerTask;

public abstract class Saboteur {
    final MyTimer _saboteurTime;
    final GameField _field;
    final int _width;
    final int _height;
    final Random _random;
    final int _mineProbability;

    Saboteur(int tickInterval, GameField field, int width, int height, int mineProbability, Random random) {
        _saboteurTime = new TickTimer(10);
        this._field = field;
        this._width = width;
        this._height = height;
        this._random = random;
        this._mineProbability = mineProbability;
    }

    public void start() {
        Dimension _fieldSize = getFieldSize();

        _field.setSize(_fieldSize);

        equipTiles();

        startEquipMines();

        _saboteurTime.schedule(TimerTask.class, );
    }

    protected abstract void equipTiles();

    protected abstract void startEquipMines();

    protected abstract void periodicalEquipMines();

    protected abstract boolean areTilesInFinishConfiguration();

    protected abstract Dimension getFieldSize();

    public void deactivate() {}
}
