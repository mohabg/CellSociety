
public class GameOfLifeSimulation extends Simulation{
	private int liveState = 1;
	private int deadState = 0;
	
	public GameOfLifeSimulation(Grid grid) {
		super(grid);
	}
	public void setLiveStateParameter(int liveCell){
		liveState = liveCell;
	}
	public void setDeadStateParameter(int deadCell){
		deadState = deadCell;
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
				Cell updatedCell = cell.clone();
				updatedCell.setState(deadState);
				return updatedCell;
			}
		}
		if(cell.isState(deadState)){
			if(liveNeighbors == 3){
				Cell updatedCell = cell.clone();
				updatedCell.setState(liveState);
				return updatedCell;
			}
		}
		return cell;
	}
}
