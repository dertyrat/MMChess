package mmchess.server.old;

import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Travis Meares
 */
public class ConnectionList {
    private static ArrayList<Socket> clientList = new ArrayList<Socket>();

    public ConnectionList(Socket name) {
        clientList.add(name);
    }

    public ArrayList<Socket> getClientList() {
        return clientList;
    }
}
