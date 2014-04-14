/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmchess.client.controller;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Matthew
 */
public class Controller implements Initializable {
    
    @FXML
    private GridPane boardGrid;
    @FXML
    private VBox movesList;
    
    private final ImageView[][] boardCells = new ImageView[8][8];
    private ImageView selectedCell = null;
    private ObservableList<Node> movesListObservable;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int x = 0;
        int y = 0;
        for (Node child : boardGrid.getChildren()) {
            // Setting up the grid array
            boardCells[x][y] = (ImageView)((StackPane)child).getChildren().get(0);
            // setting cell data: x, y coord, & selected flag
            boardCells[x][y].setUserData(new int[]{x,y});
            x++;
            if (x == 8) {
                y++;
                x = 0;
            }
        }
        
        movesListObservable = movesList.getChildren();
    }
    
    @FXML
    public void exitApplication()
    {
        Platform.exit();
    }
    
    @FXML
    public void test() {
        movesListObservable.add(new Text("test"));
    }
    
    @FXML
    public void clickCell(Event e) {
        if (selectedCell == null) { 
            // If no other cell clicked
            selectedCell = (ImageView)e.getSource();
            // TODO: Mark cell as having been selected visually
        } else if (selectedCell == (ImageView)e.getSource()) {
            // If the clicked cell is already selected
            // Deselect the cell
            selectedCell = null;
            // TODO: remove the visual indicator that the cell has been selected
        } else {
            // If the clicked cell is different from the selected cell
            // TODO: insert code to make a move here
            int[] moveTo = (int[]) ((ImageView)e.getSource()).getUserData();
            int[] moveFrom = (int[]) selectedCell.getUserData();
            // Deselect the cell
            selectedCell = null;
            // TODO: remove the visual indicator that the cell has been selected
        }
    }
}
