package rule;

public class AxisShowvalPair
{
	private double[] axisVals;
	private double[]  pointsYPositions;
	public AxisShowvalPair(double[] axisVals, double[] pointsYPositions)
	{
		super();
		this.axisVals = axisVals;
		this.pointsYPositions = pointsYPositions;
	}
	public double[]  getAxisVals ()
	{
		return axisVals;
	}
	public double[] getShowVals ()
	{
		return pointsYPositions;
	}
}
