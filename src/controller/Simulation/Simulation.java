package src.controller.Simulation;
import java.util.*;
import src.Model.Cell;
import src.Model.Grid;

public abstract class Simulation {
    
    private Grid newGrid;
    private Grid myGrid;
	private boolean useGridClone;
	
	public Simulation(Grid grid){
		isPaused = false;
		useGridClone = false;
		this.myGrid = grid;
	}
	
	public Grid getGrid(){
		return myGrid;
	}

	public void initialize(List<Integer> cellStates){
		int statesListIndex = 0;
		for(Cell cell : myGrid.getCells()){
			cell.setState(cellStates.get(statesListIndex++));
		}
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
		 for(int x=0; x<myGrid.getCells().size(); x++){
        	Cell newCell = updateCellState(newGrid.getCells().get(x));
        	newGrid.replaceCell(newCell);
        }
		return newGrid;
	}
	public abstract Cell updateCellState(Cell cell);
	public void createOrRemovePerStep(){
		//Not abstract because not every subclass needs this method
	}
	  public ArrayList<String> getParameters(){
	  }
	  public void setParameter(double aValue){
      }
       public void setParameters(ArrayList<Double> paramsList){
     }
	  public String returnTitle() {
		return "";	
	}
}
