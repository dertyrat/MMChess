package mmchess.server.controller;

import java.io.IOException;
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
            System.out.printf("%s\n", "Player 1 connected");
            p2 = listener.accept();
            System.out.printf("%s\n", "Player 2 connected, creating match thread");
            matchThread = new Thread(new MatchTarget(p1, p2));
            matchThread.start();
        }
    }
}
