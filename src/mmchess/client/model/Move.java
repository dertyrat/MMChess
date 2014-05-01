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

import java.io.Serializable;

/**
 * The move tracks the starting and ending of a chess move as well as all of the
 * special types of moves (such as a capture, castle, or check).
 * @author Matthew McGuire
 */
public class Move implements Serializable {

    /**
     * Constructor of the Move for when the Piece type is not known.
     * @param startPosX starting x-axis position
     * @param startPosY starting y-axis position
     * @param endPosX ending x-axis position
     * @param endPosY ending y-axis position
     */
    public Move(int startPosX, int startPosY, int endPosX, int endPosY) {
        this.startPosX = startPosX;
        this.startPosY = startPosY;
        this.endPosX = endPosX;
        this.endPosY = endPosY;
        capture = false;
        longCastle = false;
        shortCastle = false;
        check = false;
        checkMate = false;
        
        rowNames = new char[]{'8','7','6','5','4','3','2','1'};
        colNames = new char[]{'a','b','c','d','e','f','g','h'};
    }
    /**
     * Constructor of the Move for when the Piece type is not known.
     * @param pieceType the Class of the piece that is instantiating the move
     * @param startPosX starting x-axis position
     * @param startPosY starting y-axis position
     * @param endPosX ending x-axis position
     * @param endPosY ending y-axis position
     */
    public Move(Class pieceType, int startPosX, int startPosY, int endPosX, int endPosY) {
        this(startPosX, startPosY, endPosX, endPosY);
        this.pieceType = pieceType;
    }
    
    /**
     * Checks to see of two moves are the same based on their starting and 
     * ending x-axis and y-axis positions.
     * @param o other move
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass() == Move.class)
        {
            Move other = (Move)o;
            if (this.hashCode() == other.hashCode()) return true;
        }
        return false;
    }
    
    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     * @see java.lang.Object#hashCode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.startPosX;
        hash = 97 * hash + this.startPosY;
        hash = 97 * hash + this.endPosX;
        hash = 97 * hash + this.endPosY;
        return hash;
    }

    /**
     * Returns a string representation of the using a loose interpretation of
     * the expanded algebraic method of representing chess moves.
     * @return A string representation of the move.
     */
    @Override
    public String toString() {
        if (shortCastle) return "O-O";
        else if (longCastle) return "O-O-O";
        else {
            StringBuilder output = new StringBuilder();
        
            // Adds Piece Type
            if (Bishop.class == pieceType) output.append("B");
            else if (King.class == pieceType) output.append("K");
            else if (Knight.class == pieceType) output.append("N");
            else if (Queen.class == pieceType) output.append("Q");
            else if (Rook.class == pieceType) output.append("R");

            // Adding starting cell
            output.append(colNames[startPosX]);
            output.append(rowNames[startPosY]);

            // Adding capture flag
            if (capture) output.append('x');

            // Adding ending cell
            output.append(colNames[endPosX]);
            output.append(rowNames[endPosY]);
            
            return output.toString();
        }
    }
    
    /**
     * Gets the starting x-axis position
     * @return x-axis position
     */
    public int getStartPosX() {
        return startPosX;
    }

    /**
     * Gets the starting y-axis position
     * @return y-axis position
     */
    public int getStartPosY() {
        return startPosY;
    }

    /**
     * Gets the final x-axis position
     * @return x-axis position
     */
    public int getEndPosX() {
        return endPosX;
    }

    /**
     * Gets the final y-axis position
     * @return y-axis position
     */
    public int getEndPosY() {
        return endPosY;
    }
    
    /**
     * Returns whether the capture flag has been set.
     * @return the capture flag
     */
    public boolean isCapture() {
        return capture;
    }

    /**
     * Returns whether the long-castle flag has been set.
     * @return the long-castle flag
     */
    public boolean isLongCastle() {
        return longCastle;
    }

    /**
     * Returns whether the short-castle flag has been set.
     * @return the short-castle flag
     */
    public boolean isShortCastle() {
        return shortCastle;
    }

    /**
     * Returns whether the Check flag has been set.
     * @return the Check flag
     */
    public boolean isCheck() {
        return check;
    }

    /**
     * Returns whether the Checkmate flag has been set.
     * @return the Checkmate flag
     */
    public boolean isCheckMate() {
        return checkMate;
    }
    
    /**
     * Sets the Capture flag.
     * @param capture value to set the capture flag
     */
    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    /**
     * Sets the Long-Castle flag.
     * @param longCastle value to set the Long-Castle flag
     */
    public void setLongCastle(boolean longCastle) {
        this.longCastle = longCastle;
    }

    /**
     * Sets the Short-Castle flag.
     * @param shortCastle value to set the Short-Castle flag
     */
    public void setShortCastle(boolean shortCastle) {
        this.shortCastle = shortCastle;
    }

    /**
     * Sets the Check flag.
     * @param check value to set the Check flag
     */
    public void setCheck(boolean check) {
        this.check = check;
    }

    /**
     * Sets the CheckMate flag.
     * @param checkMate value to set the Checkmate flag
     */
    public void setCheckMate(boolean checkMate) {
        this.checkMate = checkMate;
    }
    
    private final int startPosX;
    private final int startPosY;
    private final int endPosX;
    private final int endPosY;
    private boolean capture;
    private boolean longCastle;
    private boolean shortCastle;
    private boolean check;
    private boolean checkMate;
    private Class pieceType;
    private char[] rowNames;
    private char[] colNames;
}
