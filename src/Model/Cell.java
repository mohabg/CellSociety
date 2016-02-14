package src.Model;

import java.util.*;

import src.View.Grid;

public class Cell {
	private int index;
    private int myX, myY;
    private int currState;
    private Cell[] neighbors;
    private Grid myGrid;
    private PatchOfGround patch;
    private Map<Cell, List<Actor>> cellToActorMap;
    
    public Cell(int state){
        currState = state;
        patch = new PatchOfGround();
        neighbors = new Cell[8];
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

    public Cell(int x, int y, int state){
        myX = x;
        myY = y;
        neighbors = new Cell[8];
        currState = state;
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
    
    public Cell(Cell toUse){
        myX = toUse.getX();
        myY = toUse.getY();
        currState = toUse.getState();
    }
    public Boolean isState(int state){
    	return currState == state;
    }
    public Boolean isDiagonalNeighborWith(Cell otherCell){
    	//X locations and Y locations both differ by 1
    	return (Math.abs(myX - otherCell.getX()) == 1) && (Math.abs(myY - otherCell.getY()) == 1);
    }
    
    public Cell clone(){
        return new Cell(myX,myY,currState);
    }

    public void setX(int x){
        myX = x;
    }
    public void setY(int y){
        myY = y;
    }
    public void setState(int state){
        currState = state;
    }
    public int getX(){
        return myX;
    }
    public int getY(){
        return myY;
    }
    public int getState(){
        return currState;
    }
}