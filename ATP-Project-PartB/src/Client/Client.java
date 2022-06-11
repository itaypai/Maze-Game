package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client class that implements a client.
 * This client class works according to the strategy that client object offers.
 */
public class Client {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy strategy;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }

    /**
     * According to the client strategy, client communicate with the server.
     */
    public void communicateWithServer()
    {
        try(Socket serverSocket = new Socket(serverIP, serverPort)){
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
