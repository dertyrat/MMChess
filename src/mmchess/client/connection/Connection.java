package mmchess.client.connection;

import mmchess.client.controller.Controller;
import mmchess.client.model.Move;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.application.Platform;

/**
 * @author Travis Meares
 * Establishes a connection to the server, handles output, and creates/manages
 * a thread to handle incoming input
 */
public class Connection {
    private static final String HOST = "localhost";
    private static final int PORT = 8888;
    Socket socket;
    ObjectOutputStream outputStream;
    InputTarget inputTarget;
    private char color;

    /**
     * Establishes a connection with the server and creates a thread
     * to continuously watch the socket for input.
     * @param controller the controller initializing this connection
     */
    public Connection(Controller controller) {
        try {
            // connect to server
            socket = new Socket(HOST, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            // initialize and run the input thread
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            inputTarget = new InputTarget(controller, inputStream);
            Thread inputThread = new Thread(inputTarget);
            inputThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the color assigned by the server
     * @return the color assigned to this client
     */
    public char getColor() {
        return color;
    }

    /**
     * Send a move object to the server
     * @param move the move to be sent
     */
    public void sendMove(Object move) {
        try {
            outputStream.writeObject(move);
        } catch (IOException e) {
            System.out.printf("%s\n", "Failed to send move to server.");
            e.printStackTrace();
        }
    }

    /**
     * Stop the thread and end the connection
     */
    public void end() {
        inputTarget.end();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}