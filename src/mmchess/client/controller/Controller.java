/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmchess.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import mmchess.client.model.Move;
import mmchess.client.model.Model;

/**
 *
 * @author Matthew
 */
public class Controller implements Initializable {
    
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
        
        model = new Model();
    }
    
    @FXML
    public void exitApplication()
    {
        Platform.exit();
    }
    
    @FXML
    public void newGame() {
        model.newGame();
    }
    
    @FXML
    public void test() {
        //movesListObservable.add(new Text("test"));
    }
    
    @FXML
    public void clickCell(Event e) {
        if (selectedCell == null) { 
            // If no other cell clicked
            selectedCell = (ImageView)e.getSource();
            if (selectedCell.getImage() != null) {
                
                // Draw selected cell indicator
                ((StackPane)selectedCell.getParent()).getStyleClass().add("selectedCell");
                
                // Draw Valid Moves indicators
                int[] moveFrom = (int[]) selectedCell.getUserData();
                // DEBUG: remove from final version
                System.out.println("Cell: " + moveFrom[0] + "," + moveFrom[1]);
                // END DEBUG
                validMoves = model.getValidMoves(moveFrom[0], moveFrom[1]);
                for (Move move : validMoves) {
                    ((StackPane)boardCells[move.getEndPosX()][move.getEndPosY()].getParent())
                            .getStyleClass().add("validMove");
                }
            } else {
                selectedCell = null;
            }
        } else if (selectedCell == (ImageView)e.getSource()) {
            // If the clicked cell is already selected
            // Deselect the cell
            ((StackPane)selectedCell.getParent()).getStyleClass().removeAll("selectedCell");
            for (Move move : validMoves) {
                ((StackPane)boardCells[move.getEndPosX()][move.getEndPosY()].getParent())
                        .getStyleClass().removeAll("validMove");
            }

            selectedCell = null;
        } else {
            // If the clicked cell is different from the selected cell
            // TODO: insert code to make a move here
            int[] moveTo = (int[]) ((ImageView)e.getSource()).getUserData();
            int[] moveFrom = (int[]) selectedCell.getUserData();
            // Deselect the cell
            ((StackPane)selectedCell.getParent()).getStyleClass().removeAll("selectedCell");
            selectedCell = null;
            for (Move move : validMoves) {
                ((StackPane)boardCells[move.getEndPosX()][move.getEndPosY()].getParent())
                        .getStyleClass().removeAll("validMove");
            }
            
            // TODO: Insert the move creation code here.
            // NOTE: Currently does the actual move for testing purposes, in final
            //       version, this will call a method of the model instead
            if (model.doMove(new Move(moveFrom[0], moveFrom[1], moveTo[0], moveTo[1])) ) {
                this.doMove(new Move(moveFrom[0], moveFrom[1], moveTo[0], moveTo[1]));
            }
        }
    }
    
    public void doMove(Move move) {
        boardCells[move.getEndPosX()][move.getEndPosY()].setImage(
                boardCells[move.getStartPosX()][move.getStartPosY()].getImage());
        boardCells[move.getStartPosX()][move.getStartPosY()].setImage(null);
    }
    
    
    @FXML
    private GridPane boardGrid;
    @FXML
    private VBox movesList;
    
    private ImageView[][] boardCells = new ImageView[8][8];
    private ImageView selectedCell = null;
    private ObservableList<Node> movesListObservable;
    private Model model;
    private Move[] validMoves;
}
