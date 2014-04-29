/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mmchess.client.controller;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mmchess.client.connection.Connection;
import mmchess.client.model.Bishop;
import mmchess.client.model.Board;
import mmchess.client.model.King;
import mmchess.client.model.Knight;
import mmchess.client.model.Model;
import mmchess.client.model.Move;
import mmchess.client.model.Pawn;
import mmchess.client.model.Piece;
import mmchess.client.model.Queen;
import mmchess.client.model.Rook;

/**
 * 
 * @author Matthew
 */
public class Controller implements Initializable, Observer {
    /**
     * Initializes the GUI to a starting state
     * @param url
     * @param rb blah-dee blah-dee blah
     */
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
        try {
            currentBoard = (Board) model.getBoard().clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //model.addObserverToBoard(this);
        connection = new Connection(this);
        
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.millis(40), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateBoard();
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    /**
     * Updates the board model using input from server
     * @param o the class that notified
     * @param arg the object sent from the server
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Move) {
            // update board state/GUI
            model.doMove((Move)arg);
            model.getBoard().setCheckFlag();
            model.getBoard().setMateFlag(Piece.WHITE);
            model.getBoard().setMateFlag(Piece.BLACK);
            //updateBoard();
        } else if (arg instanceof String) {
            if (((String)arg).equals("TRN")) {
                model.setPlayerTurn(true);
            } else if (((String)arg).equals("CLR W")) {
                model.setPlayerColor(Piece.WHITE);
            } else if (((String)arg).equals("CLR B")) {
                model.setPlayerColor(Piece.BLACK);
            }
        }
    }

    /**
     * Exits the Platform
     */
    @FXML
    public void exitApplication()
    {
        Platform.exit();
    }

    /**
     * Sets GUI and board model to new game set up
     */
    @FXML
    public void newGame() {
        model.newGame();
        
        boardCells[0][0].setImage(Controller.blackRook);
        boardCells[1][0].setImage(Controller.blackKnight);
        boardCells[2][0].setImage(Controller.blackBishop);
        boardCells[3][0].setImage(Controller.blackQueen);
        boardCells[4][0].setImage(Controller.blackKing);
        boardCells[5][0].setImage(Controller.blackBishop);
        boardCells[6][0].setImage(Controller.blackKnight);
        boardCells[7][0].setImage(Controller.blackRook);
        
        for (int i = 0; i < 8; i++) {
            boardCells[i][1].setImage(Controller.blackPawn);
            boardCells[i][6].setImage(Controller.whitePawn);
        }
        
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                boardCells[j][i].setImage(null);
            }
        }
        
        boardCells[0][7].setImage(Controller.whiteRook);
        boardCells[1][7].setImage(Controller.whiteKnight);
        boardCells[2][7].setImage(Controller.whiteBishop);
        boardCells[3][7].setImage(Controller.whiteQueen);
        boardCells[4][7].setImage(Controller.whiteKing);
        boardCells[5][7].setImage(Controller.whiteBishop);
        boardCells[6][7].setImage(Controller.whiteKnight);
        boardCells[7][7].setImage(Controller.whiteRook);
        
