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
                List<String> ranges = getAvailableMeasureParams(Protocol.RESOLUTION, selectedItems);

                paramAvailable.put(Protocol.RESOLUTION, ranges);
            }else if (selectedItems.get(Protocol.PULSE_WIDTH) != null) {
                paramAvailable.put(Protocol.RANGE, getAvailableMeasureParams(Protocol.RANGE, selectedItems));
                paramAvailable.remove(Protocol.RESOLUTION);
            }

            System.out.println("<Param Retrun>");
            for (String paramKey : paramAvailable.keySet()) {
                for (String ps : paramAvailable.get(paramKey)) {
                    System.out.println(paramKey+": "+ps);
                }
            }

            return paramAvailable;
        }

        return null;
    }

    private Map<String, List<String>> getDefaultMeasureParams() {
        Map<String, List<String >> measureDefaultArgs = new HashMap();

        String[] module = {"MOD1:8118RLR65"};
        measureDefaultArgs.put(Protocol.MODULE, Arrays.asList(module));

        String[] function = {"SM-OTDR"};
        measureDefaultArgs.put(Protocol.FUNCTION, Arrays.asList(function));

//        toList.clear();
//        toList.add("0");
//        measureDefaultArgs.put("OTU", "0");
//        measureDefaultArgs.put("nb_otau", "1");
//        measureDefaultArgs.put("switch", "0");

        String[] otu_in_options = {"01"};
        measureDefaultArgs.put(Protocol.OTU_IN, Arrays.asList(otu_in_options));

        String[] otu_out_options = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        measureDefaultArgs.put(Protocol.OTU_OUT, Arrays.asList(otu_out_options));

//        measureDefaultArgs.put("otu_used", "1");  // the checkbox

        String[] configuration_options = {"AUTO", "MANUAL"};
        measureDefaultArgs.put(Protocol.ACQUISITION_SETTINT, Arrays.asList(configuration_options));

        String[] laser = {"1650 nm"};
        measureDefaultArgs.put(Protocol.WAVE_LENGTH, Arrays.asList(laser));

        String[] pulse_options = {"3","30","100","300","1000","3000","10000","20000"};
        measureDefaultArgs.put(Protocol.PULSE_WIDTH, Arrays.asList(pulse_options));

        String[] range_options = {"2","5","10","20","40"};
        measureDefaultArgs.put(Protocol.RANGE, Arrays.asList(range_options));

        String[] resolution_options = {"0","4","8","16","32","64"};
        measureDefaultArgs.put(Protocol.RESOLUTION, Arrays.asList(resolution_options));

        String[] acq_min = {"0"};
        measureDefaultArgs.put(Protocol.ACQUISITION_TIME_MINUTES, Arrays.asList(acq_min));

        String[] acq_sec = {"20"};
        measureDefaultArgs.put(Protocol.ACQUISITION_TIME_SECONDS, Arrays.asList(acq_sec));

//        measureDefaultArgs.put("buffer_otau", "NOK");

        return measureDefaultArgs;
    }

    private List<String> getAvailableMeasureParams(String item, Map<String,String> selectedParams) {
        List<String> availableMeasureArgs = new ArrayList<>();

        CommandHandle commandHandle = new CommandHandle();
        HashMap cmdParam = new HashMap();

        String param = selectedParams.get(Protocol.MODULE);
        cmdParam.put(Cmds.MODULE, param.substring(0, param.indexOf(":")));
        cmdParam.put(Cmds.FUNCTION, "\""+selectedParams.get(Protocol.FUNCTION)+"\"");
        cmdParam.put(Cmds.LASER, selectedParams.get(Protocol.WAVE_LENGTH));

        if (item.equals(Protocol.RESOLUTION)) {
            cmdParam.put(Cmds.CMD, Cmds.RESOLUTION);
            cmdParam.put(Cmds.PULSE, "\""+selectedParams.get(Protocol.PULSE_WIDTH)+"\"");
            cmdParam.put(Cmds.RANGE, "\""+selectedParams.get(Protocol.RANGE)+"\"");
        } else if (item.equals(Protocol.RANGE)) {
            cmdParam.put(Cmds.CMD, Cmds.RANGE);
            cmdParam.put(Cmds.PULSE, "\""+selectedParams.get(Protocol.PULSE_WIDTH)+"\"");
        }

        String rtnVal = (String) commandHandle.commonSocketInterface(cmdParam).get(cmdParam.get(Cmds.CMD));
        System.out.println("rtnVal:"+rtnVal);
        String[] parts = rtnVal.split(",");
        for (String part : parts) {
            availableMeasureArgs.add(part.substring(1,part.length()-1));
        }

        return availableMeasureArgs;
    }
}
