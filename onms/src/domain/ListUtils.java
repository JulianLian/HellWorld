package domain;

import java.util.List;

import env.MDLogger;

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
		catch (Exception e)
		{
		        MDLogger.INS.error(e.getMessage());
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
		catch (Exception e)
		{
			MDLogger.INS.error(e.getMessage());
			System.err.println("计算最大数出错");
		}
		return max;
	}
	public static double getMaxAbsNumber (double[] v)
	{
		double max = Double.MIN_VALUE;
		try
		{
			for (double onev : v)
			{
				max = Math.max(max, Math.abs(onev));
			}			
		}
		catch (Exception e)
		{
			MDLogger.INS.error(e.getMessage());
			System.err.println("计算最大数出错");
		}
		return max;
	}
	
	public static double[] getMaxMinNumber (double[] v)
	{
		double[] max_minVal = new double[2];
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		try
		{
			for (double onev : v)
			{
				max = Math.max(max,onev);
				min = Math.min(min, onev);
			}			
		}
		catch (Exception e)
		{
			MDLogger.INS.error(e.getMessage());
			System.err.println("计算最大数出错");
		}
		max_minVal[0] = max;
		max_minVal[1] = min;
		return max_minVal;
	}
	
	public static double[] toDoubleArray(List<Double> vals)
	{
		if(vals == null || vals.size() == 0)
		{
			return new double[0];
		}
		double[] doubleVal = new double[vals.size()];
		for(int index = 0 , length = vals.size() ; index < length; index ++)
		{
			doubleVal[index] = vals.get(index);
		}
		return doubleVal;
	}
}
