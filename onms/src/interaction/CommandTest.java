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
        cmd.put("command", "idn?");
        HashMap result = command.commonSocketInterface(cmd);
        System.out.println(result.get("idn").toString());
        assertNotEquals(-1, result.get("idn").toString().indexOf("JDSU"));
    }
}