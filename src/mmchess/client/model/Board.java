package mmchess.client.model;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Board implements Cloneable {
    /**
     * Constructor for the board model, initializes the fields
     */
    public Board() {
        // Setting up default piece placement
        boardGrid = new Piece[8][8];
        resetBoard();
        check = false;
        mate = false;
        lastMove = null;
        lastCapture = null;
    }

    /**
     * Returns a deep copy of the board model
     * @return a deep copy of the board model
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Board newBoard = new Board();
        
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (boardGrid[x][y] == null) {
                    newBoard.boardGrid[x][y] = null;
                } else {
                    if (boardGrid[x][y] instanceof Pawn) {
                        newBoard.boardGrid[x][y] = new Pawn(
                                boardGrid[x][y].getXpos(), 
                                boardGrid[x][y].getYpos(), 
                                boardGrid[x][y].getColor()
                        );
                    } else if (boardGrid[x][y] instanceof Rook) {
                        newBoard.boardGrid[x][y] = new Rook(
                                boardGrid[x][y].getXpos(), 
                                boardGrid[x][y].getYpos(), 
                                boardGrid[x][y].getColor()
                        );
                    } else if (boardGrid[x][y] instanceof Bishop) {
                        newBoard.boardGrid[x][y] = new Bishop(
                                boardGrid[x][y].getXpos(), 
                                boardGrid[x][y].getYpos(), 
                                boardGrid[x][y].getColor()
                        );
                    } else if (boardGrid[x][y] instanceof Knight) {
                        newBoard.boardGrid[x][y] = new Knight(
                                boardGrid[x][y].getXpos(), 
                                boardGrid[x][y].getYpos(), 
                                boardGrid[x][y].getColor()
                        );
                    } else if (boardGrid[x][y] instanceof King) {
                        newBoard.boardGrid[x][y] = new King(
                                boardGrid[x][y].getXpos(), 
                                boardGrid[x][y].getYpos(), 
                                boardGrid[x][y].getColor()
                        );
                    } else if (boardGrid[x][y] instanceof Queen) {
                        newBoard.boardGrid[x][y] = new Queen(
                                boardGrid[x][y].getXpos(), 
                                boardGrid[x][y].getYpos(), 
                                boardGrid[x][y].getColor()
                        );
                    }
                }
            }
        }
        
        return newBoard;
    }

    /**
     * Gets the piece in the given location
     * @param xPos the horizontal array index of the piece (0-7)
     * @param yPos the vertical array index of the piece (0-7)
     * @return the piece object at the given location
     */
    public Piece getPiece(int xPos, int yPos) {
        return boardGrid[xPos][yPos];
    }

    /**
     * Uses a move object to modify the state of the board model
     * @param move the move to be implemented
     * @return true if successful
     */
    public boolean doMove(Move move) {
        if (move.isCapture()) {
            lastCapture = boardGrid[move.getEndPosX()][move.getEndPosY()];
        } else if (move.isLongCastle()) {
            boardGrid[3][move.getStartPosY()] = boardGrid[0][move.getStartPosY()];
            boardGrid[0][move.getStartPosY()] = null;
            boardGrid[3][move.getEndPosY()].setXpos(3);
        } else if (move.isShortCastle()) {
            boardGrid[5][move.getStartPosY()] = boardGrid[7][move.getStartPosY()];
            boardGrid[7][move.getStartPosY()] = null;
            boardGrid[5][move.getEndPosY()].setXpos(5);
        }
        // if pawn is moving to end of board, convert to pawn to queen
        if ((move.getEndPosY() == 0 || move.getEndPosY() == 7) &&
             boardGrid[move.getStartPosX()][move.getStartPosY()] instanceof Pawn)
        {
            boardGrid[move.getStartPosX()][move.getStartPosY()] =
                    new Queen(move.getStartPosX(), move.getStartPosY(), boardGrid[move.getStartPosX()][move.getStartPosY()].getColor());
        }

        boardGrid[move.getEndPosX()][move.getEndPosY()] = boardGrid[move.getStartPosX()][move.getStartPosY()];
        boardGrid[move.getStartPosX()][move.getStartPosY()] = null;
        boardGrid[move.getEndPosX()][move.getEndPosY()].setXpos(move.getEndPosX());
        boardGrid[move.getEndPosX()][move.getEndPosY()].setYpos(move.getEndPosY());
        
        lastMove = move;
        return true;
    }

    /**
     * Check whether or not a given move is valid for the current state of the board model
     * @param move the move to be validated
     * @return true if the move is valid
     */
    public boolean isMoveValid(Move move) {
        Move[] validMoves = getPiece(move.getStartPosX(), move.getStartPosY()).getMoves(this);
        for (Move validMove : validMoves) {
            if (validMove.equals(move)) return true;
        }
        return false;
    }

    /**
     * Generates a list of valid moves from the piece on the given location
     * @param startPosX the horizontal array index of the piece (0-7)
     * @param startPoxY the vertical array index of the piece (0-7)
     * @return an array of valid moves for the piece at that location
     */
    public Move[] getValidMoves(int startPosX, int startPoxY) {
        try {
            int currentColor = this.getPiece(startPosX, startPoxY).getColor();
            Move[] validMoves = boardGrid[startPosX][startPoxY].getMoves((Board) this.clone());

            return refineMovesList(validMoves, currentColor);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Move[0];
    }

    private Move[] refineMovesList(Move[] validMoves, int currentColor) throws CloneNotSupportedException {
        ArrayList<Move> movesList = new ArrayList<>();
        
        for (int i = 0; i < validMoves.length; i++) {
            Board nextState = (Board)this.clone();
            nextState.doMove(validMoves[i]);
            King myKing = null;
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    if (nextState.getPiece(x, y) != null
                            && nextState.getPiece(x, y) instanceof King
                            && nextState.getPiece(x, y).getColor() == currentColor) {
                        myKing = (King) nextState.getPiece(x, y);
                    }
                }
            }
            
            boolean moveGood = true;
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    if (nextState.getPiece(x, y) != null
                            && nextState.getPiece(x, y).getColor() != currentColor) {
                        for (Move nextMove : nextState.getPiece(x, y).getMoves(nextState)) {
                            if (nextMove.getEndPosX() == myKing.getXpos()) {
                                if (nextMove.getEndPosY() == myKing.getYpos()) {
                                    moveGood = false;
                                }
                            }
                        }
                    }
                }
            }
            
            if (moveGood) {
                movesList.add(validMoves[i]);
            }
        }
        
        Move[] refinedMoves = new Move[movesList.size()];
        for (int i = 0; i < movesList.size(); i++) {
            refinedMoves[i] = movesList.get(i);
        }
        
        return refinedMoves;
    }

    /**
     * Sets the board model state to that of a new game
     */
    public void resetBoard() {
        boardGrid[0][0] = new Rook (0, 0, Piece.BLACK);
        boardGrid[1][0] = new Knight (1, 0, Piece.BLACK);
        boardGrid[2][0] = new Bishop (2, 0, Piece.BLACK);
        boardGrid[3][0] = new Queen (3, 0, Piece.BLACK);
        boardGrid[4][0] = new King (4, 0, Piece.BLACK);
        boardGrid[5][0] = new Bishop (5, 0, Piece.BLACK);
        boardGrid[6][0] = new Knight (6, 0, Piece.BLACK);
        boardGrid[7][0] = new Rook (7, 0, Piece.BLACK);
        
        for (int i = 0; i < 8; i++) {
            boardGrid[i][1] = new Pawn (i, 1, Piece.BLACK);
            boardGrid[i][6] = new Pawn (i, 6, Piece.WHITE);
        }
        
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                boardGrid[j][i] = Piece.EMPTY;
            }
        }
        
        boardGrid[0][7] = new Rook (0, 7, Piece.WHITE);
        boardGrid[1][7] = new Knight (1, 7, Piece.WHITE);
        boardGrid[2][7] = new Bishop (2, 7, Piece.WHITE);
        boardGrid[3][7] = new Queen (3, 7, Piece.WHITE);
        boardGrid[4][7] = new King (4, 7, Piece.WHITE);
        boardGrid[5][7] = new Bishop (5, 7, Piece.WHITE);
        boardGrid[6][7] = new Knight (6, 7, Piece.WHITE);
        boardGrid[7][7] = new Rook (7, 7, Piece.WHITE);
    }

    /**
     * Sets the check flag based on the current state of the board
     */
    public void setCheckFlag() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (boardGrid[x][y] != null) {
                    for (Move move : this.getValidMoves(x, y)) {
                        if (boardGrid[move.getEndPosX()][move.getEndPosY()] != null
                                && boardGrid[move.getEndPosX()][move.getEndPosY()] instanceof King
                                && boardGrid[move.getEndPosX()][move.getEndPosY()].getColor() !=
                                   boardGrid[move.getStartPosX()][move.getStartPosY()].getColor()) {
                            check = true;
                            return;
                        }
                    }
                }
            }
        }
        check = false;
    }

    /**
     * Sets the mate flag for a given piece color based on the current state of the board
     * @param pieceColor the color to check for mate for
     */
    public void setMateFlag(int pieceColor) {
        if (pieceColor == Piece.WHITE) {
            mate = false;
        }
        if (!mate) {
            for (int x = 0; x < 8; ++x) {
                for (int y = 0; y < 8; ++y) {
                    if (boardGrid[x][y] != null &&
                            boardGrid[x][y].getColor() == pieceColor) {
                        if (this.getValidMoves(x, y).length > 0) {
                            mate = false;
                            return;
                        }
                    }
                }
            }
        }

        mate = true;
    }

    /**
     * Returns whether or not either king is in check
     * @return true if either king is in check
     */
    public boolean isCheck() {
        return check;
    }

    /**
     * Returns whether or not either player cannot move
     * @return true if either player cannot move
     */
    public boolean isMate() {
        return mate;
    }
    
    /**
     * Returns the last move that was done by either player and resets the value
     * to null.
     * @return Move
     */
    public Move popLastMove() {
        Move temp = lastMove;
        lastMove = null;
        return temp;
    }
    
    public Piece popLastCapturedPiece() {
        Piece temp = lastCapture;
        lastCapture = null;
        return temp;
    }
    
    private Piece[][] boardGrid;
    private boolean check;
    private boolean mate;
    private Move lastMove;
    private Piece lastCapture;
}