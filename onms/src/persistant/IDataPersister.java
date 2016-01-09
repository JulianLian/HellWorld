package persistant;

import java.awt.Color;
import java.util.List;

public interface IDataPersister
{
	public List<Double> getCashedXData();
	public List<Double> getCashedYData();
	public List<Double> getYData();
	public void setRepaintForNewDataComing(boolean isNewDataComing);
	public int getStep();
	public Color getPresentColor();
}
