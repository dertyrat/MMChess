package mmchess.server.testClient;

import java.io.ObjectInputStream;
import java.util.Observable;

/**
 * Created by Trav on 4/13/2014.
 */
public class ConnectionThread extends Observable implements Runnable {
    private ObjectInputStream inputStream;
    private boolean isRunning = true;

    public ConnectionThread(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

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

    public void end() {
        isRunning = false;
    }
}
