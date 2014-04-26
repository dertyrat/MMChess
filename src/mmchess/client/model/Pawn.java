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
public class Pawn extends Piece {

    public Pawn(int xpos, int ypos, int color) {
        super(xpos, ypos, color);
        
        if (super.getColor() == Piece.WHITE) {
            DIRECTION = -1;
        } else {
            DIRECTION = 1;
        }
    }

    @Override
    public Move[] getMoves(Board board) {
        // The valid moves will be accumulated in this list
        ArrayList<Move> movesList = new ArrayList<>();
        
        // Checks for first move
        if (super.getColor() == Piece.WHITE && super.getYpos() == 6) {
            if (board.getPiece(super.getXpos(), super.getYpos() + DIRECTION) == null
                    && board.getPiece(super.getXpos(), super.getYpos() + (2 * DIRECTION)) == null) {
                addMoveToList(movesList, super.getXpos(), 4);
            }
        }
        
        if (super.getYpos() + DIRECTION < 8 && super.getYpos() + DIRECTION >= 0) {
            // Checks for normal move
            if (board.getPiece(super.getXpos(), super.getYpos() + DIRECTION) == null) {
                addMoveToList(movesList, super.getXpos(), super.getXpos() + DIRECTION);
            }
            // Checks for capture move to the left
            if (super.getXpos() > 0 
                    && board.getPiece(super.getXpos() - 1, super.getYpos() + DIRECTION) != null
                    && board.getPiece(super.getXpos() - 1, super.getYpos() + DIRECTION).getColor() != super.getColor()) {
                addMoveToList(movesList, super.getXpos() - 1, super.getYpos() + DIRECTION);
            }
            // Checks for capture move to the right
            if (super.getXpos() < 7 
                    && board.getPiece(super.getXpos() + 1, super.getYpos() + DIRECTION) != null
                    && board.getPiece(super.getXpos() + 1, super.getYpos() + DIRECTION).getColor() != super.getColor()) {
                addMoveToList(movesList, super.getXpos() + 1, super.getYpos() + DIRECTION);
            }
        }
        
        // Cannot cast movesList.toArray(), so the following is necessary
        Move[] moves = new Move[movesList.size()];
        for (int i = 0; i < movesList.size(); i++) {
            moves[i] = movesList.get(i);
        }
        return moves;
    }
    

    
    private final int DIRECTION;
}
