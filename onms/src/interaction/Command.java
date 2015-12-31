package interaction;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Julian on 2015/12/21.
 */
public class Command {

    public Command() {

    }

    String convertToCmdline(HashMap<String, String> cmd) {
        String cmdLine = "*idn?";
        switch (cmd.get("command").toUpperCase()) {
            case "MODULE":
                cmdLine = "";
                break;
            case "FUNCTION":
                cmdLine = "OTU:MODUle:CALFUNC:LIST? MOD1";
                break;
            case "SWITCH":
                break;
            case "PORT":
                break;
            case "MANUAL":
                break;
            case "LASER":
                cmdLine = "OTU:MODUle:CALLaser:LIST? MOD1, \"SM-OTDR\"";
                break;
            case "PULSE":
                cmdLine = "OTU:MODUle:CALOT:LPULSE? MOD1, \"SM-OTDR\"";
                break;
            case "RANGE":
                cmdLine = "OTU:MODUle:CALOT:Lrange? MOD1, \"SM-OTDR\" ,\"1 us\"";
                break;
            case "TIME":
                break;
            case "RESOLUTION":
                cmdLine = "OTU:MODUle:CALOT:Lres? MOD1, \"SM-OTDR\" ,\"1 us\",\"20 km\"";
                break;
            case "START":
                cmdLine = "otu:mealink:webconfig? MOD1,1,#170001001,MAN,\"1 us\",\"20 km\",15,1.465,\"1650 nm\",\"64cm\",\"SM-OTDR\"";
                break;
            case "BUFFER":
//                cmdLine = "OTU:MEASure:RESULT?";
                cmdLine = "CURve:BUFfer?";
                break;
            default:
                break;
        }
        return cmdLine;
    }

    int getFunctionPort(String func) {
        if (func.contains("CURve"))
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
            case "BUFFER?":
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
            default:
                System.out.println("Command " + cmd.get("command") + "not support!");
                break;
        }

        return result;
    }

    HashMap commonSocketInterface(HashMap<String, String> cmd) {
        String cmdLine = convertToCmdline(cmd);
        byte[] rcvBuff = sendAndEcho(cmdLine);
        return parseReceiveBuffer(cmd, rcvBuff);
    }
}
