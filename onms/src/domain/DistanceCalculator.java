package domain;

import persistant.InventoryData;
import persistant.WindowControlEnv;

public class DistanceCalculator
{
		// 这里要考虑图形放大缩小带来的距离的变化,如下两个要好好斟酌
		public static double countPortWrongDistance(double distance, double signalsPerUs)
		{
			// 如果就一个点，没有办法测量
			int signalCounts = InventoryData.getDataFromPortImmutableLength();// 不可改变的数据
			double time = ((double) signalCounts) / signalsPerUs;// 比如30个信号数据是用1微秒
			int len1 = InventoryData.getCanTransformedDataFromPort().size();			
			double totalLongMax = ((Double) (InventoryData.getXDataFromPort()).get(len1 - 1)).doubleValue()
					- ((Double) (InventoryData.getXDataFromPort()).get(0)).doubleValue();

			double by = Math.abs(distance) / totalLongMax;

			// 由于信号是走了一个来回，所以除以2
			return by * time * WindowControlEnv.getMediaSpeed() / 2;

		}

		public static double countFileWrongDistance(double distance, double signalsPerUs)
		{
			int signalCounts = InventoryData.getDataFromFileImmutableLength();
			double time = ((double) signalCounts) / signalsPerUs;// 比如30个信号数据是用1微秒
			int len1 = InventoryData.getCanTransformedDataFromFile().size();			
			double totalLongMax = ((Double) (InventoryData.getXDataFromFile()).get(len1 - 1)).doubleValue()
					- ((Double) (InventoryData.getXDataFromFile()).get(0)).doubleValue();

			double by = Math.abs(distance) / totalLongMax;
			return by * time * WindowControlEnv.getMediaSpeed() / 2;// 由于信号是走了一个来回

		}
}
