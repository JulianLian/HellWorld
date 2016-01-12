package interaction;

import communation.ICommuParamAssiciation;
import communation.Protocol;

import java.util.*;

/**
 * Created by Julian on 2016/1/9.
 */
public class MeasureParamsSetter implements ICommuParamAssiciation {
    @Override
    public Map<String, List<String>> getPermitItemWhenSelect(Map<String, String> selectedItems) {
        System.out.println("selected Items: " + selectedItems.size());
        for (String key : selectedItems.keySet())
            System.out.println(key + ": " + selectedItems.get(key));

        if (selectedItems != null) {
            Map<String, List<String>> paramAvailable = new HashMap<>();

            String item;
            for (String key : selectedItems.keySet()) {
                item = selectedItems.get(key);
                List<String> toList = new ArrayList<>();
                toList.add(item);
                paramAvailable.put(key, toList);
            }

            if (selectedItems.get(Protocol.RESOLUTION) != null) {

            }else if (selectedItems.get(Protocol.RANGE) != null) {
                CommandHandle commandHandle = new CommandHandle();
                HashMap cmdParam = new HashMap();
                cmdParam.putAll(selectedItems);
                cmdParam.put("commandHandle", Protocol.RANGE);
                HashMap<String, List<String>> resolutionOptions = commandHandle.commonSocketInterface(cmdParam);

                paramAvailable.put(Protocol.RESOLUTION, resolutionOptions.get(Protocol.RESOLUTION));
            }else if (selectedItems.get(Protocol.PULSE_WIDTH) != null) {
                CommandHandle commandHandle = new CommandHandle();
                HashMap cmdParam = new HashMap();
                cmdParam.putAll(selectedItems);
                cmdParam.put("commandHandle", Protocol.PULSE_WIDTH);
                HashMap<String, List<String>> rangeOptions = commandHandle.commonSocketInterface(cmdParam);

                paramAvailable.put(Protocol.RANGE, rangeOptions.get(Protocol.RANGE));
                paramAvailable.remove(Protocol.RESOLUTION);
            }

            return paramAvailable;
        }

        return null;
    }

}
