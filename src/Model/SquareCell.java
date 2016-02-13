package src.Model;

import javafx.scene.shape.Polygon;

public class SquareCell extends Cell{

	private Polygon myPolygon;
	private Grid myGrid;

	public SquareCell(double x, double y, int state, double sideLen, Grid myGrid){
		super(x, y, state, sideLen, 4);
		this.myGrid = myGrid;
	}
	public Polygon getPoly(){
		return myPolygon;
	}
	public double[] getXPoints(){
		double[] arr = {getCenterX()-getSideLength()/2, getCenterX()+getSideLength()/2, getCenterX()+getSideLength()/2, getCenterX()-getSideLength()/2};
		return arr;
	}
	public double[] getYPoints(){
		double[] arr = {getCenterY()-getSideLength()/2, getCenterY()-getSideLength()/2, getCenterY()+getSideLength()/2, getCenterY()+getSideLength()/2};
		return arr;
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