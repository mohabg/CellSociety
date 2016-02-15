package src.controller.Simulation;

import java.util.ArrayList;
import java.util.Random;

import src.Model.Cell;
import src.Model.Grid;

public class SegregationSimulation extends Simulation {
    private int noAgent = 0;
    private int firstAgent = 1;
    private int secondAgent = 2;
    //satisfiedPercentage range: 0.0 - 1.0
    private double satisfiedPercentage = 1;
    
    public SegregationSimulation(Grid grid) {
        super(grid);
    }
    public SegregationSimulation() {
		// TODO Auto-generated constructor stub
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
            for(int x=0; x<getGrid().getCells().size(); x++){
            	Cell curr = getGrid().getCells().get(x);
                if(curr.isState(noAgent)){
                    emptyCells.add(curr);
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
    public ArrayList<String> paramsList(){
    	ArrayList<String> toRet = new ArrayList<String>();
    	toRet.add("satisfiedPercentage");
    	return toRet;
    }
    public double getParameter(){
    	return satisfiedPercentage;
    }
    public void setParameter(double aval){
    	satisfiedPercentage = aval;
    }

    @Override
	public ArrayList<String> getParameters() {
		return null;
	}
	@Override
	public void setParameters(ArrayList<Double> paramsList){
		
	}
}
