package src.Model;
import java.util.*;
import src.Model.Grid;

public class Actor {
	private double xLocation;
	private double yLocation;
	private double energy;
	private double energyDepletionRate;
	private double timeSinceReproduced = 0;
	private Cell cell;
	private Cell lastCell = null;
	private double nextCellX = -1;
	private double nextCellY = -1;
	
	public Actor(double x, double y){
		xLocation = x;
		yLocation = y;
	}
	public Actor(double x, double y, double actorEnergy, double depletionRate){
		xLocation = x;
		yLocation = y;
		energy = actorEnergy;
		energyDepletionRate = depletionRate;
	}
	public void setCell(Cell cellToSet){
		cell = cellToSet;
	}
	public Cell getCell(){
		return cell;
	}
	public double getTimeSinceReproduced(){
		return timeSinceReproduced;
	}
	public void resetTimeSinceReproduced(){
		timeSinceReproduced = 0;
	}
	public double getDepletionRate(){
		return energyDepletionRate;
	}
	public void updateTimeSinceReproduced(double time){
		timeSinceReproduced += time;
	}

	public void moveAtRandom(List<Cell> emptyCells){
		Random rand = new Random();
		if(emptyCells.size() == 0){
			return;
		}
		int randomCell = rand.nextInt(emptyCells.size());
		Cell newLocation = emptyCells.get(randomCell);
		move(newLocation);
	}
	
	public void move(Cell cellToMoveTo){
		xLocation = cellToMoveTo.getCenterX();
		yLocation = cellToMoveTo.getCenterY();
		cell.removeActor(this);
		cellToMoveTo.setActor(this);
		lastCell = cell;
		double xDistance = cellToMoveTo.getCenterX() - cell.getCenterX(); 
		double yDistance = cell.getCenterY() - cellToMoveTo.getCenterY();
		nextCellX = cellToMoveTo.getCenterX() + xDistance;
		nextCellY = cellToMoveTo.getCenterY() + yDistance;
	}
	public Cell findCellGivenAngle(double angle, boolean isMovingUp, Grid myGrid){
		Cell cell = getCell();
		Cell cellInActorsPath = myGrid.getCell(getNextCellX(), getNextCellY());
		if(cellInActorsPath == null){
			cellInActorsPath = getCellMovedFrom();
		}
		double xDistance, yDistance;
		if(isMovingUp){
			yDistance = cellInActorsPath.getCenterY() - cell.getCenterY();
			xDistance = yDistance * Math.abs(Math.tan(Math.toRadians(angle)));
		}
		else{
			xDistance = cellInActorsPath.getCenterX() - cell.getCenterX();
		    yDistance = Math.abs(xDistance) * Math.tan(Math.toRadians(angle));
		}
		double newX = cell.getCenterX() + xDistance;
		double newY = cell.getCenterY() + yDistance;
		return myGrid.getCell(newX, newY);
	}
	
	public void setOrientation(Cell nextCellInPath){
		nextCellX = nextCellInPath.getCenterX();
		nextCellY = nextCellInPath.getCenterY();
	}
	public void setNextCellX(double newX){
		nextCellX = newX;
	}
	public void setNextCellY(double newY){
		nextCellY = newY;
	}
	public double getNextCellX(){
		return nextCellX;
	}
	public double getNextCellY(){
		return nextCellY;
	}
	public Cell getCellMovedFrom(){
		return lastCell;
	}
	public double getX(){
		return xLocation;
	}
	public void setX(int x){
		xLocation = x;
	}
	public double getY(){
		return yLocation;
	}
	public void setY(int y){
		yLocation = y;
	}
	public void depleteEnergy(){
		energy -= energyDepletionRate;
	}
	public boolean isDead(){
		return energy <= 0;
	}
	public void setEnergy(double newEnergy){
		energy = newEnergy;
	}
	public double getEnergy(){
		return energy;
	}
}
