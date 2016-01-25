
package persistant;
/*
 * 
 * 这里面，我们准备把从端口获取到的数据
 * 及 从文件中调出来的数据全部用这个类来存储，
 * 原始数据两个，可以用来变化的两组，还有两个分别表示x,y
 * 坐标的位置，这在图形显示时候需要
 * 
 */

import java.util.ArrayList;
import java.util.List;

public class InventoryData
{
	// 说明tempDataFromPort是存放从端口获取的数据
	// dataFromFile是从文件加载进来的数据
	private static List<Double> dataFromPortCanTransformed = new ArrayList<Double>(); // 可以更改的
	private static List<Double> dataFromPortImmutable = new ArrayList<Double>(); // 要存放到文件中的,读进来就不改
	private static List<Double> dataFromFileCanTransformed = new ArrayList<Double>();
	private static List<Double> dataFromFileImmutable = new ArrayList<Double>();

	// 这里面存放x轴上的位置
	private static List<Double> xDataFromPort = new ArrayList<Double>();
	private static List<Double> xDataFromFile = new ArrayList<Double>();

	// *****************************************
	// **********************************
	// ***************************************** 端口部分数据处理
	// *********************************
	// *****************************************
	// **********************************

	// *********************端口数据，它可以改变
	public static List<Double> getCanTransformedDataFromPort ()
	{
		return dataFromPortCanTransformed;
	}

	public static void setCanTransformedDataFromPort (List<Double> c)
	{
		dataFromPortCanTransformed = c;
	}

	public static boolean hasDataToShow ()
	{
		return getCanTransformedDataFromPort().size() != 0
				|| InventoryData.getCanTransformedDataFromFile().size() != 0;
	}

	// ************************端口数据，它不可以改变

	public static List<Double> getDataFromPortImmutable ()
	{
		return dataFromPortImmutable;
	}

	public static void setDataFromPortImmutable (List<Double> c)
	{
		dataFromPortImmutable = c;
	}

	public static int getDataFromPortImmutableLength ()
	{
		return getDataFromPortImmutable().size();
	}

	// ************************端口数据对应的x坐标
	public static List<Double> getXDataFromPort ()
	{
		return xDataFromPort;
	}

//	public static void setXDataFromPort (List<Double> c)
//	{
//		xDataFromPort = c;
//	}

	public static int getXDataFromPortLength ()
	{
		return getXDataFromPort().size();
	}

	// *****************************************
	// **********************************
	// ***************************************** 文件部分数据处理
	// *********************************
	// *****************************************
	// **********************************

	public static List<Double> getCanTransformedDataFromFile ()
	{
		return dataFromFileCanTransformed;
	}

	public static void setCanTransformedDataFromFile (List<Double> c)
	{
		dataFromFileCanTransformed = c;
	}

	public static void setDataFromFileNeedPrint (List<Double> c)
	{
		dataFromFileImmutable = c;
	}

	public static int getDataFromFileImmutableLength ()
	{
		return dataFromFileImmutable.size();
	}

	public static List<Double> getDataFromFileImmutable ()
	{
		return dataFromFileImmutable;
	}

	// ************************文件数据对应的x坐标
	public static List<Double> getXDataFromFile ()
	{
		return xDataFromFile;
	}

	public static int getXDataFromFileLength ()
	{
		return getXDataFromFile().size();
	}

	// *************************************************
	// ***********************
	// ************************************************* 让所有数据复位
	// ***********************
	// *************************************************
	// ***********************
	public static void clearPersistData ()
	{
		if (dataFromPortCanTransformed.size() != 0)
			dataFromPortCanTransformed.clear();
		if (dataFromPortImmutable.size() != 0)
			dataFromPortImmutable.clear();
		if (dataFromFileCanTransformed.size() != 0)
			dataFromFileCanTransformed.clear();
		if (dataFromFileImmutable.size() != 0)
			dataFromFileImmutable.clear();
		if (xDataFromPort.size() != 0)
			xDataFromPort.clear();
		if (xDataFromFile.size() != 0)
			xDataFromFile.clear();
	}
}

