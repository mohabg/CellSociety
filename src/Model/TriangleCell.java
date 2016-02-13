package src.Model;

import javafx.scene.shape.Polygon;

public class TriangleCell extends Cell{

	private Polygon myPolygon;
	private Grid myGrid;
	private double height;
	private int type;

	public TriangleCell(double x, double y, int state, double sideLen, Grid myGrid, int type){
		super(x, y, state, sideLen, 3);
		this.myGrid = myGrid;
		setHeight(sideLen);
		this.type = type;
	}
	public TriangleCell(double sideLen){
		super(0,0,0,sideLen,3);
		setHeight(sideLen);
	}
	public void setHeight(double sideLen){
		height = Math.sqrt(Math.pow(1/2*sideLen, 2) + Math.pow(sideLen, 2));
	}
	public double getHeight(){
		return height;
	}
	public Polygon getPoly(){
		return myPolygon;
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
	public Cell getLeftNeighbor(){
		return myGrid.getCell(getCenterX() - getSideLength(), getCenterY());
	}
	public Cell getTopLeftNeighbor(){
		return myGrid.getCell(getCenterX() - getSideLength(), getCenterY() - getSideLength());
	}
	public Cell getTopNeighbor(){
		return myGrid.getCell(getCenterX(), getCenterY() - getSideLength());
	}
	public Cell getTopRightNeighbor(){
		return myGrid.getCell(getCenterX() + getSideLength(), getCenterY() - getSideLength());
	}
	public Cell getRightNeighbor(){
		return myGrid.getCell(getCenterX() + getSideLength(), getCenterY());
	}
	public Cell getBottomRightNeighbor(){
		return myGrid.getCell(getCenterX() + getSideLength(), getCenterY() + getSideLength());
	}
	public Cell getBottomNeighbor(){
		return myGrid.getCell(getCenterX(), getCenterY() + getSideLength());
	}
	public Cell getBottomLeftNeighbor(){
		return myGrid.getCell(getCenterX() - getSideLength(), getCenterY() - getSideLength());
	}
	public boolean isDiagonalNeighborWith(Cell otherCell){
		//X locations and Y locations both differ by 1
		// to implement
		return (Math.abs(myCenterX - otherCell.getCenterX()) == getSideLength()) && (Math.abs(myCenterY - otherCell.getCenterY()) == getSideLength());
	}
}