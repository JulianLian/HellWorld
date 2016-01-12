package interaction;

import communation.Protocol;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Julian on 2015/12/27.
 */
public class CommandHandleTest {

    @Test
    public void testConvertToCmdline() throws Exception {
        CommandHandle commandHandle = new CommandHandle();
        HashMap<String, String> cmd = new HashMap<String,String>();
        cmd.put(Cmds.CMD, Cmds.FUNCTION);
        cmd.put(Cmds.MODULE, "MOD1");
        String cmdLine = commandHandle.convertToCmdline(cmd);
        assertEquals("OTU:MODUle:CALFUNC:LIST? MOD1", cmdLine);
    }

    @Test
    public void testGetFunctionPort() throws Exception {
        CommandHandle commandHandle = new CommandHandle();
        assertEquals( 8003, commandHandle.getFunctionPort("*REM"));
        assertEquals( 8003, commandHandle.getFunctionPort("*idn?"));
        assertEquals( 8002, commandHandle.getFunctionPort("CURve:BUFfer?"));
    }

    @Test
    public void testSendAndEcho() throws Exception {
        CommandHandle commandHandle = new CommandHandle();
        assertNotEquals(-1, new String(commandHandle.sendAndEcho("OTU:NAMe?")).indexOf("TMS-OTU"));
        assertNotEquals(-1, new String(commandHandle.sendAndEcho("*idn?")).indexOf("JDSU,OTU 8000"));

        byte[] rcvBytes = commandHandle.sendAndEcho("CURve:Buffer?");
        ParseCurveBuffer parser = new ParseCurveBuffer();
        assertEquals('#', rcvBytes[0]);
        parser.parseDataPointsHead(rcvBytes);
        assertEquals(rcvBytes.length, parser.sizeInByte + parser.numOfChar + 3); // should plus 2
    }

    @Test
    public void testCommonSocketInterface() {
        CommandHandle commandHandle = new CommandHandle();
        HashMap cmd = new HashMap();
        cmd.put(Cmds.CMD, Cmds.IDN);
//        HashMap result;
        HashMap result = commandHandle.commonSocketInterface(cmd);
        System.out.println(result.get(Cmds.IDN).toString());
        assertNotEquals(-1, result.get(Cmds.IDN).toString().indexOf("JDSU"));
        cmd.clear();
        result.clear();

        cmd.put(Cmds.CMD, Cmds.MEAS_DEFAULT);
        result = commandHandle.commonSocketInterface(cmd);
        String[] expects = {"AUTO", "MANUAL"};
        assertArrayEquals(expects, (String[]) result.get(Protocol.MANU_CONFIG));
        cmd.clear();
        result.clear();

        cmd.put(Cmds.CMD, Cmds.MEAS_MANUAL);
        cmd.put(Protocol.MODULE, "MOD1");
        cmd.put(Protocol.FUNCTION, "\"SM-OTDR\"");
        cmd.put("switch","0");
        cmd.put(Protocol.OTU_OUT, "01");
        cmd.put(Protocol.MANU_CONFIG, "MANual");
        cmd.put(Protocol.PULSE_WIDTH, "1000");
        cmd.put(Protocol.RANGE, "20");
        cmd.put(Protocol.ACQUISITION_TIME, "15");
        //cmd.put("index", "1.46500");
        cmd.put(Protocol.WAVE_LENGTH, "1650");
        cmd.put(Protocol.RESOLUTION, "64");
        result = commandHandle.commonSocketInterface(cmd);

        assertNotEquals(-1, result.get(Cmds.MEAS_STATUS).toString().indexOf("WAITING"));
//        assert("IN_PROGRESS".equals(result.get("status")) || "WAITING".equals(result.get("status")));
        cmd.clear();
        result.clear();
    }
}