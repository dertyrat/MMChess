package mmchess.server.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Travis Meares
 * Establishes a connection to the server, handles output, and creates/manages
 * a thread to handle incoming input
 */
public class Connection {
    Socket socket;
    ConnectionThread connectionThread;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Connection() {
        final int PORT = 8888;      //must match server port number

        try {
            Socket socket = new Socket("localhost", PORT);
            System.out.printf("Connected to %s\n", socket.getInetAddress().getHostName());

            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());

            connectionThread = new ConnectionThread(inputStream);
            Thread thread = new Thread(connectionThread);
            thread.start();
            // controller (must implement Observer) will be notified of incoming messages

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a move object to the server using an object stream
     * @param move the client validated ChessMove object
     * @return true if move was successful, false if IOException
     */
    public boolean sendMove(ChessMove move) {
        try {
            outputStream.writeObject("MOVE " + move.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Close the connection to the socket and end the connection thread
     */
    public void close() {
        connectionThread.end();
        try {
            socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


}

class ChessMove {}