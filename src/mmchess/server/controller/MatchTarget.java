package mmchess.server.controller;


import mmchess.server.model.Board;
import mmchess.server.model.Move;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Travis Meares
 */
public class MatchTarget implements Runnable{
    private Socket p1, p2;
    private ObjectInputStream p1In, p2In;
    private ObjectOutputStream p1Out, p2Out;
    private Board board;
    private int turn;

    /**
     * Target for the match thread
     * @param p1 player 1's socket
     * @param p2 player 2's socket
     */
    public MatchTarget(Socket p1, Socket p2) {
        // initialize sockets
        this.p1 = p1;
        this.p2 = p2;
        // initialize p1's streams
        try {
            p1In = new ObjectInputStream(p1.getInputStream());
            p1Out = new ObjectOutputStream(p1.getOutputStream());
        } catch (IOException e) {
            System.out.printf("%s\n", "Failed to initialize p1's streams.");
            e.printStackTrace();
            close();
        }
        // initialize p2's streams
        try {
            p2In = new ObjectInputStream(p2.getInputStream());
            p2Out = new ObjectOutputStream(p2.getOutputStream());
        } catch (IOException e) {
            System.out.printf("%s\n", "Failed to initialize p2's streams.");
            e.printStackTrace();
            close();
        }
        // initialize colors
        try {
            p1Out.writeObject("CLR W");
            p2Out.writeObject("CLR B");
        } catch (IOException e) {
            System.out.printf("%s\n", "Failed to initialize players' colors.");
            e.printStackTrace();
            close();
        }
        // initialize turn counter
        board = new Board();
        turn = 0;
    }

    /**
     * Alternates between players, sending and receiving turns. Executed on
     * thread start.
     */
    @Override
    public void run() {
        Move move;
        try {
            while (!board.isGameOver) {
                if (turn%2 == 0) {
                    // get p1's move
                    do {
                        p1Out.writeObject("TRN");
                        move = (Move) p1In.readObject();
                    } while (!board.doMove(move));
                    // send p1's move to clients
                    p1Out.writeObject(move);
                    p2Out.writeObject(move);
                    // increment turn counter
                    turn++;
                } else if (turn%2 == 1) {
                    // get p2's move
                    do {
                        p2Out.writeObject("TRN");
                        move = (Move) p2In.readObject();
                    } while (!board.doMove(move));
                    // send p2's move to clients
                    p1Out.writeObject(move);
                    p2Out.writeObject(move);
                    // increment turn counter
                    turn++;
                }
            }
            // handle game over
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            p1.close();
            p2.close();
        } catch (IOException e) {
            System.out.printf("%s\n", "Failed to close sockets.");
            e.printStackTrace();
        }
    }
}