package draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import domain.ListUtils;
import domain.PointHelper;
import persistant.IDataPersister;
import rule.AxisShowvalPair;

public class DrawUtils
{
	public static final int RULES_WIDTH = 10;
	public static final int RULE_LABEL_DELTA = 2;
	private static List<AxisShowvalPair> drawGraphic (Graphics2D d , Dimension dimension, Color color , 
			List<Double> xDataList , List<Double> yDataList, 
			List<Double> yActualVals, double actualMeasureDistance)
	{
		ArrayList<AxisShowvalPair> axisShowPairs = null;
		if (xDataList != null && yDataList != null && xDataList.size() > 0
				&& xDataList.size() == yDataList.size())
		{
			axisShowPairs = new ArrayList<AxisShowvalPair>(2);
			
			GeneralPath path = new GeneralPath();
			int pointCounts = xDataList.size();
			
			for (int i = 0; i < pointCounts; i++)
			{
				if (i == 0)
					path.moveTo(xDataList.get(i), yDataList.get(i));
				else
					path.lineTo(xDataList.get(i), yDataList.get(i));
			}
			// 用红色来画图形
			d.setColor(color);
			d.draw(path);
			
			double[] axisX = ListUtils.toDoubleArray(xDataList);
			double[] axisY = ListUtils.toDoubleArray(yDataList);
			
			AxisShowvalPair xPair = new AxisShowvalPair(axisX, new double[]{actualMeasureDistance});
			AxisShowvalPair yPair = new AxisShowvalPair(axisY, ListUtils.toDoubleArray( yActualVals));
						
			axisShowPairs.add(xPair);
			axisShowPairs.add(yPair);			
//			DrawUtils.showMeasureRuler(d, dimension, maxAbsVal,40);
		}
		return axisShowPairs;
	}
	
	// ***************************************************************************************************************
	public static List<AxisShowvalPair> drawPersistData (Graphics2D d , Dimension dimension, 
			IDataPersister dataPersister, double actualMeasureDistance)
	{
		return drawGraphic(d, dimension, dataPersister.getPresentColor(), dataPersister.getCashedXData(),
				dataPersister.getCashedYData(), dataPersister.getYData(), actualMeasureDistance);
	}
	
	public static List<AxisShowvalPair> drawDataAfterAdjustAxis (Graphics2D d , Dimension dimension , 
			IDataPersister dataPersister, double actualMeasureDistance)
	{
		List<AxisShowvalPair> axisShowPairs = null;
		double screenWidth = dimension.getWidth();
		double screenHeight = dimension.getHeight();
		double baseX = -screenWidth / 2;
		double halfWindowHeight = (screenHeight / 2 - 10 - 1);
		List<Double> pointsYPositions = dataPersister.getYData();
		dataPersister.setRepaintForNewDataComing(false);
		
		int pointCounts = PointHelper.getDataPointCountsUsingStep(dataPersister.getStep(),
				pointsYPositions.size());
		if (pointCounts > 0)
		{
			axisShowPairs = new ArrayList<AxisShowvalPair>(2);
			
			GeneralPath path = new GeneralPath();
			double xData[] = new double[pointCounts];
			double yData[] = new double[pointCounts];
			
			double maxAbsVal = ListUtils.getMaxAbsNumber(pointsYPositions);
			double intervalDistance = (pointCounts == 1 ? 0 : (((screenWidth)) / (pointCounts - 1)));
			double minVal = Double.MAX_VALUE;
			for (int index = 0; index < pointCounts; index++)
			{
				xData[index] = baseX + intervalDistance * index;
				yData[index] = (-1) * (halfWindowHeight * pointsYPositions.get(index) / maxAbsVal);
				minVal = Math.min(minVal, yData[index]);
				
				if (index == 0)
					path.moveTo(xData[index], yData[index]);
				else
					path.lineTo(xData[index], yData[index]);
			}
			
			saveNewAdjustedPosition(xData, yData, dataPersister);
			// 用红色来画图形
			d.setColor(dataPersister.getPresentColor());
			d.draw(path);
			AxisShowvalPair xPair = new AxisShowvalPair(xData, new double[]{actualMeasureDistance});
			AxisShowvalPair yPair = new AxisShowvalPair(yData,  ListUtils.toDoubleArray(pointsYPositions));
			axisShowPairs.add(xPair);
			axisShowPairs.add(yPair);
//			DrawUtils.showMeasureRuler(d, dimension, maxAbsVal,40);			
		}
		return axisShowPairs;
	}
	
	public static void saveNewAdjustedPosition (double[] xData , double[] yData , IDataPersister dataPersister)
	{
		List<Double> xDataList = dataPersister.getCashedXData();
		List<Double> yDataList = dataPersister.getCashedYData();
		if (xDataList != null && yDataList != null && xData != null && yData != null
				&& xData.length == yData.length)
		{
			xDataList.clear();
			yDataList.clear();
			for (int index = 0, length = xData.length; index < length; index++)
			{
				xDataList.add(xData[index]);
				yDataList.add(yData[index]);
			}
		}
	}
	
	
	public static void showMeasureRuler (Graphics2D d , Dimension dimension,  double maxVal,  
			int totalRulerPoint)
	{
		double screenWidth = dimension.getWidth();
		double screenHeight = dimension.getHeight();	
		double distance = screenHeight/totalRulerPoint;
		double interval = Math.abs(maxVal)/totalRulerPoint;
		
		d.setColor(Color.GREEN);
		
		double x1 = (-1)*screenWidth/2;
		double y1 = (-1)*screenHeight/2;
		double x2 = x1 + 100;
		double y2 = screenHeight/2;
		int labelTotalDelta = RULES_WIDTH+RULE_LABEL_DELTA;
		double xend = x1+RULES_WIDTH;
		double xLabelEnd = x1+labelTotalDelta;
		d.fill(new Rectangle2D.Double(x1, y1, x2, y2));
		for(int index = 0; index < totalRulerPoint/2; index ++)
		{
			d.draw(new Line2D.Double(x1, -index*distance, xend,-index*distance));	
			d.drawString((int)(0+index*interval)+"",  (int)xLabelEnd, (int)(-index*distance)+1);
		}
		for(int index = 0; index < totalRulerPoint/2; index ++)
		{
			d.draw(new Line2D.Double(x1, index*distance, xend, index*distance));	
			d.drawString((int)(0-index*interval)+"",  (int)xLabelEnd, (int)(index*distance)+1);
		}			
	}
}
