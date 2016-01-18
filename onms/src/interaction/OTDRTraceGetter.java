package interaction;

import communation.IDataGetter;
import communation.Protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Julian on 2016/1/11.
 */
public class OTDRTraceGetter implements IDataGetter {

    static OTDRTrace trace;

    public OTDRTraceGetter()
    {
        super();
    }

    public OTDRTrace measureOnDemand(Map<String, String> measureParams) {

        HashMap<String, String> cmdMocker = new HashMap<>();

        cmdMocker.put(Cmds.CMD, Cmds.MEAS_MANUAL);

        String param = measureParams.get(Protocol.MODULE);
        cmdMocker.put(Cmds.MODULE, param.substring(0, param.indexOf(":")));

        cmdMocker.put(Cmds.OTU_OUT, measureParams.get(Protocol.OTU_OUT));

        param = measureParams.get(Protocol.ACQUISITION_SETTINT);
        cmdMocker.put(Cmds.MANU_CONFIG, param.equals(Protocol.MANU_CONFIG) ? "MAN" : "AUTO");

        String[] parts = measureParams.get(Protocol.PULSE_WIDTH).split(" ");
        cmdMocker.put(Cmds.PULSE, (parts[1].equals("ns") ) ? parts[0] : parts[0]+"000");

        param = measureParams.get(Protocol.RANGE);
        cmdMocker.put(Cmds.RANGE, param.substring(0, param.indexOf(" ")));

        cmdMocker.put(Cmds.ACQ_TIME,
                String.valueOf(60 * Integer.parseInt(measureParams.get(Protocol.ACQUISITION_TIME_MINUTES)) +
                        Integer.parseInt(measureParams.get(Protocol.ACQUISITION_TIME_SECONDS))));

        param = measureParams.get(Protocol.WAVE_LENGTH);
        cmdMocker.put(Cmds.LASER, param.substring(0, param.indexOf(" ")));

        param = measureParams.get(Protocol.RESOLUTION);
        cmdMocker.put(Cmds.RESOLUTION, param.equals("Auto") ? "0" : param);

        cmdMocker.put(Cmds.FUNCTION, "\""+measureParams.get(Protocol.FUNCTION)+"\"");


        CommandHandle cmdHandler = new CommandHandle();
        HashMap result = cmdHandler.commonSocketInterface(cmdMocker);

        System.out.println(result.get(Cmds.MEAS_STATUS));
        try {
            Thread.currentThread().sleep(Integer.parseInt(cmdMocker.get(Cmds.ACQ_TIME)) * 1000 + 25000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return getOTDRTrace();
    }

//    @Override
//    public boolean stopFetchData() {
//        return true;
//    }

    OTDRTrace getOTDRTrace() {
        CommandHandle commandHandle = new CommandHandle();
        OTDRTrace trace = new OTDRTrace();

        String rcvStr = commandHandle.builtInCommandWithoutParam(Cmds.MEAS_STATUS);
        if (!(rcvStr).contains("AVAILABLE")) {
            System.out.println("STATUS: "+rcvStr);
            System.out.println("Trace not available. Please try later.");
            return null;
        }

        trace.setDate(commandHandle.builtInCommandWithoutParam(Cmds.SYSTEM_DATE));

        trace.setTime(commandHandle.builtInCommandWithoutParam(Cmds.SYSTEM_TIME));

        trace.setXoffset( commandHandle.builtInCommandWithoutParam(Cmds.CURVE_XOFFSET));

        trace.setXscale( commandHandle.builtInCommandWithoutParam(Cmds.CURVE_XSCALE));

        trace.setXunit( commandHandle.builtInCommandWithoutParam(Cmds.CURVE_XUNIT));

        trace.setYoffset( commandHandle.builtInCommandWithoutParam(Cmds.CURVE_YOFFSET));

        trace.setYscale( commandHandle.builtInCommandWithoutParam(Cmds.CURVE_YSCALE));

        trace.setYunit( commandHandle.builtInCommandWithoutParam(Cmds.CURVE_YUNIT));

        byte[] rcvBuff = commandHandle.curveBufferCommand(Cmds.CURVE_BUFFER);
        trace.DataPoints = rcvBuff;

        int keyEventSize = Integer.parseInt( commandHandle.builtInCommandWithoutParam(Cmds.TABLE_SIZE));

        System.out.println("KeyEventSize:"+keyEventSize);
        trace.KeyEventSize = keyEventSize;

        for (int i = 1; i <= keyEventSize; i++) {
            HashMap newCmd = new HashMap();
            newCmd.put(Cmds.CMD, Cmds.TABLE_LINE);
            newCmd.put(Cmds.TABLE_LINE_NUM, Integer.toString(i));
            rcvBuff = commandHandle.builtInCommandWithParam(newCmd);

            trace.KeyEvents.add((new String(rcvBuff)).replaceAll("\\n|\\r", " "));
        }

        return trace;
    }

    @Override
    public List<Double> getWaveData(Map<String, String> permittedVal) {
        this.trace = measureOnDemand(permittedVal);
        if ( this.trace == null) {
            return null;
        }
        System.out.println("Date       : "+trace.Date+" "+trace.Time);
        System.out.println("Module     : "+trace.Module);
        System.out.println("Function   : "+trace.Function);
        System.out.println("Laser      : "+trace.Laser);
        System.out.println("Pulse      : "+trace.Pulsewidth);
        System.out.println("Acq time   : "+trace.AcqTime);
        System.out.println("Range      : "+trace.Range);
        System.out.println("Resolution : "+trace.Resolution);
        System.out.println("Index      : 1.465");
        System.out.println("Data Number: "+trace.DataPoints.length);
        System.out.println("Event Num  : "+trace.KeyEvents.size());
        return trace.getDataPoints();
    }

    @Override
    public List<String> getEventData(Map<String, String> permittedVal) {
        return trace.getKeyEvents();
    }
}
