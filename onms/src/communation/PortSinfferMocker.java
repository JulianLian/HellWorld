package communation;

import filedatagetter.PersistRawDataReader;
import main.Md711MainFrame;
import persistant.InventoryData;
import persistant.WindowControlEnv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PortSinfferMocker implements IDataGetter
{
		private Md711MainFrame mainFrame;
		
		public PortSinfferMocker(Md711MainFrame mainFrame)
			{
				super();
				this.mainFrame = mainFrame;
			}

			@Override
		public boolean startFetchData()
		{
//				testdata2();
				testGetRawDataFromFile();
//				testdata5(); //这个测试用例帮我测试出计算y值时候要取所有点最大绝对值，而不是最大值
				return true;
		}		

		private void testGetRawDataFromFile ()
		{
			List<Double> data = PersistRawDataReader
					.getWaveDoubleData("C:\\Users\\Julian\\Documents\\MOP\\ONMS\\lian\\test log\\dataPoints.txt");
			mainFrame.getGraph().showPortData(data);
		}

		@Override
		public boolean stopFetchData()
		{			
			return true;
		}		

		private void testdata1()
		{
			List<Double> datas = Arrays.asList(1.0, 200.0, 200.0, 1.0, 400.0);
			for (Double tempData : datas)
			{
				InventoryData.getCanTransformedDataFromPort().add(tempData);
				InventoryData.getDataFromPortImmutable().add(tempData);
			}
			WindowControlEnv.setRepaintForPortInfoCome(true);
			showAfterRecveEnoughData();
		}
		
		private void testdata5()
		{
			List<Double> datas = Arrays.asList(
					-400.,200.);
//			List<Double> datas = Arrays.asList(
//					-400., -400.,200.,200.);

			for (Double tempData : datas)
			{
				InventoryData.getCanTransformedDataFromPort().add(tempData);
				InventoryData.getDataFromPortImmutable().add(tempData);
			}
			WindowControlEnv.setRepaintForPortInfoCome(true);
			showAfterRecveEnoughData();
		}
		private void testdata4()
		{
			List<Double> datas = Arrays.asList(
					-16.013219, -14.715209, -13.267259, -11.995709, -10.882919, -9.911249, -9.060119, -8.308948999999998, -7.645979, -7.065328999999999, -6.728699, -6.424409, -6.146579, -5.893738999999999, -5.661479, -5.452738999999999, -5.111699, -2.731769, 1.2636909999999997, 4.603531, 7.2245409999999985, 8.357911000000001, 9.192871, 9.741181000000001, 10.110150999999998, 10.460011000000002, 10.761361, 11.014201, 11.228821, 11.414041000000001, 11.572800999999998, 11.708041000000001, 11.828581);
			for (Double tempData : datas)
			{
				InventoryData.getCanTransformedDataFromPort().add(tempData);
				InventoryData.getDataFromPortImmutable().add(tempData);
			}
			WindowControlEnv.setRepaintForPortInfoCome(true);
			showAfterRecveEnoughData();
		}

		private void testdata3()
		{
			List<Double> data = Arrays.asList(0., 50.0, 0.0,50.0);
			mainFrame.getGraph().showPortData(data);
		}

		private void testdata2()
		{
			Random random = new Random(1000);
			List<Double> data  = new ArrayList<Double>();
			for (int index = 0; index < 50; index++)
			{
				int maxVal = 999;
				int count = 1;
				int val = random.nextInt() % maxVal;
				data.add(Double.valueOf(val));
			}
			mainFrame.getGraph().showPortData(data);
//			Random random = new Random(1000);
//			
//			for (int index = 0; index < 50; index++)
//			{
//				int maxVal = 999;
//				int count = 1;
//				int val = random.nextInt() % maxVal;
//				InventoryData.getCanTransformedDataFromPort().add(Double.valueOf(val));
//				InventoryData.getDataFromPortImmutable().add(Double.valueOf(val));
//			}
//			WindowControlEnv.setRepaintForPortInfoCome(true);
//			showAfterRecveEnoughData();
		}
		
		public void showAfterRecveEnoughData()
		{
			mainFrame.showGraph();
		}

}
