package src.Model;

public class TriangleCell extends Cell{

	private double height;
	private Grid myGrid;

	public TriangleCell(double x, double y, int state, double sideLen, Grid myGrid){
		super(x, y, state, sideLen, 3, myGrid);
		setHeight(sideLen);
		this.myGrid = myGrid;
	}
	public TriangleCell(double sideLen, Grid myGrid){
		super(0,0,0,sideLen,3, myGrid);
		setHeight(sideLen);
		this.myGrid = myGrid;
	}
	public void setHeight(double sideLen){
		height = Math.sqrt(Math.pow(1/2*sideLen, 2) + Math.pow(sideLen, 2));
	}
	public double getHeight(){
		return height;
	}
	public double XaddToNewRow(int row, int col){
		int mod = (row+col)%2;
		double[] newXPoints = new double[3];
		if(mod == 0){
			newXPoints[0] = getSideLength()/2;
			newXPoints[1] = 0;
			newXPoints[2] = getSideLength();
		}
		else if(mod == 1){
			newXPoints[0] = 0;
			newXPoints[1] = getSideLength()/2;
			newXPoints[2] = getSideLength();
		}
		setXPoints(newXPoints);
		return (newXPoints[0] + newXPoints[1] + newXPoints[2])/3;
	}
	public double YaddToNewRow(int row, int col){
		int mod = (row+col)%2;
		double[] newYPoints = new double[3];
		if(mod == 0){
			newYPoints[0] = row*getHeight();
			newYPoints[1] = (row+1)*getHeight();
			newYPoints[2] = (row+1)*getHeight();
		}
		else if(mod == 1){
			newYPoints[0] = row*getHeight();
			newYPoints[1] = (row+1)*getHeight();
			newYPoints[2] = row*getHeight();
		}
		setYPoints(newYPoints);
		return (newYPoints[0] + newYPoints[1] + newYPoints[2])/3;
	}
	public double XaddToExistingRow(int row, int col){
		int mod = (row+col)%2;
		double[] newXPoints = new double[3];
		double[] prevPoints = myGrid.getGridMap().get(row).get(col-1).getXPoints();
		if(mod == 0){
			newXPoints[0] = prevPoints[2];
			newXPoints[1] = prevPoints[1];
			newXPoints[2] = prevPoints[1] + getSideLength();
		}
		else if(mod == 1){
			newXPoints[0] = prevPoints[0];
			newXPoints[1] = prevPoints[2];
			newXPoints[2] = prevPoints[0] + getSideLength();
		}
		setXPoints(newXPoints);
		return (newXPoints[0] + newXPoints[1] + newXPoints[2])/3;
	}
	public double YaddToExistingRow(int row, int col){
		int mod = (row+col)%2;
		double[] newYPoints = new double[3];
		double[] prevPoints = myGrid.getGridMap().get(row).get(col-1).getYPoints();
		if(mod == 0){
			newYPoints[0] = prevPoints[2];
			newYPoints[1] = prevPoints[1];
			newYPoints[2] = prevPoints[1];
		}
		else if(mod == 1){
			newYPoints[0] = prevPoints[0];
			newYPoints[1] = prevPoints[2];
			newYPoints[2] = prevPoints[0];
		}
		setYPoints(newYPoints);
		return (newYPoints[0] + newYPoints[1] + newYPoints[2])/3;
	}
}