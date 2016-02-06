package src.controller.Simulation;
import java.util.Random;

import src.Model.Cell;
import src.View.Grid;

public class FireSimulation extends Simulation{
	private int empty = 0;
	private int tree = 1;
	private int burning = 2;
	//probCatch range: 0.0 - 1.0
	private double probCatch = 0.5;
	
	public FireSimulation(Grid grid) {
		super(grid);
	}
	public void setProbCatch(double prob){
		probCatch = prob;
	}
	public void setEmptyParameter(int emptyCell){
		empty = emptyCell;
	}
	public void setTreeParameter(int treeCell){
		tree = treeCell;
	}
	public void setBurningParameter(int burningCell){
		burning = burningCell;
	}
	public String returnTitle(){
		return "Fire Simulation";
	}
	
	@Override
	public Cell updateCellState(Cell cell) {
		if(cell.isState(empty)){
			return cell;
		}
		if(cell.isState(burning)){
			cell.setState(empty);
		}
		if(cell.isState(tree)){
			
			for(Cell neighborCell : getGrid().getNonDiagonalNeighbors(cell)){
				if(neighborCell.isState(burning)){
					Random rand = new Random();
					double randomProbability = rand.nextDouble();
					if(randomProbability <= probCatch){
						cell.setState(burning);
					}
				}
			}
		}
		return cell;
	}

}
