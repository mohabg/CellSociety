package src.controller.Simulation;
import java.util.*;

import src.Model.Cell;
import src.View.Grid;

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

	public void initialize(List<Integer> cellStates){
		int statesListIndex = 0;
		for(Cell cell : myGrid.getCells()){
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
	public abstract Cell updateCellState(Cell cell);
	public void createOrRemovePerStep(){
		//Not abstract because not every subclass needs this method
	}
	
	public String returnTitle() {
		return "";
		
	}
}
