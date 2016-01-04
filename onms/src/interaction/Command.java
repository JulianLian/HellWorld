package interaction;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;

/**
 * Created by Julian on 2015/12/21.
 */
public class Command {

    public Command() {    }

    String convertToCmdline(HashMap<String, String> cmd) {
        String cmdLine = "NEEDNOT";
        switch (cmd.get("command").toUpperCase()) {
            case "IDN?":
                cmdLine = "idn?";
                break;
            case "MEASDEFAULT":
                break;
            case "MODULE":
                // PC : OTU:MODUle:DETect?
                // RTU : MOD1: -1, ;MOD2: 5027HD,"SM-OTDR" : L1625,NONE,NONE,NONE
                cmdLine = "OTU:MODUle:DETect?";
                break;
            case "FUNCTION":
                // PC : OTU:MODUle:CALFUnctions:LIST? MOD2
                // RTU : "SM-OTDR"
                cmdLine = "OTU:MODUle:CALFUNC:LIST? " + cmd.get("module");
                break;
            case "SWITCH":
                // PC : otu:switch:detect:list?
                // RTU : 0
                cmdLine = "OTU:SWITCH:DETECT:LIST?";
                // PC : otu:switch:detect:desc? 0,INT
                // RTU : OTU, "SA201004","10.33.16.63:1400"
                break;
            case "PORT":
                break;
            case "MANUAL":
                break;
            case "LASER":
                // PC : OTU:MODUle:CALLaser:LIST? MOD2, "SM-OTDR"
                // RTU : "1625 nm"5
                cmdLine = "OTU:MODUle:CALLaser:LIST? "+cmd.get("module")+","+cmd.get("function");
                break;
            case "PULSE":
                // PC : otu:module:calot:lpulse? mod2,"SM-OTDR"
                // RTU : "3 ns","30 ns","100 ns","300 ns","1 us","3 us","10 us","20 us"
                cmdLine = "OTU:MODUle:CALOT:LPULSE? "+cmd.get("module")+","+cmd.get("function");
                break;
            case "RANGE":
                // PC : otu:module:calot:lrange? mod2,"SM-OTDR","3 ns"
                // RTU : "5 km","10 km","20 km","40 km","80 km"
                cmdLine = "OTU:MODUle:CALOT:Lrange? "+
                        cmd.get("module")+","+cmd.get("function")+ ","+cmd.get("pulse");
                break;
            case "TIME":
                break;
            case "RESOLUTION":
                // PC : otu:module:calot:lres? mod2,"SM-OTDR","3 ns","5 km"
                // RTU : "Auto","4 cm","8 cm","16 cm","32 cm","64 cm"
                cmdLine = "OTU:MODUle:CALOT:Lres? "+
                        cmd.get("module")+","+cmd.get("function")+","+cmd.get("pulse")+","+cmd.get("range");
                break;
            case "MEASMANUAL":
                // PC : otu:mealink:webconfig? MOD2,1,#170001004,MAN,"1 us","80 km",15,1.465,"1625 nm","Auto","SM- OTDR"
            // RTU : "/acterna/user/harddisk/otu/result/measure_on_demand";"measure.sor"
            cmdLine = "otu:mealink:webconfig? "+
                    cmd.get("module")+","+ // <Module>: MOD1 or MOD2
                    cmd.get("switch")+","+ // <Switch Number>: 0 for local
                    "#1700010"+cmd.get("otu_out")+","+ // <Optical path>: buffer containing for each switch the common number and the port number.
                    cmd.get("configuration")+","+ // <Autoconfig> :  [MANual,AUTO]
                    cmd.get("pulse")+","+ // <Pulsewidth> : string of characters
                    cmd.get("range")+","+ // <Range> : string of characters
                    cmd.get("time")+","+ // <Acquisition Time> : [5 : 600 ] in seconds
                    //cmd.get("index")+","+ // <Refractive index> : [1.3 : 1.7]
                    "1.46500"+","+
                    cmd.get("resolution")+","+ // <Laser> : string of characters
                    cmd.get("function"); // <Function name> : ["SM-OTDR"]
            break;
            case "TCPPORT":
                // PC : MODule:FUNCtion:PORT? OPPSide,SLIC1,"OTDR"
                // RTU: 8002
                break;
            case "BUFFER":
//                cmdLine = "OTU:MEASure:RESULT?";
                cmdLine = "CURve:BUFfer?";
                break;
            case "TABLE":
                // PC : TABle:SIZe?
                // RTU: 4
                cmdLine = "TABle:SIZE?";
                // PC : TABle:LINe? 2
                // RTU: 2,Reflection, 40.29,,>-58.65,, 35.97,
                break;
            default:
                break;
        }
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
            TcpClient client = new TcpClient(ServerInfo.ServerAddress, serverPort, 5000);
            Socket clientSocket = client.getClientSocket();
            clientSocket.setSoTimeout(10000); // 10sec

            byte[] delimit = { 0x0d,0x0a};
            OutputStream outputStream = clientSocket.getOutputStream();

            outputStream.write( "*REM".concat(new String(delimit)).concat(cmdLine).concat(new String(delimit)).getBytes());
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
        switch (cmd.get("command").toUpperCase()) {
            case "BUFFER":
                double xoffset = 0.000000; //to be completed
                double xscale = 6.39488995E-01; //to be completed
                double yoffset = -13.700909; //to be completed
                double yscale = 0.001470; //to be completed
                ParseCurveBuffer parser = new ParseCurveBuffer(xscale, yscale, yoffset);
                result.put("xoffset", xoffset);
                result.put("xscale", xscale);
                result.put("yoffset", yoffset);
                result.put("yscale", yscale);
                result.put("points", parser.parseDataPoints(rcvBuffer));
                break;
            case "IDN?":
                result.put("idn", new String(rcvBuffer));
                break;
            case "TABLE":
                break;
            default:
                System.out.println("Command " + cmd.get("command") + "not support!");
                break;
        }

        return result;
    }

