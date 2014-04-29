/*
 * Copyright 2014 Matthew.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mmchess.client.connection;

import java.io.ObjectInputStream;
import javafx.application.Platform;
import javafx.concurrent.Task;
import mmchess.client.controller.Controller;

/**
 *
 * @author Matthew
 */
public class InputTask extends Task<Void> {
    private ObjectInputStream inputStream;
    private boolean isRunning = true;
    private Controller controller;

    public InputTask(Controller controller, ObjectInputStream inputStream) {
        this.inputStream = inputStream;
        this.controller = controller;
    }

    /**
     * Called by the Thread class upon start. Reads from input stream until end
     * is called
     */
    @Override
    public Void call() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isRunning) {
                        Object input = inputStream.readObject();
                        controller.update(null, input);
                        //setChanged();                
                        //notifyObservers(input);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (e instanceof IllegalStateException) {
                        System.out.println(e.getCause());
                    }
                }
            }
        });
        return null;
    }

}
