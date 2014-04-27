package mmchess.client.connection.old;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Travis Meares
 * Handles client input from the server
 */
public class InputHandler implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        // figure out input type and handle accordingly
        String[] input = arg.toString().split("[ ]+");
        String inputType = input[0];

        if (inputType.equals("MOVE")) {
            // send move to controller
            System.out.printf("MOVE = %s\n", input[1]);
        } else if (inputType.equals("CONN")) {
            // handle connection error
            System.out.printf("%s", "Connection error!");
        } else {
            // invalid data sent
        }
    }
}
