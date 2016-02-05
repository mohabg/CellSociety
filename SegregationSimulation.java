import java.util.ArrayList;

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
	@Override
	public void initialize(ArrayList<Integer> cellStates) {
		
	}
	
	@Override
	public Cell updateCellState(Cell cell) {
		int currentState = cell.getState();
		if(currentState == noAgent){
			return cell;
		}
		int similarAgents = 0;
		int differentAgents = 0;
		for(Cell neighborCell : getGrid().getAllNeighbors(cell)){
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
		double similarPercentage = (similarAgents) / (similarAgents + differentAgents);
		if(similarPercentage <= satisfiedPercentage){
			//Dissatisfied Agent
			CellIterator cellIt = getGrid().getCellIterator();
			while(cellIt.iterator().hasNext()){
				Cell emptyCell = cellIt.iterator().next();
				if(emptyCell.isState(noAgent)){
					//Move Agent to Empty Cell
					emptyCell.setState(currentState);
					cell.setState(noAgent);
				}
			}
		}
		//Satisfied Agent
		return cell;
	}

}
