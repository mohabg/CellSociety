package src.Model;

import javafx.scene.shape.Polygon;

public class SquareCell extends Cell{

	public SquareCell(double x, double y, int state, double sideLen, Grid myGrid){
		super(x, y, state, sideLen, 4, myGrid);
	}
	public double[] getXPoints(){
		double[] arr = {getCenterX()-getSideLength()/2, getCenterX()+getSideLength()/2, getCenterX()+getSideLength()/2, getCenterX()-getSideLength()/2};
		return arr;
	}
	public double[] getYPoints(){
		double[] arr = {getCenterY()-getSideLength()/2, getCenterY()-getSideLength()/2, getCenterY()+getSideLength()/2, getCenterY()+getSideLength()/2};
		return arr;
	}
}