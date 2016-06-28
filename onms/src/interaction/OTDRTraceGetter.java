package interaction;

import communation.IDataGetter;
import communation.Protocol;
import gr196.GR196;

import javax.swing.*;
import java.io.IOException;
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

        HashMap<String, String> cmdGetter = new HashMap<>();

        cmdGetter.put(Cmds.CMD, Cmds.MEAS_MANUAL);

        String param = measureParams.get(Protocol.MODULE);
        cmdGetter.put(Cmds.MODULE, param.substring(0, param.indexOf(":")));

        cmdGetter.put(Cmds.OTU_OUT, measureParams.get(Protocol.OTU_OUT));

        param = measureParams.get(Protocol.ACQUISITION_SETTINT);
        cmdGetter.put(Cmds.MANU_CONFIG, param.equals(Protocol.MANU_CONFIG) ? "MAN" : "AUTO");

        String[] parts = measureParams.get(Protocol.PULSE_WIDTH).split(" ");
        cmdGetter.put(Cmds.PULSE, (parts[1].equals("ns")) ? parts[0] : parts[0] + "000");

        param = measureParams.get(Protocol.RANGE);
        cmdGetter.put(Cmds.RANGE, param.substring(0, param.indexOf(" ")));

        int acqSeconds = 60 * Integer.parseInt(measureParams.get(Protocol.ACQUISITION_TIME_MINUTES)) +
                Integer.parseInt(measureParams.get(Protocol.ACQUISITION_TIME_SECONDS));
        cmdGetter.put(Cmds.ACQ_TIME, String.valueOf(acqSeconds));

        param = measureParams.get(Protocol.WAVE_LENGTH);
        cmdGetter.put(Cmds.LASER, param.substring(0, param.indexOf(" ")));

        param = measureParams.get(Protocol.RESOLUTION);
        cmdGetter.put(Cmds.RESOLUTION, param.equals("Auto") ? "0" : param);

        cmdGetter.put(Cmds.FUNCTION, "\"" + measureParams.get(Protocol.FUNCTION) + "\"");


        CommandHandle cmdHandler = new CommandHandle();
        cmdHandler.commonSocketInterface(cmdGetter);

        String status = cmdHandler.builtInCommandWithoutParam(Cmds.MEAS_STATUS);
        int tryTimes = acqSeconds / 3 + 20;
        while ( (status.equals("WAITING") || status.equals("IN_PROGRESS")) &&
                tryTimes > 0) {
            try {
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            status = cmdHandler.builtInCommandWithoutParam(Cmds.MEAS_STATUS);
            tryTimes--;
        }

        if (!status.equals("AVAILABLE")) {
            JOptionPane.showMessageDialog(null,
                    "Add measurement failed. Please try again!",
                    "STATUS",JOptionPane.DEFAULT_OPTION);
            return null;
        }

        OTDRTrace trace =  getOTDRTrace();
        trace.setAcqTime(cmdGetter.get(Cmds.ACQ_TIME)+" s");
        trace.setFunction(measureParams.get(Protocol.FUNCTION));
        trace.setModule(measureParams.get(Protocol.MODULE));
        trace.setLaser(measureParams.get(Protocol.WAVE_LENGTH));
        trace.setPulsewidth(measureParams.get(Protocol.PULSE_WIDTH));
        trace.setRange(measureParams.get(Protocol.RANGE));
        trace.setResolution(measureParams.get(Protocol.RESOLUTION));
        trace.setNindex("1.465");

        return trace;
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
            JOptionPane.showMessageDialog(null,
                    "Trace not available. Please try later.",
                    "OTDR TRACE",JOptionPane.NO_OPTION);
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

        trace.KeyEventSize = keyEventSize;

        for (int i = 1; i <= keyEventSize; i++) {
            HashMap newCmd = new HashMap();
            newCmd.put(Cmds.CMD, Cmds.TABLE_LINE);
            newCmd.put(Cmds.TABLE_LINE_NUM, Integer.toString(i));
            rcvBuff = commandHandle.builtInCommandWithParam(newCmd);

            trace.KeyEvents.add((new String(rcvBuff)).replaceAll("\\n|\\r", " "));
        }

        trace.writeDataPointsToFile();

        GR196 gr196 = new GR196();

        gr196.setGenBlock(commandHandle.queryCommandWithoutParam(Cmds.FSETUP_CABLEID),
                commandHandle.queryCommandWithoutParam(Cmds.FSETUP_FIBERID),
                commandHandle.queryCommandWithoutParam(Cmds.FSETUP_OPERATOR),
                commandHandle.queryCommandWithoutParam(Cmds.FSETUP_COMMENT));
        gr196.setSupBlock();
        gr196.setFxdBlock(trace.getMeasTime());
        gr196.setDataBlock((short)(trace.getDoubleYscale() * 1000000), trace.getShortDataPoints());
//        gr196.setDataBlock(trace.getDoubleYscale(), trace.getDoubleDataPoints());
        gr196.setEventBlock();

        try {
            gr196.saveToFile("data/GR196-test.sor");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trace;
    }

    @Override
    public List<Double> getWaveData(Map<String, String> permittedVal) {
        this.trace = measureOnDemand(permittedVal);
        if ( this.trace == null) {
            return null;
        }
        printTraceInfo();

        return trace.getDataPoints();
    }

    private void printTraceInfo() {
        System.out.println("Date       : "+trace.getMeasTime());
        System.out.println("Module     : "+trace.getModule());
        System.out.println("Function   : "+trace.getFunction());
        System.out.println("Laser      : "+trace.getLaser());
        System.out.println("Pulse      : "+trace.getPulsewidth());
        System.out.println("Acq time   : "+trace.getAcqTime());
        System.out.println("Range      : "+trace.getRange());
        System.out.println("Resolution : "+trace.getResolution());
        System.out.println("Index      : "+trace.getNindex());
        System.out.println("Xoffset    : "+trace.getDoubleXoffset());
        System.out.println("Xunit      : "+trace.getXunit());
        System.out.println("Yscale     : "+trace.DoubleYscale);
        System.out.println("Yoffset    : "+trace.DoubleYoffset);
        System.out.println("Yunit      : "+trace.getYunit());
        System.out.println("Data Number: "+trace.DataPoints.length);
        System.out.println("Event Num  : "+trace.getKeyEvents().size());
    }

    @Override
    public List<String> getEventData(Map<String, String> permittedVal) {
//        for (String event : trace.KeyEvents) {
//            System.out.println(event);
//        }
        return trace.getKeyEvents();
    }
}
