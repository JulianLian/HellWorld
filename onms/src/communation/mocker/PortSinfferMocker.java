package communation.mocker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import communation.IDataGetter;
import filedatagetter.PersistRawDataReader;
import main.Md711MainFrame;
import persistant.InventoryData;
import persistant.WindowControlEnv;

public class PortSinfferMocker implements IDataGetter
{
	public PortSinfferMocker()
	{
		super();
	}

//	@Override
//	public boolean startFetchData ()
//	{
//		// testdata2();
//		testGetRawDataFromFile();
//		// testdata5(); //这个测试用例帮我测试出计算y值时候要取所有点最大绝对值，而不是最大值
//		return true;
//	}

	private List<Double> testGetRawDataFromFile ()
	{
		return PersistRawDataReader.getWaveDoubleData("C:\\Users\\Julian\\IdeaProjects\\HellWorld\\curveTrace.txt");
//		List<Double> data = PersistRawDataReader.getWaveDoubleData("D:\\MD711测试数据\\dataPoints (1).txt");
//		mainFrame.getGraph().showPortData(data);
	}

	private List<Double> testdata1 ()
	{
		return Arrays.asList(1.0, 200.0, 200.0, 1.0, 400.0,300.0,100.0,200.0);
//		return Arrays.asList(1.0, 200.0, 200.0, 1.0, 400.0);
//		List<Double> datas = Arrays.asList(1.0, 200.0, 200.0, 1.0, 400.0);
//		for (Double tempData : datas)
//		{
//			InventoryData.getCanTransformedDataFromPort().add(tempData);
//			InventoryData.getDataFromPortImmutable().add(tempData);
//		}
//		WindowControlEnv.setRepaintForPortInfoCome(true);
//		showAfterRecveEnoughData();
	}

	private List<Double> testdata5 ()
	{
		return Arrays.asList(-400., 200.);
//		List<Double> datas = Arrays.asList(-400., 200.);
//		for (Double tempData : datas)
//		{
//			InventoryData.getCanTransformedDataFromPort().add(tempData);
//			InventoryData.getDataFromPortImmutable().add(tempData);
//		}
//		WindowControlEnv.setRepaintForPortInfoCome(true);
//		showAfterRecveEnoughData();
	}

	private List<Double> testdata4 ()
	{
		List<Double> data = Arrays.asList(-16.013219, -14.715209, -13.267259, -11.995709, -10.882919,
				-9.911249, -9.060119, -8.308948999999998, -7.645979, -7.065328999999999, -6.728699,
				-6.424409, -6.146579, -5.893738999999999, -5.661479, -5.452738999999999, -5.111699,
				-2.731769, 1.2636909999999997, 4.603531, 7.2245409999999985, 8.357911000000001,
				9.192871, 9.741181000000001, 10.110150999999998, 10.460011000000002, 10.761361,
				11.014201, 11.228821, 11.414041000000001, 11.572800999999998, 11.708041000000001,
				11.828581);
		return data;
//		for (Double tempData : datas)
//		{
//			InventoryData.getCanTransformedDataFromPort().add(tempData);
//			InventoryData.getDataFromPortImmutable().add(tempData);
//		}
//		WindowControlEnv.setRepaintForPortInfoCome(true);
//		showAfterRecveEnoughData();
	}

	private List<Double> testdata3 ()
	{
		return  Arrays.asList(0., 50.0, 0.0, 50.0);
	}

	private List<Double> testdata2 ()
	{
		Random random = new Random(1000);
		List<Double> data = new ArrayList<Double>();
		for (int index = 0; index < 50; index++)
		{
			int maxVal = 999;
			int val = random.nextInt() % maxVal;
			data.add(Double.valueOf(val));
		}
		return data;
	}

//	private void showAfterRecveEnoughData ()
//	{
//		mainFrame.showGraph();
//	}

	@Override
	public List<Double> getWaveData (Map<String, String> permittedVal)
	{
		return  PersistRawDataReader.getWaveDoubleData("C:\\Users\\Julian\\IdeaProjects\\HellWorld\\curveTrace.txt");
	}

	@Override
	public List<String> getEventData (Map<String, String> permittedVal)
	{
//		List<String>  eventData = new ArrayList<String>();
//		//目前只显示7列
////		eventData.add("  1,,     0.00,~ 24.482,~>-25.75,,     0.00, ");
////		eventData.add("  2,,     0.01,~ 28.482,~>-20.75,,     0.05, ");
////		eventData.add("7,Reflection,  5688.89,  1.266, -50.86,  0.226,  1678.02,  1.440");
////		eventData.add("  3,,     0.00,~ 24.482,~>-25.75,,     0.00, ");
////		eventData.add("  4,,     0.01,~ 28.482,~>-20.75,,     0.05, ");
////		eventData.add("5,Reflection,  5688.89,  1.266, -50.86,  0.226,  1678.02,  1.440");
////		eventData.add("  6,,     0.00,~ 24.482,~>-25.75,,     0.00, ");
////		eventData.add("  8,,     0.01,~ 28.482,~>-20.75,,     0.05, ");
////		eventData.add("9,Reflection,  5688.89,  1.266, -50.86,  0.226,  1678.02,  1.440");
////		eventData.add("  13,,     0.00,~ 24.482,~>-25.75,,     0.00, ");
////		eventData.add("  14,,     0.01,~ 28.482,~>-20.75,,     0.05, ");
////		eventData.add("15,Reflection,  5688.89,  1.266, -50.86,  0.226,  1678.02,  1.440");
////		eventData.add(" 16,,     0.00,~ 24.482,~>-25.75,,     0.00, ");
////		eventData.add("  18,,     0.01,~ 28.482,~>-20.75,,     0.05, ");
////		eventData.add("19,Reflection,  5688.89,  1.266, -50.86,  0.226,  1678.02,  1.440");
//		eventData.add("  1,,     0.00,~ 24.482,~>-25.75,,     0.00, ");
//		eventData.add("  2,,     500,~ 24.482,~>-25.75,,     0.00, ");
//		eventData.add("  3,,     1000,~ 24.482,~>-25.75,,     0.00, ");
//		eventData.add("  4,,     1500,~ 24.482,~>-25.75,,     0.00, ");
//		eventData.add("  5,,     2000,~ 24.482,~>-25.75,,     0.00, ");
//		
//		return eventData;
		return  PersistRawDataReader.getEventData("C:\\Users\\Julian\\IdeaProjects\\HellWorld\\KeyEvent.txt");
	}

}
