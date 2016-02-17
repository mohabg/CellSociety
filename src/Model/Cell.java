package src.Model;

import java.util.*;
import src.Model.Grid;

// This entire file is part of my code masterpiece.
// I chose to include this class as part of my masterpiece (along with HexagonCell subclass) to demonstrate what I learned during this project on abstraction and the relationship between subclasses and superclasses. This includes practicing what methods should be created as abstract methods in the superclass and better ways to implement subclasses using code written in the superclass. I significantly refactored these classes this week, and this commit is an extension to the prior refactoring.  
// Other refactoring commits: 
// https://github.com/duke-compsci308-spring2016/cellsociety_team10/commit/bcf58bc97157bef7c72ef51ea1977e2d689ac965
// https://github.com/duke-compsci308-spring2016/cellsociety_team10/commit/d24be9ce73ef3d27e37823836fa3a26d07a268ac

public abstract class Cell {
	private double myCenterX, myCenterY;
	private int currState;
	private double sideLength;
	private int numSides;
	private Grid myGrid;
	private double[] xPoints;
	private double[] yPoints;
	private boolean isAtEdge = false;
	private int myRow;
	private int myCol;
	private String edgeType;
	private PatchOfGround patch;
	private Map<Cell, List<Actor>> cellToActorMap;
	private double height;

	public Cell(int state){
		currState = state;
		patch = new PatchOfGround();
		cellToActorMap = new HashMap<Cell, List<Actor>>();
	}

	public Cell(double centerX, double centerY, int state, double sideLength, int numSides, Grid myGrid, String edgeType){
		myCenterX = centerX;
		myCenterY = centerY;
		currState = state;
		xPoints = new double[numSides];
		yPoints = new double[numSides];
		this.sideLength = sideLength;
		this.numSides = numSides;
		this.myGrid = myGrid;
		this.edgeType = edgeType;
		patch = new PatchOfGround();
		cellToActorMap = new HashMap<Cell, List<Actor>>();
	}
	
	public abstract double XaddToNewRow(int row, int col);
	public abstract double YaddToNewRow(int row, int col);
	public abstract double XaddToExistingRow(int row, int col);
	public abstract double YaddToExistingRow(int row, int col);
	public abstract double getWidth();

	public PatchOfGround getGround(){
		return patch;
	}

	public List<Actor> getActors(){
		if(cellToActorMap.containsKey(this)){
			return cellToActorMap.get(this);
		}
		else return new ArrayList<Actor>();
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
	public Grid getGrid(){
		return myGrid;
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
	public void isAtEdge(boolean edge){
		isAtEdge = edge;
	}
	public double getAverageValue(double[] vals){
		double sum = 0;
		for(int x=0; x<vals.length; x++){
			sum += vals[x];
		}
		return sum/vals.length;
	}
	public void setRow(int row){
		myRow = row;
	}
	public void setCol(int col){
		myCol = col;
	}
	public double getHeight(){
		return height;
	}
	public void setHeight(double newHeight){
		height = newHeight;
	}
	private Cell getLeftNeighbor(){
		Cell cell = myGrid.getCell(getCenterX() - getSideLength(), getCenterY());
		if(isAtEdge && edgeType.equals("Toroidal"))
			cell = myGrid.getGridMap().get(myRow).get(myGrid.getLastCol());
		return cell;
	}
	private Cell getTopLeftNeighbor(){
		return myGrid.getCell(getCenterX() - getSideLength(), getCenterY() - getSideLength());
	}
	private Cell getTopNeighbor(){
		Cell cell = myGrid.getCell(getCenterX(), getCenterY() - getSideLength());
		if(isAtEdge && edgeType.equals("Toroidal"))
			cell = myGrid.getGridMap().get(myGrid.getLastRow()).get(myCol);
		if(numSides != 6)
			return cell;
		return null;
	}
	private Cell getTopRightNeighbor(){
		return myGrid.getCell(getCenterX() + getSideLength(), getCenterY() - getSideLength());
	}
	private Cell getRightNeighbor(){
		Cell cell = myGrid.getCell(getCenterX() + getSideLength(), getCenterY());
		if(isAtEdge && edgeType.equals("Toroidal"))
			cell = myGrid.getGridMap().get(myRow).get(0);
		return cell;
	}
	private Cell getBottomRightNeighbor(){
		return myGrid.getCell(getCenterX() + getSideLength(), getCenterY() + getSideLength());
	}
	private Cell getBottomNeighbor(){
		Cell cell = myGrid.getCell(getCenterX(), getCenterY() + getSideLength());
		if(isAtEdge && edgeType.equals("Toroidal"))
			cell = myGrid.getGridMap().get(0).get(myCol);
		if(numSides != 6)
			return cell;
		return null;
	}
	private Cell getBottomLeftNeighbor(){
		return myGrid.getCell(getCenterX() - getSideLength(), getCenterY() + getSideLength());
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
}