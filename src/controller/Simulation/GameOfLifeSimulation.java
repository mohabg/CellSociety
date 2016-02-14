package src.controller.Simulation;

import java.util.ArrayList;

import src.Model.Cell;
import src.Model.Grid;

public class GameOfLifeSimulation extends Simulation{
    private int liveState = 1;
    private int deadState = 0;
    
    public GameOfLifeSimulation(Grid grid) {
        super(grid);
    }
    public GameOfLifeSimulation() {
		// TODO Auto-generated constructor stub
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
        ArrayList<Cell> neighbors = cell.getAllNeighbors();
        for(Cell neighborCell: neighbors){
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
    
    public ArrayList<String> getParameters() {
		return null;
	}
	@Override
	public void setParameters(ArrayList<Double> paramsList) {
		
	}
}
