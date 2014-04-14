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
public class Board {
    
    Board() {
        // Setting up default piece placement
        boardGrid = new Piece[8][8];
        resetBoard();
        
    }
    
    

    private void resetBoard() {
        boardGrid[0][0] = new Rook (0, 0, Piece.BLACK);
        boardGrid[0][1] = new Knight (0, 1, Piece.BLACK);
        boardGrid[0][2] = new Bishop (0, 2, Piece.BLACK);
        boardGrid[0][3] = new Queen (0, 3, Piece.BLACK);
        boardGrid[0][4] = new King (0, 4, Piece.BLACK);
        boardGrid[0][5] = new Bishop (0, 5, Piece.BLACK);
        boardGrid[0][6] = new Knight (0, 6, Piece.BLACK);
        boardGrid[0][7] = new Rook (0, 7, Piece.BLACK);
        
        for (int i = 0; i < 8; i++) {
            boardGrid[1][i] = new Pawn (1, i, Piece.BLACK);
            boardGrid[6][i] = new Pawn (6, i, Piece.WHITE);
        }
        
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                boardGrid[i][j] = Piece.EMPTY;
            }
        }
        
        boardGrid[7][0] = new Rook (0, 0, Piece.WHITE);
        boardGrid[7][1] = new Knight (0, 1, Piece.WHITE);
        boardGrid[7][2] = new Bishop (0, 2, Piece.WHITE);
        boardGrid[7][3] = new Queen (0, 3, Piece.WHITE);
        boardGrid[7][4] = new King (0, 4, Piece.WHITE);
        boardGrid[7][5] = new Bishop (0, 5, Piece.WHITE);
        boardGrid[7][6] = new Knight (0, 6, Piece.WHITE);
        boardGrid[7][7] = new Rook (0, 7, Piece.WHITE);
    }
    
    private Piece[][] boardGrid;
}