package domain;

import persistant.InventoryData;

public class DistanceCalculator
{
	public static double countPortWrongDistance (double distanceInwindow , double measureDistance)
	{
		int len1 = InventoryData.getCanTransformedDataFromPort().size();
		double areaDistance = ((Double) (InventoryData.getXDataFromPort()).get(len1 - 1)).doubleValue()
				- ((Double) (InventoryData.getXDataFromPort()).get(0)).doubleValue();
		return Math.abs(distanceInwindow) / areaDistance * measureDistance;
	}
	
	public static double countFileWrongDistance (double distanceInwindow , double measureDistance)
	{
		int len1 = InventoryData.getCanTransformedDataFromFile().size();
		double areaDistance = ((Double) (InventoryData.getXDataFromFile()).get(len1 - 1)).doubleValue()
				- ((Double) (InventoryData.getXDataFromFile()).get(0)).doubleValue();
		return Math.abs(distanceInwindow) / areaDistance * measureDistance;
	}
	
	public static double countKeyEventWindowPositionForFile (double eventDistance , double measureDistance)
	{
		int len1 = InventoryData.getCanTransformedDataFromFile().size();
		double firstEventPos = ((Double) (InventoryData.getXDataFromFile()).get(0)).doubleValue();
		double lastEventPos = ((Double) (InventoryData.getXDataFromFile()).get(len1 - 1)).doubleValue();
		double areaDistance = lastEventPos - firstEventPos;
		return Math.abs(eventDistance / measureDistance * areaDistance) + firstEventPos;
	}
	
	public static double countKeyEventWindowPositionForPort (double eventDistance , double measureDistance)
	{
		int len1 = InventoryData.getCanTransformedDataFromPort().size();
		double firstEventPos = ((Double) (InventoryData.getXDataFromPort()).get(0)).doubleValue();
		double lastEventPos = ((Double) (InventoryData.getXDataFromPort()).get(len1 - 1)).doubleValue();
		double areaDistance = lastEventPos - firstEventPos;
		return Math.abs(eventDistance/ measureDistance * areaDistance) + firstEventPos;
	}	
	public static Double countCursonPositionForFile (double eventDistance , double measureDistance, 
			double ZeroPosition)
	{
		int len1 = InventoryData.getCanTransformedDataFromFile().size();
		if(len1 == 0)
        {
            return null;
        }
        if(InventoryData.getXDataFromFile().size() == 0)
        {
            return null;
        }
		double firstEventPos = ((Double) (InventoryData.getXDataFromFile()).get(0)).doubleValue();		
		double lastEventPos = ((Double) (InventoryData.getXDataFromFile()).get(len1 - 1)).doubleValue();
		double firstEventPosUpdownPos = firstEventPos+ZeroPosition;
		if(eventDistance < firstEventPosUpdownPos && eventDistance > lastEventPos+ZeroPosition)
		{
			return null;
	}
		double areaDistance = lastEventPos - firstEventPos;
		return (eventDistance-firstEventPosUpdownPos)/ areaDistance * measureDistance ;
	}
	
	public static Double countCursonPositionForPort (double eventDistance , double measureDistance, 
			double ZeroPosition)
	{
		int len1 = InventoryData.getCanTransformedDataFromPort().size();
		double firstEventPos = ((Double) (InventoryData.getXDataFromPort()).get(0)).doubleValue();
		double lastEventPos = ((Double) (InventoryData.getXDataFromPort()).get(len1 - 1)).doubleValue();
		double firstEventPosUpdownPos = firstEventPos+ZeroPosition;
		if(eventDistance < firstEventPosUpdownPos && eventDistance > lastEventPos+ZeroPosition)
		{
			return null;
		}
		double areaDistance = lastEventPos - firstEventPos;
		return (eventDistance-firstEventPosUpdownPos)/ areaDistance * measureDistance ;
	}
}
