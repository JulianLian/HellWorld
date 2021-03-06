package filedatagetter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import env.MDLogger;

public class PersistRawDataReader
{
	public static List<Double> getWaveDoubleData (String filePath)
	{
		List<Double> dataPoints = new ArrayList<Double>();
		BufferedReader br = null;
		try
		{
			FileReader fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String sb;
			while ((sb = br.readLine()) != null)
			{
				String[] oneLineData = sb.split(" ");
				for (String oneData : oneLineData)
				{
					dataPoints.add((Double.valueOf(oneData)));
				}
			}
		}
		catch (IOException e)
		{
			MDLogger.INS.error(e.getMessage());
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					MDLogger.INS.error(e.getMessage());
				}
			}
		}
		return dataPoints;
	}
	public static List<String> getEventData (String filePath)
	{
		List<String> dataPoints = new ArrayList<String>();
		BufferedReader br = null;
		try
		{
			FileReader fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String sb;
			while ((sb = br.readLine()) != null)
			{
				dataPoints.add(sb);
			}
		}
		catch (IOException e)
		{
			MDLogger.INS.error(e.getMessage());
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					MDLogger.INS.error(e.getMessage());
				}
			}
		}
		return dataPoints;
	}
}
