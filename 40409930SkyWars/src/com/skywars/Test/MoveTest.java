package com.skywars.Test;

import com.skywars.Board;
import com.skywars.Board.Builder;
import com.skywars.Move.Move.NormalMove;
import com.skywars.Move.Move.NullMove;
import com.skywars.Ships.BattleCruiser;
import com.skywars.Ships.BattleShooter;
import com.skywars.Ships.MasterShip;
import junit.framework.TestCase;

public class MoveTest extends TestCase {
    public MoveTest() {
    }

    public void testEquals() {
        BattleShooter ship = new BattleShooter(5);
        Builder builder = new Builder();
        builder.setShip(ship);
        Board board = new Board(builder);
        NormalMove normalMove1 = new NormalMove(board, ship, 10);
        NormalMove normalMove2 = new NormalMove(board, ship, 2);
        NullMove nullMove = new NullMove();
        boolean check1 = normalMove1.equals(normalMove1);
        boolean expected1 = true;
        boolean check2 = normalMove1.equals(normalMove2);
        boolean expected2 = false;
        boolean check3 = normalMove2.equals(nullMove);
        boolean expected3 = false;
        assertEquals(check1, expected1);
        assertEquals(check2, expected2);
        assertEquals(check3, expected3);
    }

    public void testToString() {
        BattleCruiser ship = new BattleCruiser(5);
        Builder builder = new Builder();
        builder.setShip(ship);
        Board board = new Board(builder);
        NormalMove move = new NormalMove(board, ship, 10);
        String actual = move.toString();
        String expected = "CR moved from 5 to 10.";
        assertEquals(actual, expected);
    }

    public void testExecute() {
        MasterShip ship = new MasterShip(5);
        Builder builder = new Builder();
        builder.setShip(ship);
        Board board = new Board(builder);
        NormalMove move = new NormalMove(board, ship, 10);
        board = move.execute();
        int expected = 10;
        int actual = board.getMasterShip().getCoordinate();
        assertEquals(actual, expected);
    }
}
