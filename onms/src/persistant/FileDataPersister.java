package persistant;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import datastruct.EventDataStruct;
import main.KeyPointPanel;

public class FileDataPersister implements IDataPersister
{
	private static FileDataPersister INS;
	private KeyPointPanel keyPointPanel;
	public static FileDataPersister getInstance (KeyPointPanel keyPointPanel)
	{
		if (INS == null)
		{
			INS = new FileDataPersister(keyPointPanel);
		}
		return INS;
	}

	private FileDataPersister(KeyPointPanel keyPointPanel)
	{
		this.keyPointPanel = keyPointPanel;
	}

	@Override
	public List<Double> getYData ()
	{
		return InventoryData.getDataFromFileImmutable();
	}

	@Override
	public void setRepaintForNewDataComing (boolean isNewDataComing)
	{
		WindowControlEnv.setRepaintForFileInfoCome(false);
	}

	@Override
	public Color getPresentColor ()
	{
		return Color.GREEN;
	}

	@Override
	public int getStep ()
	{
		return WindowControlEnv.getStepValForFileData();
	}

	@Override
	public List<Double> getCashedXData ()
	{
		return InventoryData.getXDataFromFile();
	}

	@Override
	public List<Double> getCashedYData ()
	{
		return InventoryData.getCanTransformedDataFromFile();
	}
	
	@Override
	public Map<String, Double> eventDataIDPositionMap ()
	{
		return keyPointPanel.getEventDataIDPositionMap();
	}
}
