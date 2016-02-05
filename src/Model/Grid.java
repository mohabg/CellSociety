package cellSociety;

import cell.Cell;
import cell.CellIterator;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;

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

    public void draw(Canvas canvas) {
        HashMap<Integer, Color> myColorMap = new HashMap<>();
        myColorMap.put(0,Color.LIGHTBLUE);
        myColorMap.put(1,Color.ORANGE);
        myColorMap.put(2,Color.PURPLE);

        double myStrokeWidth = 0.05;
        double width = canvas.getWidth();
        double height = canvas.getHeight();

        double cellWidth = width / (gridWidth + myStrokeWidth * (gridWidth - 1));
        double cellHeight = height / (gridHeight + myStrokeWidth * (gridHeight - 1));

        double x, y;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (Cell cell : getCellIterator()) {
            gc.setFill(myColorMap.get(cell.getState()));
            x = cellWidth * myStrokeWidth + cellWidth * cell.getX() + (cell.getX() - 1) * cellWidth * myStrokeWidth;
            y = cellWidth * myStrokeWidth + cellHeight * cell.getY() + (cell.getY() - 1) * cellHeight * myStrokeWidth;
            gc.fillRect(x, y, cellWidth, cellHeight);
        }
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(myStrokeWidth * cellWidth * 2.0);
        gc.strokeRect(0.0, 0.0, width, height);

    }


}
