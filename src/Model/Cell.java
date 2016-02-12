package src.Model;

public class Cell {
    
    private int myX, myY;
    private int currState;
    private Actor myActor;
    
    public Cell(int state){
        currState = state;
    }
    
    public Cell(int x, int y, int state){
        myX = x;
        myY = y;
        currState = state;
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