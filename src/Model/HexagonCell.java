package src.Model;

// This entire file is part of my code masterpiece.
// Code's purpose at the top of Cell.java

public class HexagonCell extends Cell{

	public HexagonCell(double centerX, double centerY, int state, double sideLength, Grid myGrid, String edgeType) {
		super(centerX, centerY, state, sideLength, 6, myGrid, edgeType);
		setHeight();
	}

	public HexagonCell(double sideLen, Grid grid, String edgeType) {
		super(0,0,0,sideLen,6,grid,edgeType);
		setHeight();
	}
	
	private void setHeight(){
		super.setHeight(2*getInnerTriangleHeight() + getSideLength());
	}
	
	private double[] shiftByHorizontalRowGap(double[] newXPoints){
		for(int x=0; x<newXPoints.length; x++){
			newXPoints[x] += getOddRowHorizontalGap();
		}
		return newXPoints;
	}
	
	private double[] shiftByVerticalRowGap(double[] newYPoints, int col){
		for(int x=0; x<newYPoints.length; x++){
			newYPoints[x] += col*getOddRowVerticalGap();
		}
		return newYPoints;
	}
	
	private double getOddRowHorizontalGap(){
		return Math.sqrt(2)/2*getSideLength();
	}
	
	private double getOddRowVerticalGap(){
		return -1*getInnerTriangleHeight();
	}
	
	private double getInnerTriangleHeight(){
		return Math.sqrt(2)/2*getSideLength();
	}

	public double XaddToNewRow(int row, int col) {
		int mod = row%2;
		double[] newXPoints = new double[6];
		newXPoints[0] = getWidth()/2;
		newXPoints[1] = getWidth();
		newXPoints[2] = getWidth();
		newXPoints[3] = getWidth()/2;
		newXPoints[4] = 0;
		newXPoints[5] = 0;
		if(mod == 1)
			newXPoints = shiftByHorizontalRowGap(newXPoints);
		setXPoints(newXPoints);
		return getAverageValue(newXPoints);
	}

	public double YaddToNewRow(int row, int col){
		double[] newYPoints = new double[6];
		newYPoints[0] = row*getHeight();
		newYPoints[1] = row*getHeight() + getInnerTriangleHeight();
		newYPoints[2] = row*getHeight() + getInnerTriangleHeight() + getSideLength();
		newYPoints[3] = row*getHeight() + getInnerTriangleHeight()*2 + getSideLength();
		newYPoints[4] = row*getHeight() + getInnerTriangleHeight() + getSideLength();
		newYPoints[5] = row*getHeight() + getInnerTriangleHeight();
		if(row!=0)
			newYPoints = shiftByVerticalRowGap(newYPoints, row
					);
		setYPoints(newYPoints);
		return getAverageValue(newYPoints);
	}
	public double XaddToExistingRow(int row, int col){
		double[] newXPoints = new double[6];
		double[] prevPoints = getGrid().getGridMap().get(row).get(col-1).getXPoints();
		for(int x=0; x<prevPoints.length; x++){
			newXPoints[x] = prevPoints[x] + getWidth();
		}
		setXPoints(newXPoints);
		return getAverageValue(newXPoints);
	}
	public double YaddToExistingRow(int row, int col){
		double[] prevPoints = getGrid().getGridMap().get(row).get(col-1).getYPoints();
		setYPoints(prevPoints);
		return getAverageValue(prevPoints);
	}
	
	public double getWidth(){
		return getSideLength() * Math.sqrt(2);
	}
}