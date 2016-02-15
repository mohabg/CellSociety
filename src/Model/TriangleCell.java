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
		double x0 = 0;
		double x1 = 0;
		double x2 = 0;
		if(mod == 0){
			x0 = getSideLength()/2;
			newXPoints[0] = x0;
			x1 = 0;
			newXPoints[1] = x1;
			x2 = getSideLength();
			newXPoints[2] = x2;
		}
		else if(mod == 1){
			x0 = 0;
			newXPoints[0] = x0;
			x1 = getSideLength()/2;
			newXPoints[1] = x1;
			x2 = getSideLength();
			newXPoints[2] = x2;
		}
		System.out.println("(x0, x1, x2): ("+x0+", "+x1+", "+x2+")");
		setXPoints(newXPoints);
		return (x0+x1+x2)/3;
	}
	public double YaddToNewRow(int row, int col){
		int mod = (row+col)%2;
		double[] newYPoints = new double[3];
		double y0 = 0;
		double y1 = 0;
		double y2 = 0;
		if(mod == 0){
			y0 = row*getHeight();
			newYPoints[0] = y0;
			y1 = (row+1)*getHeight();
			newYPoints[1] = y1;
			y2 = (row+1)*getHeight();
			newYPoints[2] = y2;
		}
		else if(mod == 1){
			y0 = row*getHeight();
			newYPoints[0] = y0;
			y1 = (row+1)*getHeight();
			newYPoints[1] = y1;
			y2 = row*getHeight();
			newYPoints[2] = y2;
		}
		System.out.println("(y0, y1, y2): ("+y0+", "+y1+", "+y2+")");
		setYPoints(newYPoints);
		return (y0+y1+y2)/3;
	}
	public double XaddToExistingRow(int row, int col){
		int mod = (row+col)%2;
		double[] newXPoints = new double[3];
		double x0 = 0;
		double x1 = 0;
		double x2 = 0;
		double[] prevPoints = myGrid.getGridMap().get(row).get(col-1).getXPoints();
		if(mod == 0){
			x0 = prevPoints[2];
			newXPoints[0] = x0;
			x1 = prevPoints[1];
			newXPoints[1] = x1;
			x2 = prevPoints[1] + getSideLength();
			newXPoints[2] = x2;
		}
		else if(mod == 1){
			x0 = prevPoints[0];
			newXPoints[0] = x0;
			x1 = prevPoints[2];
			newXPoints[1] = x1;
			x2 = prevPoints[0] + getSideLength();
			newXPoints[2] = x2;
		}
		setXPoints(newXPoints);
		return (x0+x1+x2)/3;
	}
	public double YaddToExistingRow(int row, int col){
		int mod = (row+col)%2;
		double[] newYPoints = new double[3];
		double y0 = 0;
		double y1 = 0;
		double y2 = 0;
		double[] prevPoints = myGrid.getGridMap().get(row).get(col-1).getYPoints();
		if(mod == 0){
			y0 = prevPoints[2];
			newYPoints[0] = y0;
			y1 = prevPoints[1];
			newYPoints[1] = y1;
			y2 = prevPoints[1];
			newYPoints[2] = y2;
		}
		else if(mod == 1){
			y0 = prevPoints[0];
			newYPoints[0] = y0;
			y1 = prevPoints[2];
			newYPoints[1] = y1;
			y2 = prevPoints[0];
			newYPoints[2] = y2;
		}
		setYPoints(newYPoints);
		return (y0+y1+y2)/3;
	}
}