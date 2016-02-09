package src.controller.Simulation;
import java.util.ArrayList;
import java.util.Random;

import src.Model.Cell;
import src.View.Grid;

public class FireSimulation extends Simulation{
	private int empty = 0;
	private int tree = 1;
	private int burning = 2;
	private double probCatch;
	
	public FireSimulation(Grid grid) {
		super(grid);
	}
	public FireSimulation() {
		
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
	
	public ArrayList<String> getParameters(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("probCatch");
		return list;
	}
	@Override
	public void setParameters(ArrayList<Double> paramsList) {
		double DEFAULT_VALUE = -1/999;
		double PROB_CATCH_DEFAULT = 0.5;
		probCatch = paramsList.get(0);
		if(paramsList.get(0) == DEFAULT_VALUE)
			probCatch = PROB_CATCH_DEFAULT;
	}

}
