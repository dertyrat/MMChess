package mmchess.server.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Travis Meares
 */
public class RunServer {
    /**
     * The server main, runs the server until program errors or is closed
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(8888); // listens for connections
        Socket p1, p2;
        Thread matchThread;
        System.out.printf("%s\n", "Waiting for clients....");
        // wait for two connections and initialize a match thread with them
        while (true) {
            p1 = listener.accept();
            ObjectInputStream p1In = new ObjectInputStream(p1.getInputStream());
            ObjectOutputStream p1Out = new ObjectOutputStream(p1.getOutputStream());
            System.out.printf("%s\n", "Player 1 connected");
            p2 = listener.accept();
            ObjectInputStream p2In = new ObjectInputStream(p2.getInputStream());
            ObjectOutputStream p2Out = new ObjectOutputStream(p2.getOutputStream());
            System.out.printf("%s\n", "Player 2 connected, creating match thread");
            matchThread = new Thread(new MatchTarget(p1In, p1Out, p2In, p2Out));
            matchThread.start();
        }
    }
}
