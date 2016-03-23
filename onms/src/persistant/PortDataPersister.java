package persistant;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import main.KeyPointPanel;

public class PortDataPersister implements IDataPersister
{
	private static PortDataPersister INS;
	private KeyPointPanel keyPointPanel;
	
	public static PortDataPersister getInstance (KeyPointPanel keyPointPanel)
	{
		if (INS == null)
		{
			INS = new PortDataPersister(keyPointPanel);
		}
		return INS;
	}
	
	private PortDataPersister(KeyPointPanel keyPointPanel)
	{
		this.keyPointPanel = keyPointPanel;
	}
	
	@Override
	public List<Double> getYData ()
	{
		return InventoryData.getDataFromPortImmutable();
	}
	
	@Override
	public void setRepaintForNewDataComing (boolean isNewDataComing)
	{
		WindowControlEnv.setRepaintForPortInfoCome(false);
	}
	
	@Override
	public Color getPresentColor ()
	{
		return Color.RED;
	}
	
	@Override
	public int getStep ()
	{
		return WindowControlEnv.getStepValForPortData();
	}
	
	@Override
	public List<Double> getCashedXData ()
	{
		return InventoryData.getXDataFromPort();
	}
	
	@Override
	public List<Double> getCashedYData ()
	{
		return InventoryData.getCanTransformedDataFromPort();
	}
	
	@Override
	public Map<String, Double> eventDataIDPositionMap ()
	{
		return keyPointPanel.getEventDataIDPositionMap();
	}
}
