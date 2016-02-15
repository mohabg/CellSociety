package src.Model;
import java.util.ArrayList;

public abstract class Cell {

	protected double myCenterX, myCenterY;
	protected int currState;
	private Actor myActor;
	private double sideLength;
	private int numSides;
	private Grid myGrid;
	private double[] xPoints;
	private double[] yPoints;

	public Cell(int state){
		currState = state;
	}

	public Cell(double centerX, double centerY, int state, double sideLength, int numSides, Grid myGrid){
		myCenterX = centerX;
		myCenterY = centerY;
		currState = state;
		xPoints = new double[numSides];
		yPoints = new double[numSides];
		this.sideLength = sideLength;
		this.numSides = numSides;
		this.myGrid = myGrid;
	}

	public Cell getLeftNeighbor(){
		return myGrid.getCell(getCenterX() - getSideLength(), getCenterY());
	}
	public Cell getTopLeftNeighbor(){
		return myGrid.getCell(getCenterX() - getSideLength(), getCenterY() - getSideLength());
	}
	public Cell getTopNeighbor(){
		return myGrid.getCell(getCenterX(), getCenterY() - getSideLength());
	}
	public Cell getTopRightNeighbor(){
		return myGrid.getCell(getCenterX() + getSideLength(), getCenterY() - getSideLength());
	}
	public Cell getRightNeighbor(){
		return myGrid.getCell(getCenterX() + getSideLength(), getCenterY());
	}
	public Cell getBottomRightNeighbor(){
		return myGrid.getCell(getCenterX() + getSideLength(), getCenterY() + getSideLength());
	}
	public Cell getBottomNeighbor(){
		return myGrid.getCell(getCenterX(), getCenterY() + getSideLength());
	}
	public Cell getBottomLeftNeighbor(){
		return myGrid.getCell(getCenterX() - getSideLength(), getCenterY() + getSideLength());
	}
	public boolean isDiagonalNeighborWith(Cell otherCell){
		//X locations and Y locations both differ by 1
		// to implement
		return (Math.abs(myCenterX - otherCell.getCenterX()) == getSideLength()) && (Math.abs(myCenterY - otherCell.getCenterY()) == getSideLength());
	}

	public ArrayList<Cell> getAllNeighbors(){
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		Cell left = getLeftNeighbor(); 
		if(left!=null) neighbors.add(left);
		Cell topLeft = getTopLeftNeighbor(); 
		if(topLeft!=null) neighbors.add(topLeft);
		Cell topRight = getTopRightNeighbor(); 
		if(topRight!=null) neighbors.add(topRight);
		Cell right = getRightNeighbor(); 
		if(right!=null) neighbors.add(right);
		Cell botLeft = getBottomLeftNeighbor(); 
		if(botLeft!=null) neighbors.add(botLeft);
		Cell botRight = getBottomRightNeighbor(); 
		if(botRight!=null) neighbors.add(botRight);
		Cell bottom = getBottomNeighbor(); 
		if(bottom!=null) neighbors.add(bottom);
		Cell top = getTopNeighbor(); 
		if(top!=null) neighbors.add(top);
		return neighbors;
	}

	public ArrayList<Cell> getNonDiagonalNeighbors(){
		ArrayList<Cell> nonDiag = new ArrayList<Cell>();
		if(getLeftNeighbor() != null)
			nonDiag.add(getLeftNeighbor());
		if(getRightNeighbor() != null)
			nonDiag.add(getRightNeighbor());
		if(getTopNeighbor() != null)
			nonDiag.add(getTopNeighbor());
		if(getBottomNeighbor() != null)
			nonDiag.add(getBottomNeighbor());
		return nonDiag;
	}

	public double[] getXPoints(){
		return xPoints;
	}
	public double[] getYPoints(){
		return yPoints;
	}
	public void setXPoints(double[] newXPoints){
		for(int x=0; x<newXPoints.length; x++){
			xPoints[x] = newXPoints[x];
		}
	}
	public void setYPoints(double[] newYPoints){
		for(int y=0; y<newYPoints.length; y++){
			yPoints[y] = newYPoints[y];
		}
	}

	public Actor getActor(){
		return myActor;
	}
	public void setActor(Actor actorToSet){
		myActor = actorToSet;
	}
	public void removeActor(){
		myActor = null;
	}
	public Boolean isState(int state){
		return currState == state;
	}
	public void setState(int state){
		currState = state;
	}
	public double getCenterX(){
		return myCenterX;
	}
	public double getCenterY(){
		return myCenterY;
	}
	public int getState(){
		return currState;
	}
	public double getSideLength(){
		return sideLength;
	}
	public int getNumSides(){
		return numSides;
	}

	public abstract double getHeight();
	public abstract double XaddToNewRow(int row, int col);
	public abstract double YaddToNewRow(int row, int col);
	public abstract double XaddToExistingRow(int row, int col);
	public abstract double YaddToExistingRow(int row, int col);
}
