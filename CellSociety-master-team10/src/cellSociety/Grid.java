package cellSociety;

import cell.Cell;
import cell.CellIterator;
/**
 * Created by davidyan on 1/31/16.
 */
public class Grid {
    private Cell[][] myGrid;
    private int gridWidth,gridHeight;
    public Grid(Cell baseCell, int width, int height){
        myGrid = new Cell[width][height];
        gridWidth = width;
        gridHeight = height;
        for(int i=0;i<gridWidth;i++){
            for(int j=0;j<gridHeight;j++){
                myGrid[i][j] = baseCell.clone();
                myGrid[i][j].setX(i);
                myGrid[i][j].setY(j);
            }
        }
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public Cell getCell(int x, int y) {
        return myGrid[x][y];
    }

    public CellIterator getCellIterator() {
        return new CellIterator(this);
    }



}
