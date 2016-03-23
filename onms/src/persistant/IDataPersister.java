package persistant;

import java.awt.Color;
import java.util.List;
import java.util.Map;

public interface IDataPersister
{
	public List<Double> getCashedXData();
	public List<Double> getCashedYData();
	public Map<String, Double> eventDataIDPositionMap();
	public List<Double> getYData();
	public void setRepaintForNewDataComing(boolean isNewDataComing);
	public int getStep();
	public Color getPresentColor();
}
