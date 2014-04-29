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
    
    public Model () {
        gameBoard = new Board();
        playerTurn = false;
    }
    
    public void newGame() {
        gameBoard.resetBoard();
        playerTurn = false;
    }
    
    public boolean doMove(Move move) {
        return gameBoard.doMove(move);
    }
    
    public Move[] getValidMoves(int startPosX, int startPosY) {
        return gameBoard.getValidMoves(startPosX, startPosY);
    }
    
    public boolean isMoveValid(Move move) {
        if (playerTurn && gameBoard.getPiece(move.getStartPosX(), move.getStartPosY()).getColor() == playerColor) {
            return gameBoard.isMoveValid(move);
        } else return false;
    }
    
    public void setPlayerColor(int playerColor) {
        this.playerColor = playerColor;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }
    
    public Board getBoard() {
        return gameBoard;
    }
    
    public int getPlayerColor() {
        return playerColor;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }
        
    private final Board gameBoard;
    private int playerColor;
    private boolean playerTurn;
}
