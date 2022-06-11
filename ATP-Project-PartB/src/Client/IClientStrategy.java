package Client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * The IClientStrategy interface is an interface associated with the strategy design pattern.
 * An interface designed for clients who send requests to servers.
 * Any class that implements the interface must implement the clientStrategy method.
 */
public interface IClientStrategy {
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);

}
