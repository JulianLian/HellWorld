package communation;

import java.util.List;
import java.util.Map;

public interface IDataGetter
{
	public List <Double> getWaveData(Map<String, String> permittedVal);
	public List <String> getEventData(Map<String, String> permittedVal);
}
