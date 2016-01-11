package interaction;

import communation.IDataGetter;
import communation.Protocol;

import java.util.HashMap;

/**
 * Created by Julian on 2016/1/11.
 */
public class OTDRTraceGetter implements IDataGetter {
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

        return true;
    }

    @Override
    public boolean stopFetchData() {
        return true;
    }

    OTDRTrace getOTDRTrace() {
        CommandHandle commandHandle = new CommandHandle();
        OTDRTrace trace = new OTDRTrace();

        HashMap newCmd = new HashMap();
        newCmd.put("command", Cmds.MEAS_STATUS);
        byte[] rcvBuff = commandHandle.builtInCommand(newCmd);
        if (!(new String(rcvBuff)).contains("AVAILABLE")) {
            System.out.println("STATUS: "+new String(rcvBuff));
            System.out.println("Trace not available");
            return null;
        }

        newCmd.clear();
        newCmd.put("command", Cmds.CURVE_XOFFSET);
        rcvBuff = commandHandle.builtInCommand(newCmd);
        trace.setXoffset(new String(rcvBuff));

        newCmd.clear();
        newCmd.put("command", Cmds.CURVE_XSCALE);
        rcvBuff = commandHandle.builtInCommand(newCmd);
        trace.setXscale( new String(rcvBuff));

        newCmd.clear();
        newCmd.put("command", Cmds.CURVE_XUNIT);
        rcvBuff = commandHandle.builtInCommand(newCmd);
        trace.setXunit(new String(rcvBuff));

        newCmd.clear();
        newCmd.put("command", Cmds.CURVE_YOFFSET);
        rcvBuff = commandHandle.builtInCommand(newCmd);
        trace.setYoffset( new String(rcvBuff));

        newCmd.clear();
        newCmd.put("command", Cmds.CURVE_YSCALE);
        rcvBuff = commandHandle.builtInCommand(newCmd);
        trace.setYscale( new String(rcvBuff));

        newCmd.clear();
        newCmd.put("command", Cmds.CURVE_YUNIT);
        rcvBuff = commandHandle.builtInCommand(newCmd);
        trace.setYunit( new String(rcvBuff));

        newCmd.clear();
        newCmd.put("command", Cmds.CURVE_BUFFER);
        rcvBuff = commandHandle.builtInCommand(newCmd);
        trace.DataPoints = rcvBuff;

        newCmd.clear();
        newCmd.put("command", Cmds.TABLE_SIZE);
        rcvBuff = commandHandle.builtInCommand(newCmd);
        int keyEventSize = rcvBuff[0] - '0';
        String[] keyEvents = new String[keyEventSize];
        trace.KeyEventSize = keyEventSize;

        for (int i = 1; i <= keyEventSize; i++) {
            newCmd.clear();
            newCmd.put("command", Cmds.TABLE_LINE);
            newCmd.put("line_num", Integer.toString(i));
            rcvBuff = commandHandle.builtInCommand(newCmd);
            keyEvents[i-1] = new String(rcvBuff);
        }
        trace.KeyEvents = keyEvents;

        return trace;
    }
}
