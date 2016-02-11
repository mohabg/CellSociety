package src.View;
import src.Model.Cell;
import src.controller.CellIterator;

import java.util.*;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * Created by davidyan on 1/31/16.
 */
public class Grid {
	
    private Cell[][] myGrid;
    private int gridWidth,gridHeight;
    private ArrayList<Cell> neighbors;
    
    public Grid(int width, int height){
        myGrid = new Cell[width][height];
        neighbors = new ArrayList<Cell>();
        gridWidth = width;
        gridHeight = height;
    }
    
	public ArrayList<Cell> getAllNeighbors(Cell cell) {
		if (this.neighbors.size() != 0) {
			return this.neighbors;
		}
		ArrayList<Cell> cellNeighbors = new ArrayList<Cell>();
		int[] directions = { -1, 0, 1 };
		
		for (int i = 0; i < directions.length; i++) {
			for (int j = 0; j < directions.length; j++) {
				int xOffset = directions[i];
				int yOffset = directions[j];
				int neighborX = cell.getX() + xOffset;
				int neighborY = cell.getY() + yOffset;

				if (xOffset == 0 && yOffset == 0) {
					// Same cell
					continue;
				}
				if (neighborX < 0 || neighborY < 0 || neighborX >= gridWidth || neighborY >= gridHeight) {
					// Out of Bounds
					continue;
				}
				cellNeighbors.add(this.getCell(neighborX, neighborY));
			}
		}
		return cellNeighbors;
	}
	
	public ArrayList<Cell> getNonDiagonalNeighbors(Cell cell){
		ArrayList<Cell> neighbors = getAllNeighbors(cell);
		Iterator<Cell> neighborIterator = neighbors.iterator();
		while(neighborIterator.hasNext()){
			Cell neighborcell = neighborIterator.next();
			if(cell.isDiagonalNeighborWith(neighborcell)){
				neighborIterator.remove();
			}
		}
		return neighbors;
	}
	
	public Grid getGridClone(){
		Grid copyGrid = new Grid(gridWidth, gridHeight);
		Cell[][] copy = copyGrid.myGrid;
		for(int i=0;i<gridWidth;i++){
            for(int j=0;j<gridHeight;j++){
                copy[i][j] = new Cell(0, 0, 0);
                copy[i][j].setX(i);
                copy[i][j].setY(j);
            }
        }
		return copyGrid;
	}
	
    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }
    
    public void setCell(Cell cell){
    	myGrid[cell.getX()][cell.getY()] = cell;
    }
    public Cell getCell(int x, int y) {
        return myGrid[x][y];
    }

    public CellIterator getCellIterator() {
        return new CellIterator(this);
    }
    
    public void draw(Canvas canvas, HashMap<Integer, Color> myColorMap) {

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
    
    public String toString(){
    	String str = "";
    	for(int x=0; x<gridWidth; x++){
    		for(int y=0; y<gridHeight; y++){
    			System.out.println("("+x+", "+y+") State: "+getCell(x,y).getState());
    		}
    		str+="\n";
    	}
    	return str;
    }

}