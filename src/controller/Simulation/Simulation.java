package src.controller.Simulation;
import java.util.*;

import src.Model.Cell;
import src.View.Grid;
import src.controller.CellIterator;

public abstract class Simulation {
	
	private Grid myGrid;
	private boolean isPaused;
	private boolean useGridClone;
	
	public Simulation(Grid grid){
		isPaused = false;
		useGridClone = false;
		this.myGrid = grid;
	}
	
	public Grid getGrid(){
		return myGrid;
	}
	//Initializes cells row by row
	public void initialize(ArrayList<Integer> cellStates){
		int statesListIndex = 0;
		CellIterator cellIt = myGrid.getCellIterator();
		for(Cell cell : cellIt){
			cell.setState(cellStates.get(statesListIndex++));
		}
	}
	
	public void run(){
		while(!isPaused){
			step();
		}
	}
	public void pause(){
		isPaused = true;
	}
	public void shouldUseGridClone(){
		useGridClone = true;
	}
	public void shouldNotUseGridClone(){
		useGridClone = false;
	}
	public Grid step(){
		Grid newGrid;
		if(useGridClone){
		newGrid = myGrid.getGridClone();
		}
		else{
			newGrid = myGrid;
		}
		CellIterator cellIt = myGrid.getCellIterator();
		for(Cell cell: cellIt){
			newGrid.setCell(updateCellState(cell));
		}
		return newGrid;
	}
	public abstract Cell updateCellState(Cell cell);

	public String returnTitle() {
		return "";
		// TODO Auto-generated method stub
		
	}
}
