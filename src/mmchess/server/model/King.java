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
public class King extends Piece {

    public King(int xpos, int ypos, int color) {
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
        
        // Checks for regular move
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if ( (x != 0 || y != 0)
                        && (super.getXpos() + x) >= 0 && (super.getXpos() + x) < 8
                        && (super.getYpos() + y) >= 0 && (super.getYpos() + y) < 8) {
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
        
        // Checks for castleing
        if (this.canCastle) {
            Piece[] row = new Piece[8];
            for (int i = 0; i < 8; i++) {
                row[i] = board.getPiece(i, super.getYpos());
            }
            
            // Checks for Queen-side castle (long castle)
            if (row[0] != null && row[0].getClass() == Rook.class) {
                if ( ((Rook)row[0]).canCastle() ) {
                    boolean longCastle = true;
                    for (int i = 1; i < 4; i++) {
                        if (row[i] != null) {
                            longCastle = false;
                            break;
                        }
                    }
                    if (longCastle) {
                        super.addMoveToList(movesList, 2, super.getYpos());
                        movesList.get(movesList.size()-1).setLongCastle(true);
                    }
                }
            }
            
            // Checks for King-side castle (short castle)
            if (row[7] != null && row[7].getClass() == Rook.class) {
                if ( ((Rook)row[7]).canCastle() ) {
                    boolean shortCastle = true;
                    for (int i = 5; i < 7; i++) {
                        if (row[i] != null) {
                            shortCastle = false;
                            break;
                        }
                    }
                    if (shortCastle) {
                        super.addMoveToList(movesList, 6, super.getYpos());
                        movesList.get(movesList.size()-1).setShortCastle(true);
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
    
    private boolean canCastle;
}
