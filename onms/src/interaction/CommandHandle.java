package interaction;

import communation.Protocol;
import env.Environment;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;

/**
 * Created by Julian on 2015/12/21.
 */
public class CommandHandle {

    public CommandHandle() {    }

    String convertToCmdline(HashMap<String, String> cmd) {
        String cmdLine = cmd.get(Cmds.CMD);
        switch (cmd.get(Cmds.CMD)) {
            case Cmds.IDN:
                cmdLine = "*idn?";
                break;
            case Cmds.ERS:
                cmdLine = "*ers?";
                break;
            case Cmds.MEAS_DEFAULT:
                break;
            case Protocol.MODULE:
                // PC : OTU:MODUle:DETect?
                // RTU : MOD1: -1, ;MOD2: 5027HD,"SM-OTDR" : L1625,NONE,NONE,NONE
                cmdLine = "OTU:MODUle:DETect?";
                break;
            case Cmds.FUNCTION:
                // PC : OTU:MODUle:CALFUnctions:LIST? MOD2
                // RTU : "SM-OTDR"
                cmdLine = "OTU:MODUle:CALFUNC:LIST? " + cmd.get(Cmds.MODULE);
                break;
            case Cmds.SWITCH:
                // PC : otu:switch:detect:list?
                // RTU : 0
                cmdLine = "OTU:SWITCH:DETECT:LIST?";
                // PC : otu:switch:detect:desc? 0,INT
                // RTU : OTU, "SA201004","10.33.16.63:1400"
                break;
            case Cmds.OTU_OUT:
                break;
            case Cmds.AUTO_CONFIG:
                break;
            case Cmds.LASER:
                // PC : OTU:MODUle:CALLaser:LIST? MOD2, "SM-OTDR"
                // RTU : "1625 nm"5
                cmdLine = "OTU:MODUle:CALLaser:LIST? "+cmd.get(Cmds.MODULE)+","+cmd.get(Cmds.FUNCTION);
                break;
            case Cmds.PULSE:
                // PC : otu:module:calot:lpulse? mod2,"SM-OTDR"
                // RTU : "3 ns","30 ns","100 ns","300 ns","1 us","3 us","10 us","20 us"
                cmdLine = "OTU:MODUle:CALOT:LPULSE? "+cmd.get(Cmds.MODULE)+","+cmd.get(Cmds.FUNCTION);
                break;
            case Cmds.RANGE:
                // PC : otu:module:calot:lrange? mod2,"SM-OTDR","3 ns"
                // RTU : "5 km","10 km","20 km","40 km","80 km"
                cmdLine = "OTU:MODUle:CALOT:Lrange? "+
                        cmd.get(Cmds.MODULE)+","+cmd.get(Cmds.FUNCTION)+ ","+cmd.get(Cmds.PULSE);
                break;
            case Cmds.ACQ_TIME:
                break;
            case Cmds.RESOLUTION:
                // PC : otu:module:calot:lres? mod2,"SM-OTDR","3 ns","5 km"
                // RTU : "Auto","4 cm","8 cm","16 cm","32 cm","64 cm"
                cmdLine = "OTU:MODUle:CALOT:Lres? "+
                        cmd.get(Cmds.MODULE)+","+cmd.get(Cmds.FUNCTION)+","+cmd.get(Cmds.PULSE)+","+cmd.get(Cmds.RANGE);
                break;
            case Cmds.MEAS_MANUAL:
                // PC : otu:mealink:SI:webconfig? MOD2,1,#170001004,MAN,1000,80,15,1.465,1650,64,"SM-OTDR"
                // RTU : "/acterna/user/harddisk/otu/result/measure_on_demand";"measure.sor"
                cmdLine = "otu:mealink:SI:webconfig? "+
                    cmd.get(Cmds.MODULE)+","+ // <Module>: MOD1 or MOD2
                    "0,"+ // <Switch Number>: 0 for local
                    "#1700010"+cmd.get(Cmds.OTU_OUT)+","+ // <Optical path>: buffer containing for each switch the common number and the port number.
                    cmd.get(Cmds.MANU_CONFIG)+","+ // <Autoconfig> :  [MANual,AUTO]
                    cmd.get(Cmds.PULSE)+","+ // <Pulsewidth> : string of characters
                    cmd.get(Cmds.RANGE)+","+ // <Range> : string of characters
                    cmd.get(Cmds.ACQ_TIME)+","+ // <Acquisition Time> : [5 : 600 ] in seconds
                    //cmd.get("index")+","+ // <Refractive index> : [1.3 : 1.7]
                    "1.465,"+
                    cmd.get(Cmds.LASER)+","+
                    cmd.get(Cmds.RESOLUTION)+","+ // <Laser> : string of characters
                    cmd.get(Cmds.FUNCTION); // <Function name> : ["SM-OTDR"]
            break;
            case Cmds.TCPPORT:
                // PC : MODule:FUNCtion:PORT? OPPSide,SLIC1,"OTDR"
                // RTU: 8002
                break;
            case Cmds.MEAS_STATUS:
                cmdLine = "OTU:MEAS:STATUS?";
                break;
            case Cmds.SYSTEM_DATE:
                cmdLine = "OTU:SYSTem:DATe?";
                break;
            case Cmds.SYSTEM_TIME:
                cmdLine = "OTU:SYSTem:TIMe?";
                break;
            case Cmds.CURVE_BUFFER:
                //  cmdLine = "OTU:MEASure:RESULT?";
                cmdLine = "CURve:BUFfer?";
                break;
            case Cmds.CURVE_XOFFSET:
                cmdLine = "CURve:XOFFset?";
                break;
            case Cmds.CURVE_XSCALE:
                cmdLine = "CURve:XSCale?";
                break;
            case Cmds.CURVE_XUNIT:
                cmdLine = "CURve:XUNit?";
                break;
            case Cmds.CURVE_YOFFSET:
                cmdLine = "CURve:YOFFset?";
                break;
            case Cmds.CURVE_YSCALE:
                cmdLine = "CURve:YSCale?";
                break;
            case Cmds.CURVE_YUNIT:
                cmdLine = "CURve:YUNit?";
                break;
            case Cmds.TABLE_SIZE:
                // PC : TABle:SIZe?
                // RTU: 4
                cmdLine = "TABle:SIZE?";
                break;
            case Cmds.TABLE_LINE:
                // PC : TABle:LINe? 1
                // RTU:   1,,     0.00,~ 25.614,~>-25.75,,     0.00,
                cmdLine = "TABle:LINe? "+cmd.get(Cmds.TABLE_LINE_NUM);
                break;
            default:
                System.out.println("Command \"" + cmd.get(Cmds.CMD) + "\" not support!");
                break;
        }
//        System.out.println("cmdLine: "+cmdLine);
        return cmdLine;
    }

