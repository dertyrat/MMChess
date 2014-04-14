package mmchess.server.testClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

/**
 * @author Travis Meares
 */
public class Connection extends Observable {
    Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Connection() {
        final int PORT = 8888;      //must match server port number

        try {
            Socket socket = new Socket("localhost", PORT);
            System.out.printf("Connected to %s\n", socket.getInetAddress().getHostName());

            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            Thread thread = new Thread(new ConnectionThread(inputStream));
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean sendMove(ChessMove move) {
        try {
            outputStream.writeObject(move);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    public ChessMove receiveMove() {
        ChessMove input = null;
        try {
            input = (ChessMove) inputStream.readObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return input;
    }

    public void close() {
        System.out.printf("Goodbye");
        try {
            socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


}

class ChessMove {}