package mmchess.server.old;

import java.util.ArrayList;

/**
 * @author Travis Meares
 * Maintains a list of available players for the lobby
 */
public class AvailablePlayersList {
    private static ArrayList<String> clientList = new ArrayList<String>();

    /**
     * Constructor, adds the new name to the list
     * @param name The player's name
     */
    public AvailablePlayersList(String name) {
        clientList.add(name);
    }

    /**
     * Gets the list of players' names
     * @return The names of the players
     */
    public ArrayList<String> getClientList() {
        return clientList;
    }
}
