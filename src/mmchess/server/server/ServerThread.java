package mmchess.server.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Observable;

/**
 * @author Travis Meares
 * Creates runnable target for a Thread to continuously watch the socket's input
 * stream and alerts an input handler any time there is input from the server
 */
public class ServerThread extends Observable implements Runnable {
    private Socket socket;
    private ObjectInputStream inputStream;
    private boolean isRunning = true;

    public ServerThread(Socket socket) {
        try {
            this.socket = socket;
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            addObserver(new InputHandler());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Called by the Thread class upon start. Reads from input stream until end
     * is called
     */
    @Override
    public void run() {
        try {
            while (isRunning) {
                Object input = inputStream.readObject();
                setChanged();
                notifyObservers(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the wrapper thread from watching the input stream
     */
    public void end() {
        isRunning = false;
    }
}
