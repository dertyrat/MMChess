package mmchess.server;

import java.util.ArrayList;

/**
 * @author Travis Meares
 */
public class ConnectionQueue {
    private static ArrayList<String> clientList = new ArrayList<String>();

    public ConnectionQueue(String name) {
        clientList.add(name);
    }

    public ArrayList<String> getClientList() {
        return clientList;
    }
}
