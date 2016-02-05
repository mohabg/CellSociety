package cell;

import cellSociety.Grid;
import java.util.*;



public class CellIterator implements Iterable<Cell> {

    private Grid myGrid;
    private int myGridWidth;
    private int myGridHeight;

    public CellIterator(Grid grid) {
        myGrid = grid;
        myGridWidth = grid.getGridWidth();
        myGridHeight = grid.getGridHeight();
    }

    public Iterator<Cell> iterator() {
        Iterator<Cell> cellIt = new Iterator<Cell>() {

            private int myCurrentX = 0;
            private int myCurrentY = 0;
            private boolean started = false;

            public boolean hasNext() {
                return ((myCurrentX < myGridWidth - 1) || (myCurrentY < myGridHeight - 1) || !started);
            }
            public Cell next() {
                if (!started) {
                    started = true;
                    return myGrid.getCell(0, 0);
                }

                myCurrentX++;
                if (myCurrentX >= myGridWidth) {
                    myCurrentY++;
                    myCurrentX = 0;
                }
                Cell retCell = myGrid.getCell(myCurrentX, myCurrentY);
                return retCell;
            }

        };
        return cellIt;
    }
}