        while (movesListObservable.size() > 0) {
            movesListObservable.remove(0);
        }
        while (topGraveyard.getChildren().size() > 0) {
            topGraveyard.getChildren().remove(0);
        }
        while (bottomGraveyard.getChildren().size() > 0) {
            bottomGraveyard.getChildren().remove(0);
        }
        while(statusBox.getChildren().size() > 0) {
            statusBox.getChildren().remove(0);
        }
    }

    /**
     * Updates the GUI by reading the state of the board model
     */
    @FXML
    public void updateBoard() {
        try {
            currentBoard = (Board) model.getBoard().clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece currentPiece = currentBoard.getPiece(x, y);
                
                if (currentPiece == null) {
                    boardCells[x][y].setImage(null);
                } else if (currentPiece instanceof Rook) {
                    if (currentPiece.getColor() == Piece.BLACK) {
                        boardCells[x][y].setImage(blackRook);
                    } else {
                        boardCells[x][y].setImage(whiteRook);
                    }
                } else if (currentPiece instanceof Bishop) {
                    if (currentPiece.getColor() == Piece.BLACK) {
                        boardCells[x][y].setImage(blackBishop);
                    } else {
                        boardCells[x][y].setImage(whiteBishop);
                    }
                } else if (currentPiece instanceof Knight) {
                    if (currentPiece.getColor() == Piece.BLACK) {
                        boardCells[x][y].setImage(blackKnight);
                    } else {
                        boardCells[x][y].setImage(whiteKnight);
                    }
                } else if (currentPiece instanceof King) {
                    if (currentPiece.getColor() == Piece.BLACK) {
                        boardCells[x][y].setImage(blackKing);
                    } else {
                        boardCells[x][y].setImage(whiteKing);
                    }
                } else if (currentPiece instanceof Queen) {
                    if (currentPiece.getColor() == Piece.BLACK) {
                        boardCells[x][y].setImage(blackQueen);
                    } else {
                        boardCells[x][y].setImage(whiteQueen);
                    }
                } else if (currentPiece instanceof Pawn) {
                    if (currentPiece.getColor() == Piece.BLACK) {
                        boardCells[x][y].setImage(blackPawn);
                    } else {
                        boardCells[x][y].setImage(whitePawn);
                    }
                } 
            }
        }
        // orient board to piece color
        if (model.getPlayerColor() == Piece.BLACK) {
            boardGrid.setRotate(180);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    boardCells[i][j].setRotate(-180);
                }
            }
            bottomPlayerIcon.setImage(blackKing);
            topPlayerIcon.setImage(whiteKing);
        } else {
            boardGrid.setRotate(0);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    boardCells[i][j].setRotate(0);
                }
            }
            bottomPlayerIcon.setImage(whiteKing);
            topPlayerIcon.setImage(blackKing);
        }
        // set proper turn indicator
        if (model.isPlayerTurn()) {
            bottomTurnLight.setImage(turnIconOn);
            topTurnLight.setImage(turnIconOff);
        } else {
            bottomTurnLight.setImage(turnIconOff);
            topTurnLight.setImage(turnIconOn);
        }
        // remove old status label and set new one (check/checkmate)
        while(statusBox.getChildren().size() > 0) {
            statusBox.getChildren().remove(0);
        }
        if (model.getBoard().isCheck() && model.getBoard().isMate()) {
            statusBox.getChildren().add(new Label("Checkmate!"));
        } else if (model.getBoard().isCheck()) {
            statusBox.getChildren().add(new Label("Check"));
        } else if (model.getBoard().isMate()) {
            statusBox.getChildren().add(new Label("Stalemate..."));
        }
        if (statusBox.getChildren().size() > 0) {
            ((Label)statusBox.getChildren().get(0)).setAlignment(Pos.CENTER);
            ((Label)statusBox.getChildren().get(0)).setFont(Font.font("System", 18));
        }
        
        // Adds the last move to the GUI's moves list
        appendMoveNotation();
        // Adds the last captured piece to the appropriate graveyart.
        addPieceToGraveyard();
    }


    /**
     * Handles the cell-clicked event by displaying the available moves for the
     * selected piece on the GUI and sending the move to the model.
     * @param e event of the clicked cell
     */
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
            
            Move newMove = new Move(moveFrom[0], moveFrom[1], moveTo[0], moveTo[1]);
            
            for (Move move : validMoves) {
                if (move.equals(newMove)) {
                    //newMove = move;
                    if (model.isMoveValid(move)) {
                        connection.sendMove(move);
                        model.setPlayerTurn(false);
                    }
                }
            }
        }
    }

    /**
     * Uses a move object to modify the state of the board model
     * @param move the move to be implemented
     */
    public void doMove(Move move) {
        if (move.isLongCastle()) {
            boardCells[3][move.getStartPosY()].setImage(
                    boardCells[0][move.getStartPosY()].getImage());
            boardCells[0][move.getStartPosY()].setImage(null);
        }

        if (move.isShortCastle()) {
            boardCells[5][move.getStartPosY()].setImage(
                    boardCells[7][move.getStartPosY()].getImage());
            boardCells[7][move.getStartPosY()].setImage(null);
        }

        boardCells[move.getEndPosX()][move.getEndPosY()].setImage(
                boardCells[move.getStartPosX()][move.getStartPosY()].getImage());
        boardCells[move.getStartPosX()][move.getStartPosY()].setImage(null);
        
        //Text temp = new Text(movesListObservable.size() + ". " + move.toString());
        //temp.setFont(new Font("System", 14));
        //movesListObservable.add(temp);

    }
    
    private Image getPieceImage(Class c, int color) {
        if (c == King.class) {
            if (color == Piece.WHITE) return Controller.whiteKing;
            else return Controller.blackKing;
        } else if (c == Queen.class) {
            if (color == Piece.WHITE) return Controller.whiteQueen;
            else return Controller.blackQueen;
        } else if (c == Rook.class) {
            if (color == Piece.WHITE) return Controller.whiteRook;
            else return Controller.blackRook;
        } else if (c == Knight.class) {
            if (color == Piece.WHITE) return Controller.whiteKnight;
            else return Controller.blackKnight;
        } else if (c == Bishop.class) {
            if (color == Piece.WHITE) return Controller.whiteBishop;
            else return Controller.blackBishop;
        } else if (c == Pawn.class) {
            if (color == Piece.WHITE) return Controller.whitePawn;
            else return Controller.blackPawn;
        } else return null;
    }

    private void appendMoveNotation() {
        // Adds the last move to the GUI's moves list
        Move lastMove = model.getBoard().popLastMove();
        if (lastMove != null) {
            String tempString;
            if (model.getBoard().getPiece(lastMove.getEndPosX(), lastMove.getEndPosY()).getColor() == Piece.WHITE) {
                tempString = (movesListObservable.size()+1) + ". " + lastMove.toString();
            } else {
                tempString = ((Text) movesListObservable.get(movesListObservable.size()-1)).textProperty().get();
                movesListObservable.remove(movesListObservable.size()-1);
                tempString += " " + lastMove.toString();
            }
            if (model.getBoard().isCheck()) {
                tempString += "+";
            }
            if (model.getBoard().isMate()) {
                tempString += "+";
            }
            Text tempText = new Text(tempString);
            tempText.setFont(Font.font("System", 16));
            movesListObservable.add(tempText);
        }
    }
    
    private void addPieceToGraveyard() {
        Piece capturedPiece = model.getBoard().popLastCapturedPiece();
        if (capturedPiece != null) {
            FlowPane whiteGraveyard;
            FlowPane blackGraveyard;
            if (model.getPlayerColor() == Piece.WHITE) {
                whiteGraveyard = bottomGraveyard;
                blackGraveyard = topGraveyard;
            } else {
                whiteGraveyard = topGraveyard;
                blackGraveyard = bottomGraveyard;
            }
            
            ImageView temp = new ImageView(getPieceImage(capturedPiece.getClass(), capturedPiece.getColor()));
            temp.setFitHeight(33);
            temp.setFitWidth(33);
            if (capturedPiece.getColor() == Piece.WHITE) {
                whiteGraveyard.getChildren().add(temp);
            } else {
                blackGraveyard.getChildren().add(temp);
            }
        }
    }
    
    @FXML
    private GridPane boardGrid;
    @FXML
    private VBox movesList;
    @FXML
    private ImageView topTurnLight;
    @FXML
    private ImageView bottomTurnLight;
    @FXML
    private ImageView topPlayerIcon;
    @FXML
    private ImageView bottomPlayerIcon;
    @FXML
    private VBox statusBox;
    @FXML
    private FlowPane topGraveyard;
    @FXML
    private FlowPane bottomGraveyard;
    
    private ImageView[][] boardCells = new ImageView[8][8];
    private ImageView selectedCell = null;
    private ObservableList<Node> movesListObservable;
    private Model model;
    private Move[] validMoves;
    private Connection connection;
    private Board currentBoard;
    
    private static final Image whitePawn = new Image("/mmchess/client/gui/images/wP.png", 46, 46, true, true);
    private static final Image whiteRook = new Image("/mmchess/client/gui/images/wR.png", 46, 46, true, true);
    private static final Image whiteBishop = new Image("/mmchess/client/gui/images/wB.png", 46, 46, true, true);
    private static final Image whiteKnight = new Image("/mmchess/client/gui/images/wH.png", 46, 46, true, true);
    private static final Image whiteKing = new Image("/mmchess/client/gui/images/wK.png", 46, 46, true, true);
    private static final Image whiteQueen = new Image("/mmchess/client/gui/images/wQ.png", 46, 46, true, true);
    private static final Image blackPawn = new Image("/mmchess/client/gui/images/bP.png", 46, 46, true, true);
    private static final Image blackRook = new Image("/mmchess/client/gui/images/bR.png", 46, 46, true, true);
    private static final Image blackBishop = new Image("/mmchess/client/gui/images/bB.png", 46, 46, true, true);
    private static final Image blackKnight = new Image("/mmchess/client/gui/images/bH.png", 46, 46, true, true);
    private static final Image blackKing = new Image("/mmchess/client/gui/images/bK.png", 46, 46, true, true);
    private static final Image blackQueen = new Image("/mmchess/client/gui/images/bQ.png", 46, 46, true, true);
    private static final Image turnIconOn = new Image("/mmchess/client/gui/images/TurnIcon_Green.png", 30, 30, true, true);
    private static final Image turnIconOff = new Image("/mmchess/client/gui/images/TurnIcon_Red.png", 30, 30, true, true);
    
}
