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

//    @Override
//    public boolean startFetchData() {
    public OTDRTrace measureOnDemand(Map<String, String> measureParams) {
        HashMap cmdMocker = new HashMap<>();

        cmdMocker.put(Cmds.CMD, Cmds.MEAS_MANUAL);
        cmdMocker.put(Protocol.MODULE, "MOD1");
        cmdMocker.put(Protocol.OTU_OUT, "01");
        cmdMocker.put(Protocol.MANU_CONFIG, "MANual");
        cmdMocker.put(Protocol.PULSE_WIDTH, "1000");
        cmdMocker.put(Protocol.RANGE, "20");
        cmdMocker.put(Protocol.ACQUISITION_TIME, "15");
        cmdMocker.put(Protocol.WAVE_LENGTH, "1650");
        cmdMocker.put(Protocol.RESOLUTION, "64");
        cmdMocker.put(Protocol.FUNCTION, "\"SM-OTDR\"");

        CommandHandle cmdHandler = new CommandHandle();
        HashMap result = cmdHandler.commonSocketInterface(cmdMocker);

        System.out.println(result.get(Cmds.MEAS_STATUS));
        try {
            Thread.currentThread().sleep(30000);
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

        byte[] rcvBuff = commandHandle.builtInCommandWithoutParam(Cmds.MEAS_STATUS);
        if (!(new String(rcvBuff)).contains("AVAILABLE")) {
            System.out.println("STATUS: "+new String(rcvBuff));
            System.out.println("Trace not available. Please try later.");
            return null;
        }

        rcvBuff = commandHandle.builtInCommandWithoutParam(Cmds.SYSTEM_DATE);
        trace.setDate(new String(rcvBuff));

        rcvBuff = commandHandle.builtInCommandWithoutParam(Cmds.SYSTEM_TIME);
        trace.setTime(new String(rcvBuff));

        rcvBuff = commandHandle.builtInCommandWithoutParam(Cmds.CURVE_XOFFSET);
        trace.setXoffset(new String(rcvBuff));

        rcvBuff = commandHandle.builtInCommandWithoutParam(Cmds.CURVE_XSCALE);
        trace.setXscale( new String(rcvBuff));

        rcvBuff = commandHandle.builtInCommandWithoutParam(Cmds.CURVE_XUNIT);
        trace.setXunit(new String(rcvBuff));

        rcvBuff = commandHandle.builtInCommandWithoutParam(Cmds.CURVE_YOFFSET);
        trace.setYoffset( new String(rcvBuff));

        rcvBuff = commandHandle.builtInCommandWithoutParam(Cmds.CURVE_YSCALE);
        trace.setYscale( new String(rcvBuff));

        rcvBuff = commandHandle.builtInCommandWithoutParam(Cmds.CURVE_YUNIT);
        trace.setYunit( new String(rcvBuff));

        rcvBuff = commandHandle.builtInCommandWithoutParam(Cmds.CURVE_BUFFER);
        trace.DataPoints = rcvBuff;

        rcvBuff = commandHandle.builtInCommandWithoutParam(Cmds.TABLE_SIZE);
        int keyEventSize = rcvBuff[0] - '0';
        trace.KeyEventSize = keyEventSize;

        for (int i = 1; i <= keyEventSize; i++) {
            HashMap newCmd = new HashMap();
            newCmd.put(Cmds.CMD, Cmds.TABLE_LINE);
            newCmd.put(Cmds.TABLE_LINE_NUM, Integer.toString(i));
            rcvBuff = commandHandle.builtInCommandWithParam(newCmd);

            trace.KeyEvents.add(new String(rcvBuff));
        }

        return trace;
    }

    @Override
    public List<Double> getWaveData(Map<String, String> permittedVal) {
        this.trace = measureOnDemand(permittedVal);
        if ( this.trace == null) {
            return null;
        }

        return trace.getDataPoints();
    }

    @Override
    public List<String> getEventData(Map<String, String> permittedVal) {
        List<String> eventsList = trace.getKeyEvents();
        for (String s : eventsList) {
            System.out.println(s);
        }
        return trace.getKeyEvents();
    }
}
