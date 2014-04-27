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
public class Move {

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
    
    public Move(Class pieceType, int startPosX, int startPosY, int endPosX, int endPosY) {
        this(startPosX, startPosY, endPosX, endPosY);
        this.pieceType = pieceType;
        /*
        if (Bishop.class == pieceType) this.pieceType = 'B';
        else if (King.class == pieceType) this.pieceType = 'K';
        else if (Knight.class == pieceType) this.pieceType = 'N';
        else if (Pawn.class == pieceType) this.pieceType = '\u0000';
        else if (Queen.class == pieceType) this.pieceType = 'Q';
        else if (Rook.class == pieceType) this.pieceType = 'R';
        */
    }

    @Override
    public boolean equals(Object o) {
        try {
            Move other = (Move)o;
            if (this.hashCode() == other.hashCode()) return true;
            else return false;
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.startPosX;
        hash = 97 * hash + this.startPosY;
        hash = 97 * hash + this.endPosX;
        hash = 97 * hash + this.endPosY;
        return hash;
    }

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
    
    public int getStartPosX() {
        return startPosX;
    }

    public int getStartPosY() {
        return startPosY;
    }

    public int getEndPosX() {
        return endPosX;
    }

    public int getEndPosY() {
        return endPosY;
    }
    
    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    public boolean isLongCastle() {
        return longCastle;
    }

    public void setLongCastle(boolean longCastle) {
        this.longCastle = longCastle;
    }

    public boolean isShortCastle() {
        return shortCastle;
    }

    public void setShortCastle(boolean shortCastle) {
        this.shortCastle = shortCastle;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

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
