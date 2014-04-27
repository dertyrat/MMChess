package mmchess.server;

import mmchess.server.old.InputHandler;
import mmchess.server.old.ServerThread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Travis Meares
 */
public class RunServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.printf("%s\n", "Waiting for clients....");

            Socket socket;
            Thread thread;
            InputHandler inputHandler = new InputHandler();

            socket = serverSocket.accept();
            thread = new Thread(new ServerThread(socket));
            inputHandler.addPlayer(new ObjectOutputStream(socket.getOutputStream()));
            thread.start();
            System.out.printf("%s\n", "Client 1 connected");

            socket = serverSocket.accept();
            inputHandler.addPlayer(new ObjectOutputStream(socket.getOutputStream()));
            thread = new Thread(new ServerThread(socket));
            thread.start();
            System.out.printf("%s\n", "Client 2 connected");



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
