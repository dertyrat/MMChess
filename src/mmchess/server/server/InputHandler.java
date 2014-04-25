package mmchess.server.server;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Travis Meares
 */
public class InputHandler implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        // figure out input type and handle accordingly
        String[] input = arg.toString().split("[ ]+");
        String inputType = input[0];
        String inputMessage = input[1];

        if (inputType.equals("MOVE")) {
            // send inputMessage to model
            System.out.printf("%s\n", inputMessage);
        } else if (inputType.equals("CONN")) {
            // handle connection error
            System.out.printf("%s", "Connection error!");
        } else {
            // invalid data sent, respond to server
        }
    }
}
