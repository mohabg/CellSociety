package src.controller.Simulation;

import java.util.ArrayList;
import java.util.Random;

import src.Model.Cell;
import src.View.Grid;

public class SegregationSimulation extends Simulation {
	private int noAgent = 0;
	private int firstAgent = 1;
	private int secondAgent = 2;
	//satisfiedPercentage range: 0.0 - 1.0
	private double satisfiedPercentage = 0.3;
	
	public SegregationSimulation(Grid grid) {
		super(grid);
	}
	public void setSatisfiedPercentage(double percentage){
		satisfiedPercentage = percentage;
	}
	public double getSatisfiedPercentage(){
		return satisfiedPercentage;
	}
	public void setNoAgentParameter(int emptyCell){
		noAgent = emptyCell;
	}
	public void setFirstAgentParameter(int firstAgentCell){
		firstAgent = firstAgentCell;
	}
	public void setSecondAgentParameter(int secondAgentCell){
		secondAgent = secondAgentCell;
	}
	public String returnTitle(){
		return "Segregation Simulation";
	}
	@Override
	public Cell updateCellState(Cell cell) {
		int currentState = cell.getState();
		if(currentState == noAgent){
			return cell;
		}
		int similarAgents = 0;
		int differentAgents = 0;
		for(Cell neighborCell : cell.getAllNeighbors()){
			int neighborState = neighborCell.getState();
			if(neighborState == noAgent){
				continue;
			}
			if(currentState == neighborState){
				similarAgents++;
			}
			else{
				differentAgents++;
			}
		}
		double similarPercentage = 0;
		if(similarAgents + differentAgents > 0){
		 similarPercentage = (similarAgents) / (similarAgents + differentAgents);
		}
		if(similarPercentage <= satisfiedPercentage){
			//Dissatisfied Agent
			ArrayList<Cell> emptyCells = new ArrayList<Cell>();
			for(Cell emptyCell: getGrid().getCells()){
				if(emptyCell.isState(noAgent)){
					emptyCells.add(emptyCell);
				}
			}
			Random rand = new Random();
			int randomCellIndex = rand.nextInt(emptyCells.size());
			Cell randomEmptyCell = emptyCells.get(randomCellIndex);
			randomEmptyCell.setState(currentState);
			cell.setState(noAgent);
		}
		//Satisfied Agent
		return cell;
	}

}
