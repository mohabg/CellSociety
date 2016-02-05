import java.util.*;

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
	public abstract void initialize(ArrayList<Integer> cellStates);
	
	public void run(){
		while(!isPaused){
			step();
		}
	}
	public void pause(){
		isPaused = true;
	}
	
	public void step(){
		oldGrid = myGrid.getGridClone();
		CellIterator cellIt = oldGrid.getCellIterator();
		while(cellIt.iterator().hasNext()){
			myGrid.setCell(updateCellState(cellIt.iterator().next()));
		}
	}
	public abstract Cell updateCellState(Cell cell);
}
