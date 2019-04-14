package com.skywars.Test;

import com.skywars.Board;
import com.skywars.Board.Builder;
import com.skywars.Move.Move.NormalMove;
import com.skywars.Ships.BattleShooter;
import junit.framework.TestCase;

public class BattleShooterTest extends TestCase {
    public BattleShooterTest() {
    }

    public void testToString() {
        BattleShooter battleCruiser = new BattleShooter(3);
        String expected = "SH";
        String actual = battleCruiser.toString();
        assertEquals(actual, expected);
    }

    public void testMoveShip() {
        BattleShooter ship1 = new BattleShooter(7);
        BattleShooter ship2 = new BattleShooter(13);
        Builder builder = new Builder();
        builder.setShip(ship1);
        builder.setShip(ship2);
        Board board = new Board(builder);
        NormalMove move1 = new NormalMove(board, ship1, 4);
        NormalMove move2 = new NormalMove(board, ship2, 14);
        ship1 = (BattleShooter)ship1.moveShip(move1);
        ship2 = (BattleShooter)ship2.moveShip(move2);
        int actual1 = ship1.getCoordinate();
        int actual2 = ship2.getCoordinate();
        int expected1 = 4;
        int expected2 = 14;
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
    }
}
