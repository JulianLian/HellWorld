package interaction;

import communation.IDataGetter;
import communation.Protocol;
import main.Md711MainFrame;

import java.util.HashMap;

/**
 * Created by Julian on 2016/1/11.
 */
public class OTDRTraceGetter implements IDataGetter {
    private Md711MainFrame mainFrame;

    public OTDRTraceGetter(Md711MainFrame mainFrame)
    {
        super();
        this.mainFrame = mainFrame;
    }

    @Override
    public boolean startFetchData() {
        HashMap cmdMocker = new HashMap<>();

        cmdMocker.put("command", Cmds.MEAS_MANUAL);
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

        OTDRTrace trace = getOTDRTrace();
        if (trace != null && mainFrame != null) {
            mainFrame.getGraph().showDataPoint(trace.getDoubleDataPoints());

            System.out.println("Key events:");
            for (String s : trace.KeyEvents) {
                System.out.println(s);
            }
        }

        return true;
    }

    @Override
    public boolean stopFetchData() {
        return true;
    }

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
        String[] keyEvents = new String[keyEventSize];
        trace.KeyEventSize = keyEventSize;

        for (int i = 1; i <= keyEventSize; i++) {
            HashMap newCmd = new HashMap();
            newCmd.put(Cmds.CMD, Cmds.TABLE_LINE);
            newCmd.put(Cmds.TABLE_LINE_NUM, Integer.toString(i));
            rcvBuff = commandHandle.builtInCommandWithParam(newCmd);
            keyEvents[i-1] = new String(rcvBuff);
        }
        trace.KeyEvents = keyEvents;

        return trace;
    }
}
