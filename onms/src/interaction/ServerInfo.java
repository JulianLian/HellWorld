package interaction;

/**
 * Created by Julian on 2015/12/26.
 */
public class ServerInfo {
    static String ServerAddress = "192.168.0.5";
    static final int ISUPort = 8000;
    static final int OTUPort = 8003;
    static final int FiberOpticPort = 8002;
//    Command MODule:FUNCtions:PORT?

    public static void setServerAddress(String serverAddress) {
        ServerInfo.ServerAddress = serverAddress;
    }

    public static String getServerAddress() {
        return ServerAddress;
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
