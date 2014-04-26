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

import java.util.ArrayList;

/**
 *
 * @author Matthew
 */
public class Rook extends Piece {

    public Rook(int xpos, int ypos, int color) {
        super(xpos, ypos, color);
        canCastle = true;
    }
    
    public boolean canCastle() {
        return canCastle;
    }
    
    @Override
    public void setYpos(int ypos) { 
        super.ypos = ypos;
        this.canCastle = false;
    }
    
    @Override
    public void setXpos(int xpos) {
        this.xpos = xpos;
        this.canCastle = false;
    }

    @Override
    public Move[] getMoves(Board board) {
        // The valid moves will be accumulated in this list
        ArrayList<Move> movesList = new ArrayList<>();
        Piece other = null;
        
        // Checking for moves going "up"
        for (int y = super.getYpos()-1; y >= 0; y--) {
            other = board.getPiece(super.getXpos(), y);
            if (other == null) { // Valid Move
                addMoveToList(movesList, super.getXpos(), y);
            } else {
                if (other.getColor() != super.getColor()) { // Valid Move
                    addMoveToList(movesList, super.getXpos(), y);
                    break; // cannot move further in this direction
                } else { // Invalid Move and cannot move past
                    break;
                }
            }
        }
        
        // Checking for moves going "down"
        for (int y = super.getYpos()+1; y <= 7; y++) {
            other = board.getPiece(super.getXpos(), y);
            if (other == null) { // Valid Move
                addMoveToList(movesList, super.getXpos(), y);
            } else {
                if (other.getColor() != super.getColor()) { // Valid Move
                    addMoveToList(movesList, super.getXpos(), y);
                    break; // cannot move further in this direction
                } else { // Invalid Move and cannot move past
                    break;
                }
            }
        }
        
        // Checking for moves going "left"
        for (int x = super.getXpos()-1; x >= 0; x--) {
            other = board.getPiece(x, super.getYpos());
            if (other == null) { // Valid Move
                addMoveToList(movesList, x, super.getYpos());
            } else {
                if (other.getColor() != super.getColor()) { // Valid Move
                    addMoveToList(movesList, x, super.getYpos());
                    break; // cannot move further in this direction
                } else { // Invalid Move and cannot move past
                    break;
                }
            }
        }
        
        // Checking for moves going "right"
        for (int x = super.getXpos()+1; x >= 0; x++) {
            other = board.getPiece(x, super.getYpos());
            if (other == null) { // Valid Move
                addMoveToList(movesList, x, super.getYpos());
            } else {
                if (other.getColor() != super.getColor()) { // Valid Move
                    addMoveToList(movesList, x, super.getYpos());
                    break; // cannot move further in this direction
                } else { // Invalid Move and cannot move past
                    break;
                }
            }
        }
        
        // Cannot cast movesList.toArray(), so the following is necessary
        Move[] moves = new Move[movesList.size()];
        for (int i = 0; i < movesList.size(); i++) {
            moves[i] = movesList.get(i);
        }
        return moves;
    }

    
    private boolean canCastle;
}
