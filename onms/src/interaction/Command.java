package interaction;

import java.io.*;
import java.net.Socket;
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
        ServerInfo serverInfo = new ServerInfo();
        if (func.contains("CURve"))
            return ServerInfo.FiberOpticPort;
        return ServerInfo.OTUPort;
    }

    void send(String cmdLine) throws IOException {
        int serverPort = getFunctionPort(cmdLine);

        TcpClient client = new TcpClient(ServerInfo.getServerAddress(), serverPort, 5000);
        Socket clientSocket = client.getClientSocket();
        clientSocket.setSoTimeout(10000); // 10sec
        OutputStream outputStream = clientSocket.getOutputStream();
        DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());

        outputStream.write(cmdLine.getBytes());

        receive(inputStream);

        clientSocket.close();
    }

    HashMap<String, Object> receive(DataInputStream in) {
        HashMap<String, Object> result = new HashMap<>();
        byte[] rcvBuffer = new byte[1024];

        in.readFully(rcvBuffer);

        return result;
    }
}
