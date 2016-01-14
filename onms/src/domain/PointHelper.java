package domain;

import java.util.ArrayList;
import java.util.List;

public class PointHelper
{
	public static List<Double> adjustYDataList (List<Double> yDataList , int step)
	{
		int pointCountAfterConsiderStep = getDataPointCountsUsingStep(step, yDataList.size());
		List<Double> adjustedYDataList = new ArrayList<Double>();
		for (int index = 0; index < pointCountAfterConsiderStep; index++)
		{
			adjustedYDataList.add(yDataList.get(index * step));
		}
		return adjustedYDataList;
	}

	public static int getDataPointCountsUsingStep (int step , int totalPoints)
	{
		try
		{
			if (totalPoints % step == 0) // 如果整除
			{
				return totalPoints / step;
			}
			else
			{
				return totalPoints / step + 1;// 整数部分加1
			}

		}
		catch (Exception e)
		{
			System.err.println("由步长计算数据量时候出错");
		}
		return 0;
	}
}
