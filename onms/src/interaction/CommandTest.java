package interaction;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Julian on 2015/12/27.
 */
public class CommandTest {

    @Test
    public void testConvertToCmdline() throws Exception {
        Command command = new Command();
        HashMap<String, String> cmd = new HashMap<String,String>();
        cmd.put("command", "FUNCTION");
        cmd.put("module", "MOD1");
        String cmdLine = command.convertToCmdline(cmd);
        assertEquals("OTU:MODUle:CALFUNC:LIST? MOD1", cmdLine);
    }

    @Test
    public void testGetFunctionPort() throws Exception {
        Command command = new Command();
        assertEquals( 8003, command.getFunctionPort("*REM"));
        assertEquals( 8003, command.getFunctionPort("*idn?"));
        assertEquals( 8002, command.getFunctionPort("CURve:BUFfer?"));
    }

    @Test
    public void testSendAndEcho() throws Exception {
        Command command = new Command();
        assertNotEquals(-1, new String(command.sendAndEcho("OTU:NAMe?")).indexOf("TMS-OTU"));
        assertNotEquals(-1, new String(command.sendAndEcho("*idn?")).indexOf("JDSU,OTU 8000"));

        byte[] rcvBytes = command.sendAndEcho("CURve:Buffer?");
        ParseCurveBuffer parser = new ParseCurveBuffer();
        assertEquals('#', rcvBytes[0]);
        parser.parseDataPointsHead(rcvBytes);
        assertEquals(rcvBytes.length, parser.sizeInByte + parser.numOfChar + 3); // should plus 2
    }

    @Test
    public void testCommonSocketInterface() {
        Command command = new Command();
        HashMap cmd = new HashMap();
        cmd.put("command", "*idn?");
//        HashMap result;
        HashMap result = command.commonSocketInterface(cmd);
        System.out.println(result.get("idn").toString());
        assertNotEquals(-1, result.get("idn").toString().indexOf("JDSU"));
        cmd.clear();
        result.clear();

        cmd.put("command", "measdefault");
        result = command.commonSocketInterface(cmd);
        String[] expects = {"AUTO", "MANUAL"};
        assertArrayEquals(expects, (String[]) result.get("configuration"));
        cmd.clear();
        result.clear();

        cmd.put("command", "measManual");
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
        result = command.commonSocketInterface(cmd);
        System.out.println(result.get("status"));
        assertNotEquals(-1, result.get("status").toString().indexOf("WAITING"));
//        assert("IN_PROGRESS".equals(result.get("status")) || "WAITING".equals(result.get("status")));
        cmd.clear();
        result.clear();
    }
}