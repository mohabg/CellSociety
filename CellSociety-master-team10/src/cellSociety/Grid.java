package cellSociety;
import cell.Cell;
/**
 * Created by davidyan on 1/31/16.
 */
public abstract class Grid {
    protected Cell[][] myGrid;
    protected int gridWidth,gridHeight;

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


}
