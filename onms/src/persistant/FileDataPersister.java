package persistant;

import java.awt.Color;
import java.util.List;

public class FileDataPersister implements IDataPersister
{
	private static FileDataPersister INS;

	public static FileDataPersister getInstance ()
	{
		if (INS == null)
		{
			INS = new FileDataPersister();
		}
		return INS;
	}

	private FileDataPersister()
	{

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
}
