package interaction;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Julian on 2015/12/27.
 */
public class CommandTest {

    @Test
    public void testConvertToCmdline() throws Exception {
        Command command = new Command();
        HashMap<String, String> cmd = new HashMap<>();
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
//        assertEquals( "TMS-OTU", new String(command.sendAndEcho("OTU:NAMe?")));
//        assertEquals( "JDSU,OTU 8000,1275,OTU(OEM),V1.64", new String(command.sendAndEcho("*idn?")));
        System.out.println(new String(command.sendAndEcho("*idn?")));
        System.out.println(new String(command.sendAndEcho("OTU:NAMe?")));
    }

    @Test
    public void testParseReceiveBuffer() {

    }
}