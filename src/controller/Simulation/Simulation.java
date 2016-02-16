package src.controller.Simulation;
import java.util.*;
import src.Model.Cell;
import src.Model.Grid;

public abstract class Simulation {
    
    private Grid myGrid;
	private boolean useGridClone;
	private double defaultParamValue = -1/999;
	
    public Simulation(Grid grid){
        this.myGrid = grid;
        useGridClone = false;
    }
    
    public Simulation(){
    	
    }
    
    public Grid getGrid(){
        return myGrid;
    }

	public void shouldUseGridClone(){
		useGridClone = true;
	}
	public void shouldNotUseGridClone(){
		useGridClone = false;
	}
    public Grid step(){
    	createOrRemovePerStep();
    	Grid newGrid;
		if(useGridClone){
		newGrid = myGrid.getGridClone();
		}
		else{
			newGrid = myGrid;
		}
		for(Cell cell: myGrid.getCells()){
			newGrid.replaceCell(updateCellState(cell));
		}
        return newGrid;
    }

	public abstract void createOrRemovePerStep();
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
    
    public double getDefaultVal(){
    	return defaultParamValue;
    }
    
    public abstract ArrayList<String> getParameters();
    public abstract void setParameters(ArrayList<Double> paramsList);
}
