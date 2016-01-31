/**
 * @Author: David Yan
 * @Date: Jan 31
 */
package cell;


public class Cell {
    protected int myX, myY;
    protected int currState;

    public Cell(int state){
        currState = state;
    }

    public Cell(int x, int y, int state){
        myX = x;
        myY = y;
        currState = state;
    }

    public Cell(Cell toUse){
        myX = toUse.getX();
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