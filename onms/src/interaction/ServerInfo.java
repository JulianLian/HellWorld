package interaction;

/**
 * Created by Julian on 2015/12/26.
 */
public class ServerInfo {
    public static String serverAddress;
    public static final int ISUPort = 8000;
    public static final int OTUPort = 8003;
    public static final int FiberOpticPort = 8002;
//    Command MODule:FUNCtions:PORT?

    public static void setServerAddress(String serverAddress) {
        ServerInfo.serverAddress = serverAddress;
    }

    public static String getServerAddress() {
        return serverAddress;
    }

    public static int getISUPort() {
        return ISUPort;
    }

    public static int getOTUPort() {
        return OTUPort;
    }

    public static int getFiberOpticPort() {
        return FiberOpticPort;
    }
}
