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

package mmchess.server.model;

/**
 *
 * @author Matthew
 */
public class Model {
    
    public Model () {
        gameBoard = new Board();
    }
    
    public void newGame() {
        gameBoard.resetBoard();
    }
    
    public boolean doMove(Move move) {
        return gameBoard.doMove(move);
    }
    
    public Move[] getValidMoves(int startPosX, int startPosY) {
        return gameBoard.getPiece(startPosX, startPosY).getMoves(gameBoard);
    }
    
    private final Board gameBoard;
}