    private boolean needSend(String cmdLine) {
        return !cmdLine.equals("NEEDNOT");
    }

    private HashMap measureTrace(HashMap<String, String> cmd) {
        return null;
    }

    private HashMap measureOnDemand(HashMap<String, String> cmd) {
        String cmdLine = convertToCmdline(cmd);
        byte[] rcvBuff = sendAndEcho(cmdLine);
        HashMap result = parseReceiveBuffer(cmd,rcvBuff);
        if (result != null) {
            HashMap newCmd = new HashMap();
            newCmd.put("command", "status?");
            cmdLine = convertToCmdline(newCmd);
            rcvBuff = sendAndEcho(cmdLine);
            result = parseReceiveBuffer(newCmd, rcvBuff);
        } else {
            result.clear();
            result.put("status", "error:fail to add measurement");
        }

        return result;
    }

    private HashMap measureDefault(HashMap<String, String> cmd) {
        HashMap measureDefaultArgs = new HashMap();
        measureDefaultArgs.put("module", "MOD1");
        measureDefaultArgs.put("function", "\"SM-OTDR\"");
        measureDefaultArgs.put("OTU", "0");
//        measureDefaultArgs.put("nb_otau", "1");
        measureDefaultArgs.put("switch", "0");
        String[] otu_in_options = {"01"};
        measureDefaultArgs.put("otu_in", otu_in_options);
        String[] otu_out_options = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        measureDefaultArgs.put("otu_out", otu_out_options);
        measureDefaultArgs.put("otu_used", "1");  // the checkbox
        String[] configuration_options = {"AUTO", "MANUAL"};
        measureDefaultArgs.put("configuration", configuration_options);
        measureDefaultArgs.put("laser", "1650");
        String[] pulse_options = {"3","30","100","300","1000","3000","10000","20000"};
        measureDefaultArgs.put("pulse", pulse_options);
        String[] range_options = {"2","5","10","20","40"};
        measureDefaultArgs.put("range", range_options);
        String[] resolution_options = {"0","4","8","16","32","64"};
        measureDefaultArgs.put("resolution", resolution_options);
        measureDefaultArgs.put("time", "20");
//        measureDefaultArgs.put("duration_min", "0");
//        measureDefaultArgs.put("duration_sec", "20");

//        measureDefaultArgs.put("buffer_otau", "NOK");

        return measureDefaultArgs;
    }

    private HashMap singleCommand(HashMap<String, String> cmd) {
        String cmdLine = convertToCmdline(cmd);
        byte[] rcvBuff = sendAndEcho(cmdLine);
        return parseReceiveBuffer(cmd, rcvBuff);
    }

    HashMap mapCommand(HashMap<String, String> cmd) {
        switch (cmd.get("command").toUpperCase()) {
            case "MEASDEFAULT":
                return measureDefault(cmd);
            case "MEASMANUAL":
                return measureOnDemand(cmd);
            case "MEASTRACE":
                return measureTrace(cmd);
            default:
                return singleCommand(cmd);
        }
    }

    HashMap commonSocketInterface(HashMap<String, String> cmd) {
        return mapCommand(cmd);
    }
}
