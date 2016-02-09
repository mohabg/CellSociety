package src.controller.Simulation;

import java.util.ArrayList;

import src.Model.Cell;
import src.View.Grid;

public class GameOfLifeSimulation extends Simulation{
	private int liveState = 1;
	private int deadState = 0;
	
	public GameOfLifeSimulation(Grid grid) {
		super(grid);
	}
	public GameOfLifeSimulation() {
		
	}
	public void setLiveStateParameter(int liveCell){
		liveState = liveCell;
	}
	public void setDeadStateParameter(int deadCell){
		deadState = deadCell;
	}
	public String returnTitle(){
		return "Game Of Life Simulation";
	}
	@Override
	public Cell updateCellState(Cell cell) {
		int liveNeighbors = 0;
		for(Cell neighborCell: getGrid().getAllNeighbors(cell)){
			if(neighborCell.isState(liveState)){
				liveNeighbors++;
			}
		}
		if(cell.isState(liveState)){
			if(liveNeighbors != 2 && liveNeighbors != 3){
				cell.setState(deadState);
			}
		}
		if(cell.isState(deadState)){
			if(liveNeighbors == 3){
				cell.setState(liveState);
			}
		}
		return cell;
	}
	@Override
	public ArrayList<String> getParameters() {
		return null;
	}
	@Override
	public void setParameters(ArrayList<Double> paramsList) {
		
	}
}