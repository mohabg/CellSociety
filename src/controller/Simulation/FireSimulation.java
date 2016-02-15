package src.controller.Simulation;
import java.util.*;
import src.Model.Cell;
import src.Model.Grid;

public class FireSimulation extends Simulation{
    private int empty = 0;
    private int tree = 1;
    private int burning = 2;
    private double probCatch;
    
    public FireSimulation(Grid myGrid) {
        super(myGrid);
    }
    public FireSimulation() {
		
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
            for(Cell neighborCell : cell.getNonDiagonalNeighbors()){
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
    
    public ArrayList<String> paramsList(){
    	ArrayList<String> toRet = new ArrayList<String>();
    	toRet.add("probCatch");
    	return toRet;
    }
    
    public double getParameter(){
    	return probCatch;
    }
    public void setParameter(double aval){
    	probCatch = aval;
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
		if(paramsList.get(0) == DEFAULT_VALUE){
			probCatch = PROB_CATCH_DEFAULT;
		}
	}
	@Override
	public void createOrRemovePerStep() {
		// TODO Auto-generated method stub
		
	}
    
}