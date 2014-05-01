/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmchess.client.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Matthew
 */
public class ClientGUI extends Application {
    
    /**
     * The main entry point for all JavaFX applications. The start method is 
     * called after the init method has returned, and after the system is ready 
     * for the application to begin running.
     * @param stage the primary stage for this application, onto which the 
     *              application scene can be set. The primary stage will be 
     *              embedded in the browser if the application was launched as 
     *              an applet. Applications may create other stages, if needed, 
     *              but they will not be primary stages and will not be embedded 
     *              in the browser. 
     * @throws java.lang.Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MMChessClient.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        //Platform.setImplicitExit(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
