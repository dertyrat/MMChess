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
 * Defines the Knight and its behavior.
 * @author Matthew McGuire
 */
public class Knight extends Piece {

    /**
     * Constructs the Knight
     * @param xpos x-axis position
     * @param ypos y-axis position
     * @param color piece's color
     */
    public Knight(int xpos, int ypos, int color) {
        super(xpos, ypos, color);
    }

    @Override
    public Move[] getMoves(Board board) {
        // The valid moves will be accumulated in this list
        ArrayList<Move> movesList = new ArrayList<>();
        Piece other;
        
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                if (x != y && x != -y && x != 0 && y != 0
                        && (super.getXpos() + x) < 8 && (super.getXpos() + x) >= 0
                        && (super.getYpos() + y) < 8 && (super.getYpos() + y) >= 0) {
                    other = board.getPiece(super.getXpos()+x, super.getYpos()+y);
                    if (other == null) {
                        super.addMoveToList(movesList, super.getXpos()+x, super.getYpos()+y);
                    } else if (other.getColor() != super.getColor()) {
                        super.addMoveToList(movesList, super.getXpos()+x, super.getYpos()+y);
                        movesList.get(movesList.size()-1).setCapture(true);
                    }
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

}