    int getFunctionPort(String cmdline) {
        if (cmdline.contains("CURve") || cmdline.contains("TABle"))
            return ServerInfo.FiberOpticPort;
        return ServerInfo.OTUPort;
    }

    byte[] sendAndEcho(String cmdLine) {
        int serverPort = getFunctionPort(cmdLine);
        try {
//            TcpClient client = new TcpClient(ServerInfo.ServerAddress, serverPort, 5000);
            TcpClient client = new TcpClient(Environment.peerIP, serverPort, Integer.parseInt(Environment.timeOut)*1000);
            Socket clientSocket = client.getClientSocket();
            clientSocket.setSoTimeout(10000); // 10sec

            byte[] delimit = { 0x0d,0x0a};
            OutputStream outputStream = clientSocket.getOutputStream();

            outputStream.write( "*REM".concat(";").concat(cmdLine).concat(new String(delimit)).getBytes());
            clientSocket.shutdownOutput();
//            System.out.println( "*REM".concat(new String(delimit)).concat(cmdLine).concat(new String(delimit)));

            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

            int read;
            byte[] rcvBuffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ( (read = inputStream.read(rcvBuffer)) != -1) {
                baos.write(rcvBuffer, 0, read);
            }

            outputStream.close();
            inputStream.close();
            clientSocket.close();

//            FileOutputStream fileOutputStream = new FileOutputStream(
//                    "./trace_"+new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())+".txt");
            FileOutputStream fileOutputStream = new FileOutputStream("./onmsTrace.txt");
            fileOutputStream.write(baos.toByteArray());
            fileOutputStream.close();
//            try {
//                Files.write(Paths.get("./curveTrace.txt"), baos.toByteArray(), StandardOpenOption.APPEND);
//            }catch (IOException e) {
//                //exception handling
//            }

            return baos.toByteArray();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    HashMap parseReceiveBuffer(HashMap<String, String> cmd, byte[] rcvBuffer) {
        HashMap<String, Object> result = new HashMap<>();
        switch (cmd.get(Cmds.CMD)) {
            case Cmds.MEAS_MANUAL:
                result.put("file",new String(rcvBuffer));
                break;
            case Cmds.MEAS_TRACE:
                break;
            case Cmds.IDN:
            case Cmds.ERS:
            case Cmds.MEAS_STATUS:
            case Cmds.PULSE:
            case Cmds.RANGE:
            case Cmds.RESOLUTION:
                String s = new String(rcvBuffer);
                s = s.replaceAll("\\r|\\n", "");
                result.put(cmd.get(Cmds.CMD), s);
                break;
            case Cmds.TABLE_SIZE:
                break;
            default:
                System.out.println("Command \"" + cmd.get("command") + "\" not support!");
                break;
        }

        return result;
    }

    private HashMap measureTrace(HashMap<String, String> cmd) {
        return null;
    }

    private HashMap measureOnDemand(HashMap<String, String> cmd) {
        String cmdLine = convertToCmdline(cmd);
        byte[] rcvBuff = sendAndEcho(cmdLine);
        System.out.println("ECHO: "+new String(rcvBuff));
        HashMap result = parseReceiveBuffer(cmd, rcvBuff);

        if (result.get("file").toString().indexOf(".sor") != -1) {
            HashMap newCmd = new HashMap();
//            newCmd.put("command", "*ers?");
//            cmdLine = convertToCmdline(newCmd);
//            rcvBuff = sendAndEcho(cmdLine);
//            result = parseReceiveBuffer(newCmd, rcvBuff);
//            newCmd.clear();

            newCmd.put(Cmds.CMD, Cmds.MEAS_STATUS);
            cmdLine = convertToCmdline(newCmd);
            rcvBuff = sendAndEcho(cmdLine);
            result = parseReceiveBuffer(newCmd, rcvBuff);
        } else {
            result.clear();
            result.put(Cmds.MEAS_STATUS, "error:fail to add measurement");
        }

        return result;
    }

    private HashMap singleCommand(HashMap<String, String> cmd) {
        String cmdLine = convertToCmdline(cmd);
        byte[] rcvBuff = sendAndEcho(cmdLine);
        return parseReceiveBuffer(cmd, rcvBuff);
    }

    HashMap mapCommand(HashMap<String, String> cmd) {
        switch (cmd.get(Cmds.CMD)) {
//            case Cmds.MEAS_DEFAULT:
//                return measureDefault(cmd);
            case Cmds.MEAS_MANUAL:
                return measureOnDemand(cmd);
            case Cmds.MEAS_TRACE:
                return measureTrace(cmd);
            default:
                return singleCommand(cmd);
        }
    }

    HashMap commonSocketInterface(HashMap<String, String> cmd) {
        return mapCommand(cmd);
    }

    byte[] builtInCommandWithoutParam(String cmd) {
        HashMap newCmd = new HashMap();
        newCmd.put(Cmds.CMD, cmd);
        String cmdLine = convertToCmdline(newCmd);
        return sendAndEcho(cmdLine);
    }

    byte[] builtInCommandWithParam(HashMap<String, String> cmd) {
        String cmdLine = convertToCmdline(cmd);
        return sendAndEcho(cmdLine);
    }
}
