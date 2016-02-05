package src.controller.Simulation;
import java.util.*;

import src.Model.Cell;
import src.View.Grid;
import src.controller.CellIterator;

public abstract class Simulation {
	
	private Grid oldGrid;
	private Grid myGrid;
	private boolean isPaused;
	
	public Simulation(Grid grid){
		isPaused = false;
		this.myGrid = grid;
	}
	
	public Grid getGrid(){
		return myGrid;
	}
	//Initializes cells row by row
	public void initialize(ArrayList<Integer> cellStates){
		int statesListIndex = 0;
		CellIterator cellIt = myGrid.getCellIterator();
		while(cellIt.iterator().hasNext()){
			cellIt.iterator().next().setState(cellStates.get(statesListIndex++));
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
	
	public Grid step(){
		oldGrid = myGrid.getGridClone();
		CellIterator cellIt = oldGrid.getCellIterator();
		while(cellIt.iterator().hasNext()){
			myGrid.setCell(updateCellState(cellIt.iterator().next()));
		}
		return myGrid;
	}
	public abstract Cell updateCellState(Cell cell);
}
