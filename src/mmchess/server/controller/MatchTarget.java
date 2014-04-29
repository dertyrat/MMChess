package mmchess.server.controller;


import mmchess.client.model.Board;
import mmchess.client.model.Move;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Travis Meares
 */
public class MatchTarget implements Runnable{
    private ObjectInputStream p1In, p2In;
    private ObjectOutputStream p1Out, p2Out;
    private Board board;
    private int turn;

    /**
     * Target for a match thread, can be placed in a thread and ran
     * @param p1In player 1's input stream
     * @param p1Out player 1's output stream
     * @param p2In player 2's input stream
     * @param p2Out player 2's output stream
     */
    public MatchTarget(ObjectInputStream p1In, ObjectOutputStream p1Out, ObjectInputStream p2In, ObjectOutputStream p2Out) {
        // initialize sockets
        this.p1In = p1In;
        this.p1Out = p1Out;
        this.p2In = p2In;
        this.p2Out = p2Out;
        // initialize colors
        try {
            p1Out.writeObject("CLR W");
            p2Out.writeObject("CLR B");
        } catch (IOException e) {
            System.out.printf("%s\n", "Failed to initialize players' colors.");
            e.printStackTrace();
            //close();
        }
        // initialize turn counter
//        board = new Board();
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
            while (true) {
                if (turn%2 == 0) {
                    // get p1's move
                    do {
                        System.out.println("Sending TRN key"); 
                        p1Out.writeObject("TRN");
                        System.out.println("Waiting on p1 move");
                        move = (Move) p1In.readObject();
                        System.out.println("p1 move received, sending to both clients");
                    } while (!board.doMove(move));
                    // send p1's move to clients
                    p1Out.writeObject(move);
                    p2Out.writeObject(move);
                    System.out.println("Move sent to both clients");
                    // increment turn counter
                    turn++;
                    System.out.println("turn incremented");
                } else if (turn%2 == 1) {
                    // get p2's move
                    do {
                        System.out.println("Sending TRN key"); 
                        p2Out.writeObject("TRN");
                        System.out.println("Waiting on p2 move");
                        move = (Move) p2In.readObject();
                        System.out.println("p2 move received, sending to both clients");
                    } while (!board.doMove(move));
                    // send p2's move to clients
                    p1Out.writeObject(move);
                    p2Out.writeObject(move);
                    System.out.println("Move sent to both clients");
                    // increment turn counter
                    turn++;
                    System.out.println("turn incremented");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}