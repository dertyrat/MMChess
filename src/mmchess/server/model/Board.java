package mmchess.server.model;

public class Board {
    
    public Board() {
        // Setting up default piece placement
        boardGrid = new Piece[8][8];
        resetBoard();
    }

    public Piece getPiece(int xPos, int yPos) {
        return boardGrid[xPos][yPos];
    }

    public boolean doMove(Move move) {
        if (isMoveValid(move)) {

            if (move.isCapture()) {
                //TODO: if capture move, add captured piece to capture box/list
            }

            if (move.isCheck()) {
                //TODO: write code here
            }

            if (move.isCheckMate()) {
                //TODO: write code here
            }

            if (move.isLongCastle()) {
                boardGrid[3][move.getStartPosY()] = boardGrid[0][move.getStartPosY()];
                boardGrid[0][move.getStartPosY()] = null;
                boardGrid[3][move.getEndPosY()].setXpos(3);
            }

            if (move.isShortCastle()) {
                boardGrid[5][move.getStartPosY()] = boardGrid[7][move.getStartPosY()];
                boardGrid[7][move.getStartPosY()] = null;
                boardGrid[5][move.getEndPosY()].setXpos(5);
            }

            boardGrid[move.getEndPosX()][move.getEndPosY()] = boardGrid[move.getStartPosX()][move.getStartPosY()];
            boardGrid[move.getStartPosX()][move.getStartPosY()] = null;
            boardGrid[move.getEndPosX()][move.getEndPosY()].setXpos(move.getEndPosX());
            boardGrid[move.getEndPosX()][move.getEndPosY()].setYpos(move.getEndPosY());

            return true;
        } else return false;
    }

    public boolean isMoveValid(Move move) {
        Move[] validMoves = getPiece(move.getStartPosX(), move.getStartPosY()).getMoves(this);
        for (Move validMove : validMoves) {
            if (validMove.equals(move)) return true;
        }
        return false;
    }

    public void resetBoard() {
        boardGrid[0][0] = new Rook(0, 0, Piece.BLACK);
        boardGrid[1][0] = new Knight(1, 0, Piece.BLACK);
        boardGrid[2][0] = new Bishop(2, 0, Piece.BLACK);
        boardGrid[3][0] = new Queen(3, 0, Piece.BLACK);
        boardGrid[4][0] = new King(4, 0, Piece.BLACK);
        boardGrid[5][0] = new Bishop(5, 0, Piece.BLACK);
        boardGrid[6][0] = new Knight(6, 0, Piece.BLACK);
        boardGrid[7][0] = new Rook(7, 0, Piece.BLACK);

        for (int i = 0; i < 8; i++) {
            boardGrid[i][1] = new Pawn (i, 1, Piece.BLACK);
            boardGrid[i][6] = new Pawn (i, 6, Piece.WHITE);
        }

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                boardGrid[j][i] = Piece.EMPTY;
            }
        }

        boardGrid[0][7] = new Rook(0, 7, Piece.WHITE);
        boardGrid[1][7] = new Knight(1, 7, Piece.WHITE);
        boardGrid[2][7] = new Bishop(2, 7, Piece.WHITE);
        boardGrid[3][7] = new Queen(3, 7, Piece.WHITE);
        boardGrid[4][7] = new King(4, 7, Piece.WHITE);
        boardGrid[5][7] = new Bishop(5, 7, Piece.WHITE);
        boardGrid[6][7] = new Knight(6, 7, Piece.WHITE);
        boardGrid[7][7] = new Rook(7, 7, Piece.WHITE);
    }
    
    private Piece[][] boardGrid;
}