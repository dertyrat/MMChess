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

import java.util.ArrayList;

/**
 *
 * @author Matthew
 */
public class Bishop extends Piece {

    public Bishop(int xpos, int ypos, int color) {
        super(xpos, ypos, color);
    }

    @Override
    public Move[] getMoves(Board board) {
        // The valid moves will be accumulated in this list
        ArrayList<Move> movesList = new ArrayList<>();
        Piece other;
        
        // Checking for moves in "up-left" direction
        for (int i = 1; i < 8; i++) {
            // Checks for out-of-bounds
            if ((super.getXpos() - i) < 0 || (super.getYpos() - i) < 0) {
                break;
            }
            
            other = board.getPiece(super.getXpos() - i, super.getYpos() - i);
            
            if (other == null) {
                addMoveToList(movesList, super.getXpos() - i, super.getYpos() - i);
            } else if (other.getColor() != super.getColor()) {
                addMoveToList(movesList, super.getXpos() - i, super.getYpos() - i);
                break;
            } else {
                break;
            }
        }
        
        // Checking for moves in "up-right" direction
        for (int i = 1; i < 8; i++) {
            // Checks for out-of-bounds
            if ((super.getXpos() + i) > 7 || (super.getYpos() - i) < 0) {
                break;
            }
            
            other = board.getPiece(super.getXpos() + i, super.getYpos() - i);
            
            if (other == null) {
                addMoveToList(movesList, super.getXpos() + i, super.getYpos() - i);
            } else if (other.getColor() != super.getColor()) {
                addMoveToList(movesList, super.getXpos() + i, super.getYpos() - i);
                break;
            } else {
                break;
            }
        }
        
        // Checking for moves in "down-right" direction
        for (int i = 1; i < 8; i++) {
            // Checks for out-of-bounds
            if ((super.getXpos() + i) > 7 || (super.getYpos() + i) > 7) {
                break;
            }
            
            other = board.getPiece(super.getXpos() + i, super.getYpos() + i);
            
            if (other == null) {
                addMoveToList(movesList, super.getXpos() + i, super.getYpos() + i);
            } else if (other.getColor() != super.getColor()) {
                addMoveToList(movesList, super.getXpos() + i, super.getYpos() + i);
                break;
            } else {
                break;
            }
        }
        
        // Checking for moves in "down-left" direction
        for (int i = 1; i < 8; i++) {
            // Checks for out-of-bounds
            if ((super.getXpos() - i) < 0 || (super.getYpos() + i) > 7) {
                break;
            }
            
            other = board.getPiece(super.getXpos() - i, super.getYpos() + i);
            
            if (other == null) {
                addMoveToList(movesList, super.getXpos() - i, super.getYpos() + i);
            } else if (other.getColor() != super.getColor()) {
                addMoveToList(movesList, super.getXpos() - i, super.getYpos() + i);
                break;
            } else {
                break;
            }
        }
        
        // Cannot cast movesList.toArray(), so the following is necessary
        Move[] moves = new Move[movesList.size()];
        for (int i = 0; i < movesList.size(); i++) {
            moves[i] = movesList.get(i);
        }
        return moves;
    }
}
