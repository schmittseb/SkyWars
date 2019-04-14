package com.skywars.Test;

import com.skywars.Board;
import com.skywars.Board.Builder;
import com.skywars.Move.MoveStatus;
import com.skywars.Move.Transition;
import com.skywars.Move.Move.NormalMove;
import com.skywars.Ships.BattleCruiser;
import junit.framework.TestCase;

public class TransitionTest extends TestCase {
    public TransitionTest() {
    }

    public void testGetMoveStatus() {
        BattleCruiser ship = new BattleCruiser(5);
        Builder builder = new Builder();
        builder.setShip(ship);
        Board board = new Board(builder);
        NormalMove move = new NormalMove(board, ship, 10);
        Transition transition = new Transition(board, move, MoveStatus.DONE);
        MoveStatus expected = MoveStatus.DONE;
        MoveStatus actual = transition.getMoveStatus();
        assertEquals(actual, expected);
    }

    public void testGetTransitionBoard() {
        BattleCruiser ship = new BattleCruiser(5);
        Builder builder = new Builder();
        builder.setShip(ship);
        Board board = new Board(builder);
        NormalMove move = new NormalMove(board, ship, 10);
        Transition transition = new Transition(board, move, MoveStatus.DONE);
        Board actual = transition.getTransitionBoard();
        assertEquals(actual, board);
    }
}
