package com.skywars.Test;

import com.skywars.Board;
import com.skywars.Board.Builder;
import com.skywars.Move.Move.NormalMove;
import com.skywars.Ships.MasterShip;
import junit.framework.TestCase;

public class MasterShipTest extends TestCase {
    public MasterShipTest() {
    }

    public void testToString() {
        MasterShip ship = new MasterShip(3);
        String expected = "MS";
        String actual = ship.toString();
        assertEquals(actual, expected);
    }

    public void testMoveShip() {
        MasterShip ship1 = new MasterShip(0);
        MasterShip ship2 = new MasterShip(14);
        Builder builder = new Builder();
        builder.setShip(ship1);
        builder.setShip(ship2);
        Board board = new Board(builder);
        NormalMove move1 = new NormalMove(board, ship1, 4);
        NormalMove move2 = new NormalMove(board, ship2, 10);
        ship1 = (MasterShip)ship1.moveShip(move1);
        ship2 = (MasterShip)ship2.moveShip(move2);
        int actual1 = ship1.getCoordinate();
        int actual2 = ship2.getCoordinate();
        int expected1 = 4;
        int expected2 = 10;
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
    }
}
