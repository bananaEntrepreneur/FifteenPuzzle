package listeners;

import cell.Cell;
import units.Unit;

public interface CellStateListener {
    void unitPlaced(Cell cell, Unit unit);
    void unitExtracted(Cell cell, Unit unit);
}
