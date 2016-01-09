package draw;

import domain.ListUtils;
import domain.PointHelper;
import persistant.IDataPersister;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.List;

public class DrawUtils
{
	private static void drawGraphic(Graphics2D d, Color color, List<Double> xDataList, List<Double> yDataList)
	{
		if (xDataList != null && yDataList != null && xDataList.size() > 0 && xDataList.size() == yDataList.size())
		{
			GeneralPath path = new GeneralPath();
			int pointCounts = xDataList.size();
			for (int i = 0; i < pointCounts; i++)
			{
				if (i == 0)
					path.moveTo(xDataList.get(i), yDataList.get(i));
				else
					path.lineTo(xDataList.get(i),  yDataList.get(i) );
			}
			// 用红色来画图形
			d.setColor(color);
			d.draw(path);
		}
	}

	// ***************************************************************************************************************
	public static void drawPersistData(Graphics2D d, IDataPersister dataPersister)
	{
		drawGraphic(d, dataPersister.getPresentColor(), dataPersister.getCashedXData(),dataPersister.getCashedYData());
	}

	public static void drawDataAfterAdjustAxis(Graphics2D d, Dimension dimension, IDataPersister dataPersister)
	{
		double screenWidth = dimension.getWidth();
		double screenHeight= dimension.getHeight();
		double baseX = -screenWidth/2;
		double halfWindowHeight =  (screenHeight / 2 - 10 - 1);
		List<Double> pointsYPositions = dataPersister.getYData();
		dataPersister.setRepaintForNewDataComing(false);

		int pointCounts = PointHelper.getDataPointCountsUsingStep(dataPersister.getStep(),pointsYPositions.size());
		if (pointCounts > 0)
		{
			GeneralPath path = new GeneralPath();
			double xData[] = new double[pointCounts];
			double yData[] = new double[pointCounts];

//				double maxVal = ListUtils.getMaxNumber(pointsYPositions);
			double maxAbsVal = ListUtils.getMaxAbsNumber(pointsYPositions);

			double intervalDistance = (pointCounts == 1 ? 0 : (( (screenWidth)) / (pointCounts - 1)));
			double minVal = Double.MAX_VALUE;
			for (int index = 0; index < pointCounts; index++)
			{
				xData[index] = baseX + intervalDistance * index;
				// 我们让最大数为最上面可视区域－1,最大值/信号值＝(height/2-11)/我们这里的相对坐标值
//					yData[index] = (-1)* (halfWindowHeight * pointsYPositions.get(index) / maxVal);
				yData[index] = (-1)* (halfWindowHeight * pointsYPositions.get(index) / maxAbsVal);
				minVal = Math.min(minVal, yData[index]);
//					yData[index] +=  screenHeight / 4;
				if (index == 0)
					path.moveTo(xData[index] , yData[index]);
				else
					path.lineTo(xData[index] , yData[index]);
			}

			saveNewAdjustedPosition(xData,  yData,  dataPersister);
			// 用红色来画图形
			d.setColor(dataPersister.getPresentColor());
			d.draw(path);
		}
	}

	public static void saveNewAdjustedPosition(double[] xData, double[] yData,
											   IDataPersister dataPersister)
	{
		List<Double> xDataList = dataPersister.getCashedXData();
		List<Double> yDataList = dataPersister.getCashedYData();
		if (xDataList != null && yDataList != null && xData != null && yData != null && xData.length == yData.length)
		{
			xDataList.clear();
			yDataList.clear();
			for(int index = 0, length = xData.length; index < length; index ++)
			{
				xDataList.add(xData[index]);
				yDataList.add(yData[index]);
			}
		}
	}
}
