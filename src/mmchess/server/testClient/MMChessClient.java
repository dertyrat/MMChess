package mmchess.server.testClient;

/**
 * @author Travis Meares
 */

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/**
 * @author Travis Meares
 */

public class MMChessClient {
    public static void main(String[] args) {
        final int PORT = 8888;      //must match server port number

        try {
            Socket socket = new Socket("localhost", PORT);
            System.out.printf("Connected to %s\n", socket.getInetAddress().getHostName());

            Scanner sc = new Scanner(System.in);
            Scanner fromServer = new Scanner(socket.getInputStream());
            PrintWriter toServer = new PrintWriter(socket.getOutputStream());

            String msg = "";
            while (!msg.equals("EXIT")) {
                System.out.printf("Message for server: ");
                msg = sc.nextLine();
                toServer.println(msg);
                toServer.flush();
                System.out.printf("sent\n");
            }

            System.out.printf("Goodbye");
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}