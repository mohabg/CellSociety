package src.Model;

public class SquareCell extends Cell{

	public SquareCell(double x, double y, int state, double sideLen, Grid myGrid, String edgeType){
		super(x, y, state, sideLen, 4, myGrid, edgeType);
		setHeight(sideLen);
	}
	
	public SquareCell(double sideLen, Grid myGrid, String edgeType){
		super(0,0,0,sideLen,4,myGrid, edgeType);
		setHeight(sideLen);
	}
	
	public double XaddToNewRow(int row, int col){
		double[] newXPoints = new double[4];
		newXPoints[0] = 0;
		newXPoints[1] = 0;
		newXPoints[2] = getSideLength();
		newXPoints[3] = getSideLength();
		setXPoints(newXPoints);
		return getAverageValue(newXPoints);
	}
	public double YaddToNewRow(int row, int col){
		double[] newYPoints = new double[4];
		newYPoints[0] = row*getSideLength();
		newYPoints[1] = (row+1)*getSideLength();
		newYPoints[2] = (row+1)*getSideLength();
		newYPoints[3] = row*getSideLength();
		setYPoints(newYPoints);
		return getAverageValue(newYPoints);
	}
	public double XaddToExistingRow(int row, int col){
		double[] newXPoints = new double[4];
		double[] prevPoints = getGrid().getGridMap().get(row).get(col-1).getXPoints();
		newXPoints[0] = prevPoints[3];
		newXPoints[1] = prevPoints[2];
		newXPoints[2] = prevPoints[2] + getSideLength();
		newXPoints[3] = prevPoints[3] + getSideLength();
		setXPoints(newXPoints);
		return getAverageValue(newXPoints);
	}
	public double YaddToExistingRow(int row, int col){
		double[] newYPoints = new double[4];
		double[] prevPoints = getGrid().getGridMap().get(row).get(col-1).getYPoints();
		newYPoints[0] = prevPoints[0];
		newYPoints[1] = prevPoints[1];
		newYPoints[2] = prevPoints[2];
		newYPoints[3] = prevPoints[3];
		setYPoints(newYPoints);
		return getAverageValue(newYPoints);
	}
	public double getWidth(){
		return getSideLength();
	}
}