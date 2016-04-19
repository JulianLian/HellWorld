package domain;

import persistant.InventoryData;

public class DistanceCalculator
{
	// 这里要考虑图形放大缩小带来的距离的变化,如下两个要好好斟酌
	// public static double countPortWrongDistance (double distance , double
	// signalsPerUs)
	// {
	// // 如果就一个点，没有办法测量
	// int signalCounts = InventoryData.getDataFromPortImmutableLength();//
	// 不可改变的数据
	// double time = ((double) signalCounts) / signalsPerUs;//
	// 比如30个信号数据是用1微秒
	// int len1 = InventoryData.getCanTransformedDataFromPort().size();
	// double totalLongMax = ((Double)
	// (InventoryData.getXDataFromPort()).get(len1 - 1)).doubleValue()
	// - ((Double) (InventoryData.getXDataFromPort()).get(0)).doubleValue();
	//
	// double by = Math.abs(distance) / totalLongMax;
	//
	// // 由于信号是走了一个来回，所以除以2
	// return by * time * WindowControlEnv.getMediaSpeed() / 2;
	//
	// }
	
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
