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

package mmchess.client.model;

/**
 *
 * @author Matthew
 */
public class Model {
    /**
     * Constructor for model, initializes fields
     */
    public Model () {
        gameBoard = new Board();
        playerTurn = false;
    }

    /**
     * Puts the model in the state of a new game
     */
    public void newGame() {
        gameBoard.resetBoard();
        playerTurn = false;
    }

    /**
     * Uses a move object to modify the state of the board model
     * @param move the move to be implemented
     * @return true if successful
     */
    public boolean doMove(Move move) {
        return gameBoard.doMove(move);
    }

    /**
     * Returns a list of valid moves from the piece on the given location
     * @param startPosX the horizontal array index of the piece (0-7)
     * @param startPosY the vertical array index of the piece (0-7)
     * @return an array of valid moves for the piece at that location
     */
    public Move[] getValidMoves(int startPosX, int startPosY) {
        return gameBoard.getValidMoves(startPosX, startPosY);
    }

    /**
     * Checks whether or not a move is valid for current board model state
     * @param move the move to be validated
     * @return true if the given move is valid
     */
    public boolean isMoveValid(Move move) {
        if (playerTurn && gameBoard.getPiece(move.getStartPosX(), move.getStartPosY()).getColor() == playerColor) {
            return gameBoard.isMoveValid(move);
        } else return false;
    }

    /**
     * Sets color this player will be using
     * @param playerColor the color for this player
     */
    public void setPlayerColor(int playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * Sets the player's turn flag
     * @param playerTurn true if now it's this player's turn
     */
    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    /**
     * Returns the board model in it's current state
     * @return the board model
     */
    public Board getBoard() {
        return gameBoard;
    }

    /**
     * Returns this player's piece color
     * @return the piece color for this player
     */
    public int getPlayerColor() {
        return playerColor;
    }

    /**
     * Returns whether or not it's this player's turn
     * @return true if it's this player's turn
     */
    public boolean isPlayerTurn() {
        return playerTurn;
    }
        
    private final Board gameBoard;
    private int playerColor;
    private boolean playerTurn;
}
