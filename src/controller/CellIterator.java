import java.util.*;


public class CellIterator implements Iterable<Cell> {

    private Grid myGrid;
    private int myWidth;
    private int myHeight;


    public CellIterator(Grid grid) {
        myGrid = grid;
        myWidth = grid.getGridWidth();
        myHeight = grid.getGridHeight();
    }

    public Iterator<Cell> iterator() {
        Iterator<Cell> cellIt = new Iterator<Cell>() {

            private int myCurrentX = 0;
            private int myCurrentY = 0;
            private boolean started = false;

            public boolean hasNext() {
                return (!started || (myCurrentX < myWidth - 1) || (myCurrentY < myHeight - 1));
            }
            public Cell next() {
                if (!started) {
                    started = true;
                    return myGrid.getCell(0, 0);
                }

                myCurrentX++;
                if (myCurrentX >= myWidth) {
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