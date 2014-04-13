/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmchess.server;

/**
 * @author Travis Meares
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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

class ServerThread extends Thread {
    private static int clientNum = 0;
    private final int myNum;
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
        myNum = ++clientNum;
    }

    @Override
    public void run() {
        System.out.printf("Client #%d connected\n", myNum);

        try {
            Scanner fromClient = new Scanner(socket.getInputStream());          // gets input from the socket
            PrintWriter toClient = new PrintWriter(socket.getOutputStream());   // writes to the socket

            String msg = "";
            while (!msg.equals("EXIT")) {
                msg = fromClient.nextLine();
                System.out.printf("Client #%d sent: %s\n",myNum, msg);
            }

            System.out.printf("Client #%d disconnected\n", myNum);
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}