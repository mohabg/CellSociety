package src.Model;

import javafx.scene.shape.Polygon;

public class TriangleCell extends Cell{

	private Grid myGrid;
	private double height;
	private int type;

	public TriangleCell(double x, double y, int state, double sideLen, Grid myGrid, int type){
		super(x, y, state, sideLen, 3, myGrid);
		this.myGrid = myGrid;
		setHeight(sideLen);
		this.type = type;
	}
	public TriangleCell(double sideLen, Grid myGrid){
		super(0,0,0,sideLen,3, myGrid);
		setHeight(sideLen);
	}
	public void setHeight(double sideLen){
		height = Math.sqrt(Math.pow(1/2*sideLen, 2) + Math.pow(sideLen, 2));
	}
	public double getHeight(){
		return height;
	}
	public double[] getXPoints(){
		double[] arr = {getCenterX() - getSideLength()/2, getCenterX(), getCenterX() + getSideLength()/2};
		return arr;
	}
	public double[] getYPoints(){
		double[] arr = {height, 0, height};
		double[] arr2 = {0, height, 0};
		if(type == 0)
			return arr;
		else
			return arr2;
	}
}