package src.Model;
import java.util.ArrayList;
import java.util.List;

public class PatchOfGround{
	private List<Double> pheromones;
	
	public PatchOfGround(){
		pheromones = new ArrayList<Double>();
	}
	public int numberOfPheromones(){
		int numberOfPheromones = 0;
		for(Double pheromone : pheromones){
			if(pheromone > 0){
				numberOfPheromones++;
			}
		}
		return numberOfPheromones;
	}
	public void addPheromone(double pheromone){
		pheromones.add(pheromone);
	}
	public void increasePheromone(double pheromone, int index){
		pheromones.remove(index);
		pheromones.add(index, pheromone);
	}
	public void setPheromone(double pheromone, int index){
		pheromones.remove(index);
		pheromones.add(index, pheromone);
	}
	public List<Double> getPheromones(){
		return pheromones;
	}
	public void diffusePheromones(List<Cell> neighbors, double diffusionRatio){
		for(Cell cell : neighbors){
			List<Double> neighborPheromones = cell.getGround().getPheromones();
			for(int i = 0; i < pheromones.size(); i++){
				double pheromone = diffusionRatio * pheromones.get(i);
				if(i < neighborPheromones.size()){
					double pheromoneToAdd = neighborPheromones.get(i);
					pheromone += pheromoneToAdd;
				}
				neighborPheromones.add(i, pheromone);
			}
		}
	}
}
