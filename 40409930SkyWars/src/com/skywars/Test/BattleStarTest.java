package com.skywars.Test;

import com.skywars.Board;
import com.skywars.Board.Builder;
import com.skywars.Move.Move.NormalMove;
import com.skywars.Ships.BattleStar;
import junit.framework.TestCase;

public class BattleStarTest extends TestCase {
    public BattleStarTest() {
    }

    public void testToString() {
        BattleStar ship = new BattleStar(3);
        String expected = "ST";
        String actual = ship.toString();
        assertEquals(actual, expected);
    }

    public void testMoveShip() {
        BattleStar ship1 = new BattleStar(8);
        BattleStar ship2 = new BattleStar(14);
        Builder builder = new Builder();
        builder.setShip(ship1);
        builder.setShip(ship2);
        Board board = new Board(builder);
        NormalMove move1 = new NormalMove(board, ship1, 9);
        NormalMove move2 = new NormalMove(board, ship2, 13);
        ship1 = (BattleStar)ship1.moveShip(move1);
        ship2 = (BattleStar)ship2.moveShip(move2);
        int actual1 = ship1.getCoordinate();
        int actual2 = ship2.getCoordinate();
        int expected1 = 9;
        int expected2 = 13;
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
    }
}
