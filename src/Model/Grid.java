package src.Model;
import java.util.*;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
/**
 * Created by davidyan on 1/31/16.
 * Modified by adamtache on 2/12/16
 */
public class Grid {

	private ArrayList<Cell> myCells;
	private boolean outlined;
	private double width;
	private double height;
	private int numCols;
	private int numRows;
	private HashMap<Integer, ArrayList<Cell>> gridMap = new HashMap<Integer, ArrayList<Cell>>();

	public Grid(double width, double height){
		myCells = new ArrayList<Cell>();
		this.width = width;
		this.height = height;
	}

	public ArrayList<Cell> getCells(){
		return myCells;
	}

	public void addCell(Cell cell){
		myCells.add(cell);
	}

	public void replaceCell(Cell cell){
		for(int x=0; x<myCells.size(); x++){
			Cell curr = myCells.get(x);
			if(curr.getCenterX() == cell.getCenterX() && curr.getCenterY() == cell.getCenterY()){
				myCells.set(x, cell);
			}
		}
	}

	public Cell getCell(double x, double y){
		for(int i=0; i<myCells.size(); i++){
			Cell curr = myCells.get(i);
			if(curr.getCenterX() == x && curr.getCenterY() == y){
				return curr;
			}
		}
		return null;
	}
	/*
	public ArrayList<Cell> getNonDiagonalNeighbors(Cell cell){
		ArrayList<Cell> neighbors = cell.getAllNeighbors();
		Iterator<Cell> neighborIterator = neighbors.iterator();
		while(neighborIterator.hasNext()){
			Cell neighborcell = neighborIterator.next();
			if(cell.isDiagonalNeighborWith(neighborcell)){
				neighborIterator.remove();
			}
		}
		return neighbors;
	}*/

	public Grid getGridClone(){
		Grid copyGrid = new Grid(width, height);
		for(int x=0; x<getCells().size(); x++){
			copyGrid.addCell(getCells().get(x));
		}
		return copyGrid;
	}

	public void setOutline(boolean val){
		outlined = val;
	}

	public boolean isOutlined(){
		return outlined;
	}

	public HashMap<Integer, Integer> createMap(){
		HashMap<Integer, Integer> toRet = new HashMap<Integer, Integer>();
		for(int x=0; x<myCells.size(); x++){
			Integer id = myCells.get(x).getState();
			if(!toRet.containsKey(id)){
				toRet.put(id, 1);
			}else{
				Integer toUpdate = toRet.get(id);
				toUpdate++;
				toRet.put(id,toUpdate);
			}
		}
		return toRet;
	}

	public double getWidth(){
		return width;
	}

	public double getHeight(){
		return height;
	}

	public void draw(Canvas canvas, HashMap<Integer, Color> myColorMap){
		double myStrokeWidth = 10;
		GraphicsContext gc = canvas.getGraphicsContext2D();
		for(int i=0; i<myCells.size(); i++){
			Cell cell = myCells.get(i);
			gc.setFill(myColorMap.get(cell.getState()));
			double[] xPoints = cell.getXPoints();
			double[] yPoints = cell.getYPoints();
			gc.fillPolygon(xPoints, yPoints, cell.getNumSides());
			if(outlined){
				gc.setStroke(Color.BLACK);
				gc.setLineWidth(myStrokeWidth);
				gc.stroke();
			}
			else
				gc.setStroke(Color.TRANSPARENT);
		}
	}

	public void createGrid(ArrayList<Integer> cellList, String shapeType, double sideLen, String edgeType){
		int nextRow = 0;
		int nextCol = 0;
		Cell cell = null;
		if(shapeType.equals("Triangle")){
			cell = new TriangleCell(sideLen, this, edgeType);
		}
		if(shapeType.equals("Square"))
			cell = new SquareCell(sideLen, this, edgeType);
		if(shapeType.equals("Hexagon"))
			cell = new HexagonCell(sideLen, this, edgeType);
		gridMap.put(0, new ArrayList<Cell>());
		for(int x=0; x<cellList.size(); x++){
			int state = cellList.get(x);
			double centerX = 0;
			double centerY = 0;
			if(nextCol == 0){
				centerX = cell.XaddToNewRow(nextRow, nextCol);
				centerY = cell.YaddToNewRow(nextRow, nextCol);
			}
			else{
				centerX = cell.XaddToExistingRow(nextRow, nextCol);
				centerY = cell.YaddToExistingRow(nextRow, nextCol);
			}
			Cell newCell = null;
			if(shapeType.equals("Triangle")){
				newCell = new TriangleCell(centerX, centerY, state, sideLen, this, edgeType);
			}
			if(shapeType.equals("Square"))
				newCell = new SquareCell(centerX, centerY, state, sideLen, this, edgeType);
			if(shapeType.equals("Hexagon"))
				newCell = new HexagonCell(centerX, centerY, state, sideLen, this, edgeType);
			newCell.setXPoints(cell.getXPoints());
			newCell.setYPoints(cell.getYPoints());
			newCell.setRow(nextRow);
			newCell.setCol(nextCol);
			addCell(newCell);
			ArrayList<Cell> gridRow = gridMap.get(nextRow);
			gridRow.add(newCell);
			gridMap.put(nextRow, gridRow);
			if(nextCol == 0 || nextRow == 0)
				newCell.isAtEdge(true);
			if(( centerX + newCell.getWidth()) >= width){
				newCell.isAtEdge(true);
				nextCol = 0;
				nextRow++;
				ArrayList<Cell> nextGridRow = new ArrayList<Cell>();
				gridMap.put(nextRow, nextGridRow);
			}
			else
				nextCol++;
		}
		setNumCols(nextCol);
		setNumRows(nextRow);
		ArrayList<Cell> lastCol = getGridMap().get(nextCol);
		updateEdges(lastCol);
	}

	public void updateEdges(ArrayList<Cell> cells){
		for(int x=0; x<cells.size(); x++){
			cells.get(x).isAtEdge(true);
		}
	}

	public void setNumCols(int numCols){
		this.numCols = numCols;
	}

	public void setNumRows(int numRows){
		this.numRows = numRows;
	}

	public int getLastRow(){
		return numRows-1;
	}

	public int getLastCol(){
		return numCols-1;
	}

	public HashMap<Integer, ArrayList<Cell>> getGridMap(){
		return gridMap;
	}

}