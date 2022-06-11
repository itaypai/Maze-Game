package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * The IServerStrategy interface is an interface associated with the strategy design pattern.
 * An interface designed for servers to handle clients by using it.
 * Any class that implements the interface must implement the applyStrategy method.
 */
public interface IServerStrategy {
    void applyStrategy(InputStream inFromClient, OutputStream outToClient);
}

