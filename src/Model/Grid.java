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
	private HashMap<Integer, ArrayList<Cell>> gridMap = new HashMap<Integer, ArrayList<Cell>>();
	private int numCellsPerRow;
	private int intNumRows;

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
	}

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
		double myStrokeWidth = 0.2;
		GraphicsContext gc = canvas.getGraphicsContext2D();
		for(int i=0; i<myCells.size(); i++){
			Cell cell = myCells.get(i);
			gc.setFill(myColorMap.get(cell.getState()));
			gc.setLineWidth(myStrokeWidth);
			double[] xPoints = cell.getXPoints();
			double[] yPoints = cell.getYPoints();
			gc.fillPolygon(xPoints, yPoints, cell.getNumSides());
		}
		if(outlined)
			gc.setStroke(Color.DARKGRAY);
		else
			gc.setStroke(Color.TRANSPARENT);
	}

	public void createGrid(ArrayList<Integer> cellList, String shapeType, double sideLen){
		if(shapeType.equals("Square")){
			double centerX = sideLen/2;
			double centerY = sideLen/2;
			for(int x=0; x<cellList.size(); x++){
				int state = cellList.get(x);
				Cell newCell = new SquareCell(centerX, centerY, state, sideLen, this);
				addCell(newCell);
				centerX += sideLen;
				if(centerX >= (getWidth() - sideLen)){
					centerX = sideLen/2;
					centerY += sideLen;
				}
			}
		}
		else if(shapeType.equals("Triangle")){
			System.out.println("Making triangle grid.");
			int nextRow = 0;
			int nextCol = 0;
			TriangleCell tri = new TriangleCell(sideLen, this);
			gridMap.put(0, new ArrayList<Cell>());
			for(int x=0; x<cellList.size(); x++){
				int state = cellList.get(x);
				double centerX = 0;
				double centerY = 0;
				System.out.println("At cell # "+x);
				if(nextCol == 0){
					centerX = tri.XaddToNewRow(nextRow, nextCol);
					centerY = tri.YaddToNewRow(nextRow, nextCol);
				}
				else{
					centerX = tri.XaddToExistingRow(nextRow, nextCol);
					centerY = tri.YaddToExistingRow(nextRow, nextCol);
				}
				System.out.println("("+centerX+", "+centerY+")");
				Cell newCell = new TriangleCell(centerX, centerY, state, sideLen, this);
				newCell.setXPoints(tri.getXPoints());
				newCell.setYPoints(tri.getYPoints());
				addCell(newCell);
				ArrayList<Cell> gridRow = gridMap.get(nextRow);
				gridRow.add(newCell);
				System.out.println("GRID3 "+newCell.getXPoints()[1]+" "+getGridMap().get(0).get(0).getXPoints()[1]);
				gridMap.put(nextRow, gridRow);
				if(( centerX + sideLen) >= width){
					nextCol = 0;
					nextRow++;
					ArrayList<Cell> nextGridRow = new ArrayList<Cell>();
					gridMap.put(nextRow, nextGridRow);
				}
				else
					nextCol++;
			}
		}
		else if(shapeType.equals("Hexagon")){

		}
		System.out.println("GRID2 "+getGridMap().get(0).get(0).getXPoints()[1]);
	}

	public HashMap<Integer, ArrayList<Cell>> getGridMap(){
		return gridMap;
	}

}