package interaction;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by Julian on 2015/12/21.
 */
public class TcpClient {
    String server;
    int port;
    int timeout;
//    Socket socket;

    public TcpClient(String server, int port, int timeout) {
        this.server = server;
        this.port = port;
        this.timeout = timeout;
    }

    Socket getClientSocket() throws SocketTimeoutException, IOException{
        try {
            Socket clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress(this.server, this.port), this.timeout);
            return clientSocket;
        } catch (UnknownHostException e) {
            System.err.println("Didn't know about host " + server);
            return null;
        } catch (SocketTimeoutException sto) {
            throw sto;
        }
    }
}
