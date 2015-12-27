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
            case "RESULT":
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
//            System.out.println( "*REM".concat(new String(delimit)).concat(cmdLine).concat(new String(delimit)));


            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            int rcvBytes = inputStream.available();
            while ( rcvBytes == 0) {
                rcvBytes = inputStream.available();
            }

            byte[] rcvBuffer = new byte[rcvBytes];
            if ( inputStream.read(rcvBuffer) == -1) {
                System.out.println("receive from socket failed!%n");
                return null;
            }

            outputStream.close();
            inputStream.close();
            clientSocket.close();

            return rcvBuffer;
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

    byte[] receive(DataInputStream in) {
        try {
            byte[] rcvBuffer = new byte[in.available()];

            in.read(rcvBuffer);

            return rcvBuffer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    HashMap parseReceiveBuffer(byte[] rcvBuffer) {
        HashMap<String, Object> result = new HashMap<>();
        return result;
    }
}
