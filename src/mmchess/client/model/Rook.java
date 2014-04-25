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
    }

    @Override
    public Move[] getMoves() {
        ArrayList<Move> movesList = new ArrayList<>();
        // Generates vertical moves
        for (int i = -7; i < 8; i++) {
            int endPosX = super.getXpos() + i;
            int endPosY = super.getYpos() + i;
            if (endPosX >= 0 && endPosX < 8 && endPosX != super.getXpos()) {
                movesList.add(new Move(super.getXpos(), super.getYpos(), endPosX, super.getYpos()));
            }
            if (endPosY >= 0 && endPosY < 8 && endPosY != super.getYpos()) {
                movesList.add(new Move(super.getXpos(), super.getYpos(), super.getYpos(), endPosY));
            }
        }
        return (Move[]) movesList.toArray();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
