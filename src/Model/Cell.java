package src.Model;

import java.util.ArrayList;

import javafx.scene.shape.Polygon;

public abstract class Cell {

	private int index;
	protected double myCenterX, myCenterY;
	protected int currState;
	private Actor myActor;
	private double sideLength;
	private int numSides;

	public Cell(int state){
		currState = state;
	}

	public Cell(double centerX, double centerY, int state, double sideLength, int numSides){
		myCenterX = centerX;
		myCenterY = centerY;
		currState = state;
		this.sideLength = sideLength;
		this.numSides = numSides;
	}

	public abstract Cell getLeftNeighbor();
	public abstract Cell getTopLeftNeighbor();
	public abstract Cell getTopNeighbor();
	public abstract Cell getTopRightNeighbor();
	public abstract Cell getRightNeighbor();
	public abstract Cell getBottomRightNeighbor();
	public abstract Cell getBottomNeighbor();
	public abstract Cell getBottomLeftNeighbor();
	public abstract boolean isDiagonalNeighborWith(Cell otherCell);
	public abstract double[] getXPoints();
	public abstract double[] getYPoints();
	public abstract Polygon getPoly();

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
}