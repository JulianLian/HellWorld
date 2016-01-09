package persistant;

import java.awt.Color;
import java.util.List;

public class PortDataPersister implements IDataPersister
{
		private static PortDataPersister INS;
		public static PortDataPersister getInstance()
		{
			if (INS == null)
			{
				INS = new PortDataPersister();
			}
			return INS;
		}
		
		private PortDataPersister()
		{
			
		}

		@Override
		public List<Double> getYData()
		{
			return InventoryData.getDataFromPortImmutable();
		}

		@Override
		public void setRepaintForNewDataComing(boolean isNewDataComing)
		{
			WindowControlEnv.setRepaintForPortInfoCome(false);
		}

		@Override
		public Color getPresentColor()
		{
			return Color.RED;
		}

		@Override
		public int getStep()
		{
			return WindowControlEnv.getStepValForPortData();
		}

		@Override
		public List<Double> getCashedXData()
		{
			return InventoryData.getXDataFromPort();
		}

		@Override
		public List<Double> getCashedYData()
		{
			return InventoryData.getCanTransformedDataFromPort();
		}
}
