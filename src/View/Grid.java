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
    
    public Grid(Cell baseCell, int width, int height){
        myGrid = new Cell[width][height];
        neighbors = new ArrayList<Cell>();
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
		
		for(Cell neighborCell: neighbors){
			if(cell.isDiagonalNeighborWith(neighborCell)){
				neighbors.remove(neighborCell);
			}
		}
		return neighbors;
	}
	
	public Grid getGridClone(){
		Grid copy = new Grid(new Cell(0, 0, 0), gridWidth, gridHeight);
		CellIterator cellIt = this.getCellIterator();
		while(cellIt.iterator().hasNext()){
			copy.setCell(cellIt.iterator().next().clone());
		}
		return copy;
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