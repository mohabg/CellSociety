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
	double width;
	double height;

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
			gc.fillPolygon(cell.getXPoints(), cell.getYPoints(), cell.getNumSides());
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
			double height = new TriangleCell(sideLen).getHeight();
			double centroidX = 1/2*sideLen;
			double centroidY = (1/3)*(height + height);// centroid formula
			double centerX = sideLen/2 + centroidX; 
			double centerY = sideLen/2 + centroidY;
			int row = 0;
			for(int x=0; x<cellList.size(); x++){
				int state = cellList.get(x);
				int type = x%2; // type 0 = vertical, type 1 = flipped vertically
				if(row % 2 == 1){
					if(type == 0)
						type = 1;
					else if(type == 1)
						type = 0;
				}
				Cell newCell = new TriangleCell(centerX, centerY, state, sideLen, this, type);
				addCell(newCell);
				centerX += sideLen/2;
				if(centerX >= (getWidth() - sideLen)){
					centerX = sideLen/2 + centroidX;
					centerY += height;
					row++;
				}
			}
		}
		else if(shapeType.equals("Hexagon")){

		}
	}

}