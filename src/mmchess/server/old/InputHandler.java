package mmchess.server.old;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Travis Meares
 */
public class InputHandler implements Observer {
    private static ObjectOutputStream[] players = new ObjectOutputStream[2];

    @Override
    public void update(Observable o, Object arg) {
        // figure out input type and handle accordingly
        String[] input = arg.toString().split("[ ]+");
        String inputType = input[0];

        if (inputType.equals("MOVE")) {
            // verify move
            System.out.printf("MOVE = %s\n", input[1]);
            // send moves to clients
            for (int i = 0; i < players.length; ++i) {
                try {
                    players[i].writeObject("MOVE " + input[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (inputType.equals("CONN")) {
            // handle connection error
            System.out.printf("%s", "Connection error!");
        } else {
            // invalid data sent
        }
    }

    public boolean addPlayer(ObjectOutputStream objectOutputStream) {
        int length = players.length;
        if (length > 2) {
            players[length] = objectOutputStream;
        } else {
            return false;
        }

        return true;
    }
}
