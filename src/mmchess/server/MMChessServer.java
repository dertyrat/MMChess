package mmchess.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Travis Meares
 */
public class MMChessServer {
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(8888); // listens for connections

        while (true) {
            System.out.printf("%s\n", "Waiting for clients....");
            Socket socket = listener.accept();
            Thread thread = new ServerThread(socket);
            thread.start();
        }
    }
}