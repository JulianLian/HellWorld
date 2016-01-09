
package persistant;
/*
 * 
 * �����棬����׼���ѴӶ˿ڻ�ȡ��������
 * �� ���ļ��е�����������ȫ������������洢��
 * ԭʼ�������������������仯�����飬���������ֱ��ʾx,y
 * �����λ�ã�����ͼ����ʾʱ����Ҫ
 * 
 */

import java.util.ArrayList;
import java.util.List;

public class InventoryData
{
		// ˵��tempDataFromPort�Ǵ�ŴӶ˿ڻ�ȡ������
		// dataFromFile�Ǵ��ļ����ؽ���������
		private static List<Double>	dataFromPortCanTransformed	= new ArrayList<Double>();	// ���Ը��ĵ�
		private static List<Double>	dataFromPortImmutable		= new ArrayList<Double>();	// Ҫ��ŵ��ļ��е�,�������Ͳ���
		private static List<Double>	dataFromFileCanTransformed	= new ArrayList<Double>();
		private static List<Double>	dataFromFileImmutable		= new ArrayList<Double>();

		// ��������x���ϵ�λ��
		private static List<Double>	xDataFromPort	= new ArrayList<Double>();
		private static List<Double>	xDataFromFile	= new ArrayList<Double>();	

		// *****************************************
		// **********************************
		// ***************************************** �˿ڲ������ݴ���
		// *********************************
		// *****************************************
		// **********************************

		// *********************�˿����ݣ������Ըı�
		public static List<Double> getCanTransformedDataFromPort()
		{
			return dataFromPortCanTransformed;
		}

		public static void setCanTransformedDataFromPort(List<Double> c)
		{
			dataFromPortCanTransformed = c;
		}

		public static boolean hasDataToShow()
		{
			return getCanTransformedDataFromPort().size() != 0
					|| InventoryData.getCanTransformedDataFromFile().size() != 0;
		}

		// ************************�˿����ݣ��������Ըı�

		public static List<Double> getDataFromPortImmutable()
		{
			return dataFromPortImmutable;
		}

		public static void setDataFromPortImmutable(List<Double> c)
		{
			dataFromPortImmutable = c;
		}

		public static int getDataFromPortImmutableLength()
		{
			return getDataFromPortImmutable().size();
		}

		// ************************�˿����ݶ�Ӧ��x����
		public static List<Double> getXDataFromPort()
		{
			return xDataFromPort;
		}

		public static void setXDataFromPort(List<Double> c)
		{
			xDataFromPort = c;
		}

		public static int getXDataFromPortLength()
		{
			return getXDataFromPort().size();
		}

		// *****************************************
		// **********************************
		// ***************************************** �ļ��������ݴ���
		// *********************************
		// *****************************************
		// **********************************

		public static List<Double> getCanTransformedDataFromFile()
		{
			return dataFromFileCanTransformed;
		}

		public static void setCanTransformedDataFromFile(List<Double> c)
		{
			dataFromFileCanTransformed = c;
		}

		// public static int getCanTransformedDataFromFileLength()
		// {
		// return getCanTransformedDataFromFile().size();
		// }

		public static void setDataFromFileNeedPrint(List<Double> c)
		{
			dataFromFileImmutable = c;
		}

		public static int getDataFromFileImmutableLength()
		{
			return dataFromFileImmutable.size();
		}

		public static List<Double> getDataFromFileImmutable()
		{
			return dataFromFileImmutable;
		}

		// ************************�ļ����ݶ�Ӧ��x����
		public static List<Double> getXDataFromFile()
		{
			return xDataFromFile;
		}

		public static int getXDataFromFileLength()
		{
			return getXDataFromFile().size();
		}		

		// *************************************************
		// ***********************
		// ************************************************* ���������ݸ�λ
		// ***********************
		// *************************************************
		// ***********************
		public static void clearPersistData()
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
