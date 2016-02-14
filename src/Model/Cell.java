package src.Model;

import java.util.*;
import javafx.scene.shape.Polygon;
import src.View.Grid;

public abstract class Cell {
    private int index;
	protected double myCenterX, myCenterY;
	protected int currState;
	private double sideLength;
	private int numSides;
    private PatchOfGround patch;
    private Map<Cell, List<Actor>> cellToActorMap;
    
    public Cell(int state){
		currState = state;
        patch = new PatchOfGround();
        cellToActorMap = new HashMap<Cell, List<Actor>>();
	}

	public Cell(double centerX, double centerY, int state, double sideLength, int numSides){
		myCenterX = centerX;
		myCenterY = centerY;
		currState = state;
		this.sideLength = sideLength;
		this.numSides = numSides;
        patch = new PatchOfGround();
        cellToActorMap = new HashMap<Cell, List<Actor>>();
	}
	
    public void setIndex(int indexToSet){
    	index = indexToSet;
    }
    public int getIndex(){
    	return index;
    }
    public PatchOfGround getGround(){
    	return patch;
    }

    public List<Actor> getActors(){
    	return cellToActorMap.get(this);
    }
    public void setActor(Actor actorToSet){
    	actorToSet.setCell(this);
    	if(cellToActorMap.containsKey(this)){
    		List<Actor> actors = cellToActorMap.get(this);
    		actors.add(actorToSet);
        	cellToActorMap.put(this, actors);
    	}
    	else{
    		List<Actor> actors = new ArrayList<Actor>();
    		actors.add(actorToSet);
    		cellToActorMap.put(this, actors);
    	}
    }
    public void removeActor(Actor actorToRemove){
    	if(cellToActorMap.containsKey(this)){
    	actorToRemove.setCell(null);
    	List<Actor> actors = cellToActorMap.get(this);
		actors.remove(actorToRemove);
    	cellToActorMap.put(this, actors);
    	}
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
}