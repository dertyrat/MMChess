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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

}
