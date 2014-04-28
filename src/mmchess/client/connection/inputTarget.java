package mmchess.client.connection;


import mmchess.client.controller.Controller;

import java.io.ObjectInputStream;
import java.util.Observable;

/**
 * @author Travis Meares
 */
public class InputTarget extends Observable implements Runnable {
    private ObjectInputStream inputStream;
    private boolean isRunning = true;

    public InputTarget(Controller controller, ObjectInputStream inputStream) {
        this.inputStream = inputStream;
        addObserver(controller);
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
