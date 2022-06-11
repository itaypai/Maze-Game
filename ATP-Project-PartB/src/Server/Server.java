package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server class that implements a server.
 * The server treats clients in parallel according to the strategies it offers.
 */
public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean stop;
    //Thread pool
    private ExecutorService threadPool;

    /**
     * Constructor
     * @param port
     * @param listeningIntervalMS
     * @param strategy
     */
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy)
    {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        //Initialize new thread pool with the requested number of threads.
        int numOfThreads = Configurations.getInstance().getThreadPoolSize();
        this.threadPool = Executors.newFixedThreadPool(numOfThreads);
    }

    /**
     * Start the threads work.
     * Gets the startServer task.
     */
    public void start()
    {
        Thread newThread = new Thread(()->{this.startServer();});
        newThread.start();
    }

    /**
     * A method called in the start function.
     * Its purpose is to start the work of the servers
     */
    private void startServer()
    {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);

            while (!stop)
            {
                try {
                    Socket clientSocket = serverSocket.accept();

                    // Insert the new task into the thread pool
                    threadPool.submit(() -> {handleClient(clientSocket);
                    });
                }
                catch (SocketTimeoutException e){
                    e.printStackTrace();
                }
            }
            serverSocket.close();
            // do not allow any new tasks into the thread pool, and also interrupts all running threads
            // (do not terminate the threads, so if they do not handle interrupts properly, they could never stop...)
            threadPool.shutdownNow();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function handle with the requests of the clients.
     * @param clientSocket, creates the communication with the client.
     */
    private void handleClient(Socket clientSocket)
    {
        try {
            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Change the boolean to true.
     */
    public void stop()
    {
        stop = true;
    }
}
