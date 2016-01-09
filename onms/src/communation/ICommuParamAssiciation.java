package communation;

import java.util.List;
import java.util.Map;

public interface ICommuParamAssiciation
{
	Map<String, List<String>> getPermitItemWhenSelect(Map<String, String> selectedItems);
}
