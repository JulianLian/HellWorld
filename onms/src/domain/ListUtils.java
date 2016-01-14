package domain;

import java.util.List;

public class ListUtils
{
	public static double getMaxNumber (List<Double> v)
	{
		double max = Double.MIN_VALUE;
		try
		{
			for (int i = 0; i < v.size(); i++)
			{
				max = Math.max(max, v.get(i).doubleValue());
			}

		}
		catch (Exception eee)
		{
			System.err.println("计算最大数出错");
		}
		return max;
	}

	public static double getMaxAbsNumber (List<Double> v)
	{
		double max = Double.MIN_VALUE;
		try
		{
			for (int i = 0; i < v.size(); i++)
			{
				max = Math.max(max, Math.abs(v.get(i).doubleValue()));
			}

		}
		catch (Exception eee)
		{
			System.err.println("计算最大数出错");
		}
		return max;
	}
}
