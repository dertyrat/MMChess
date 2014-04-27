package mmchess.client.connection;

import mmchess.client.connection.Connection;

import java.util.Scanner;

/**
 * @author Travis Meares
 */
public class ControllerReplicator1 {
    public static void main(String[] args) {
        Connection conn = new Connection();
        conn.open();
        Scanner scanner = new Scanner(System.in);
        String msg;
        do {
            System.out.printf("Message for server: ");
            msg = scanner.nextLine();
            conn.sendMove(msg);
        } while (!msg.equals("END"));

        conn.close();
    }
}
