package src.controller.Simulation;
import java.util.*;
import src.Model.Cell;
import src.Model.Grid;

public abstract class Simulation {
    
    private Grid newGrid;
    private Grid myGrid;
    private boolean isPaused;
    
    public Simulation(Grid grid){
        isPaused = false;
        this.myGrid = grid;
    }
    
    public Simulation(){
    	
    }
    
    public Grid getGrid(){
        return myGrid;
    }
    public void run(){
        while(!isPaused){
            step();
        }
    }
    public void pause(){
        isPaused = true;
    }
    
    public Grid step(){
        newGrid = myGrid.getGridClone();
        for(int x=0; x<myGrid.getCells().size(); x++){
        	Cell newCell = updateCellState(newGrid.getCells().get(x));
        	newGrid.replaceCell(newCell);
        }
        return newGrid;
    }
    public abstract Cell updateCellState(Cell cell);
    public abstract String returnTitle();
    public ArrayList<String> paramsList(){
    	ArrayList<String> blanky = new ArrayList<String>();
    	return blanky;
    }
    
    public double getParameter(){
    	return 0.0;
    }
    public void setParameter(double aValue){
    	
    }
    
    public abstract ArrayList<String> getParameters();
    public abstract void setParameters(ArrayList<Double> paramsList);
}
