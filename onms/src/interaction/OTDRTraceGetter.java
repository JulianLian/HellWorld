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
        System.out.println("module: " + measureParams.get(Protocol.MODULE));
        System.out.println("otu_out: " + measureParams.get(Protocol.OTU_OUT));
//        System.out.println("manual: " + measureParams.get(Protocol.MANU_CONFIG));
        System.out.println("manual: " + measureParams.get(Protocol.ACQUISITION_SETTINT));
        System.out.println("pulse: " + measureParams.get(Protocol.PULSE_WIDTH));
        System.out.println("range: " + measureParams.get(Protocol.RANGE));
        System.out.println("acq_time: " + measureParams.get(Protocol.ACQUISITION_TIME));
        System.out.println("acq_min: " + measureParams.get(Protocol.ACQUISITION_TIME_MINUTES));
        System.out.println("acq_sec: " + measureParams.get(Protocol.ACQUISITION_TIME_SECONDS));
        System.out.println("laser: " + measureParams.get(Protocol.WAVE_LENGTH));
        System.out.println("resolution: " + measureParams.get(Protocol.RESOLUTION));
        System.out.println("function: " + measureParams.get(Protocol.FUNCTION));
        HashMap cmdMocker = new HashMap<>();

        cmdMocker.put(Cmds.CMD, Cmds.MEAS_MANUAL);

        String param = measureParams.get(Protocol.MODULE);
        cmdMocker.put(Cmds.MODULE, param.substring(0, param.indexOf(":")));

        cmdMocker.put(Cmds.OTU_OUT, "01");

//        param = measureParams.get(Protocol.MANU_CONFIG);
        cmdMocker.put(Cmds.MANU_CONFIG, "MAN");

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
        cmdMocker.put(Cmds.RESOLUTION, param.equals("自动") ? "0" : param);

        cmdMocker.put(Cmds.FUNCTION, "\""+measureParams.get(Protocol.FUNCTION)+"\"");


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
