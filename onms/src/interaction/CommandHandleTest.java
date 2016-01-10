package interaction;

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
        cmd.put("command", Cmds.FUNCTION);
        cmd.put("module", "MOD1");
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
        cmd.put("command", Cmds.IDN);
//        HashMap result;
        HashMap result = commandHandle.commonSocketInterface(cmd);
        System.out.println(result.get("idn").toString());
        assertNotEquals(-1, result.get("idn").toString().indexOf("JDSU"));
        cmd.clear();
        result.clear();

        cmd.put("command", Cmds.MEASDEFAULT);
        result = commandHandle.commonSocketInterface(cmd);
        String[] expects = {"AUTO", "MANUAL"};
        assertArrayEquals(expects, (String[]) result.get("configuration"));
        cmd.clear();
        result.clear();

        cmd.put("command", Cmds.MEASMANUAL);
        cmd.put("module", "MOD1");
        cmd.put("function", "\"SM-OTDR\"");
        cmd.put("switch","0");
        cmd.put("otu_out", "01");
        cmd.put("configuration", "MANual");
        cmd.put("pulse", "1000");
        cmd.put("range", "20");
        cmd.put("time", "15");
        //cmd.put("index", "1.46500");
        cmd.put("laser", "1650");
        cmd.put("resolution", "64");
        result = commandHandle.commonSocketInterface(cmd);
        System.out.println(result.get("status"));
        assertNotEquals(-1, result.get("status").toString().indexOf("WAITING"));
//        assert("IN_PROGRESS".equals(result.get("status")) || "WAITING".equals(result.get("status")));
        cmd.clear();
        result.clear();
    }
}