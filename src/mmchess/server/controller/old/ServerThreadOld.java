package mmchess.server.controller.old;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Travis Meares
 * Creates a thread that watches every
 */
public class ServerThreadOld implements Runnable {
    private Socket socket;
    private Scanner fromClient;          // gets input from the socket
    private PrintWriter toClient;        // writes to the socket
    private static int numberOfClients = 0;
    private final int clientNum;
    AvailablePlayersList availablePlayersList;
    private String clientName;


    public ServerThreadOld(Socket socket) {
        this.socket = socket;
        clientNum = ++numberOfClients;
    }

    @Override
    public void run() {
        System.out.printf("Client #%d connected\n", clientNum);

        try {
            fromClient = new Scanner(socket.getInputStream());
            toClient = new PrintWriter(socket.getOutputStream());

            boolean isLogged;
            do {
                isLogged = logIn();
            } while (!isLogged);

            System.out.printf("Client #%d logged in as \"%s\"\n", clientNum, clientName);

            String msg;
            do {
                // get message from client
                msg = fromClient.nextLine();
                // check for secret codes, else treat as a message
                if (msg.equals("GET")) {
                    System.out.printf("%s", availablePlayersList.getClientList().toString());
                } else {
                    System.out.printf("%s sent: %s\n", clientName, msg);
                }
            } while (!msg.equals("EXIT"));

            System.out.printf("%s disconnected\n", clientName);
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean logIn() {
        toClient.println("LOG");
        toClient.flush();

        clientName = fromClient.nextLine();
        if (clientName.isEmpty()) {
            return false;
        }
        // add client name to connection queue
        availablePlayersList = new AvailablePlayersList(clientName);
        toClient.println("SUCCESS");
        toClient.flush();
        return true;
    }
}