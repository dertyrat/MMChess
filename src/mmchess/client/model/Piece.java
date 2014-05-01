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
 * The Piece class defines the common behavior of all of the chess pieces.
 * @author Matthew
 */
public abstract class Piece {
    
    /** Defines the black color for pieces */
    public static final int BLACK = 0;
    /** Defines the white color for pieces */
    public static final int WHITE = 1;
    
    /**
     * Builds the base properties of the piece.
     * @param xpos the position of the piece on the board on the x-axis
     * @param ypos the position of the piece on the board on the y-axis
     * @param color the color of the piece (Use the static properties belonging to Piece
     */
    public Piece(int xpos, int ypos, int color) {
        this.color = color;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    /**
     * This method generates an array of moves based on the type of the current
     * piece, its color, its position on the board, and its surrounding pieces.
     * The array of moves return will all be legal moves for this piece, but
     * should be checked for rules not involving the direct interaction between
     * this piece and any of the other pieces, such as moves that would create a
     * check for the current player.
     * @param board a copy of the board the piece inhabits
     * @return an array of moves
     */
    public abstract Move[] getMoves(Board board);
    
    /**
     * This is a utility method which makes it easier for objects that extend
     * Piece to create a new move and add it to an ArrayList.
     * @param movesList List of moves to append the new move to
     * @param x the final x-axis position of the move
     * @param y the final y-acis position of the move
     */
    protected void addMoveToList(ArrayList<Move> movesList, int x, int y) {
        movesList.add(new Move(this.getClass(), this.getXpos(), this.getYpos(), x, y));
    }
    
    /**
     * Gets the x-axis position of the piece
     * @return x-axis position
     */
    public int getXpos() { return xpos; }

    /**
     * Gets the y-axis position of the piece
     * @return y-axis position
     */
    public int getYpos() { return ypos; }

    /**
     * gets the color of the piece
     * @return color 
     */
    public int getColor() { return color; }

    /**
     * Sets the x-axis position of the piece
     * @param ypos  y-axis position
     */
    public void setYpos(int ypos) { this.ypos = ypos; }

    /**
     * Sets the x-axis position of the piece
     * @param xpos x-axis position
     */
    public void setXpos(int xpos) { this.xpos = xpos; }
    
    private int xpos;
    private int ypos;
    
    private final int color;
}
