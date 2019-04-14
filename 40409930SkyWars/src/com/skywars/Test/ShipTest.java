package com.skywars.Test;


import com.skywars.Board;
import com.skywars.Board.Builder;
import com.skywars.Move.Move;
import com.skywars.Move.Move.NormalMove;
import com.skywars.Ships.BattleCruiser;
import com.skywars.Ships.BattleShooter;
import com.skywars.Ships.BattleStar;
import com.skywars.Ships.MasterShip;
import com.skywars.Ships.Ship;
import com.skywars.Ships.Ship.ShipType;
import java.util.ArrayList;
import java.util.Collection;
import junit.framework.TestCase;

public class ShipTest extends TestCase {
    public ShipTest() {
    }

    public void testCalculateLegalMoves() {
        Builder builder = new Builder();
        MasterShip masterShip = new MasterShip(2);
        BattleStar battleStar = new BattleStar(15);
        builder.setShip(masterShip);
        builder.setShip(battleStar);
        Board board = builder.build();
        Collection<Move> expected1 = new ArrayList();
        Collection<Move> expected2 = new ArrayList();
        Collection<Move> actual1 = masterShip.calculateLegalMoves(board);
        Collection<Move> actual2 = battleStar.calculateLegalMoves(board);
        expected1.add(new NormalMove(board, masterShip, 1));
        expected1.add(new NormalMove(board, masterShip, 3));
        expected1.add(new NormalMove(board, masterShip, 5));
        expected1.add(new NormalMove(board, masterShip, 6));
        expected1.add(new NormalMove(board, masterShip, 7));
        expected2.add(new NormalMove(board, battleStar, 10));
        expected2.add(new NormalMove(board, battleStar, 11));
        expected2.add(new NormalMove(board, battleStar, 14));
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
    }

    public void testIsFourthColumnLegalMove() {
        boolean expected1 = false;
        boolean expected2 = false;
        boolean expected3 = true;
        boolean expected4 = true;
        boolean actual1 = Ship.isFourthColumnLegalMove(3, -3);
        boolean actual2 = Ship.isFourthColumnLegalMove(7, 5);
        boolean actual3 = Ship.isFourthColumnLegalMove(11, 3);
        boolean actual4 = Ship.isFourthColumnLegalMove(15, 3);
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
        assertEquals(actual3, expected3);
        assertEquals(actual4, expected4);
    }

    public void testIsFirstColumnLegalMove() {
        boolean expected1 = true;
        boolean expected2 = true;
        boolean expected3 = false;
        boolean expected4 = true;
        boolean actual1 = Ship.isFirstColumnLegalMove(0, -3);
        boolean actual2 = Ship.isFirstColumnLegalMove(4, 5);
        boolean actual3 = Ship.isFirstColumnLegalMove(8, 3);
        boolean actual4 = Ship.isFirstColumnLegalMove(12, 1);
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
        assertEquals(actual3, expected3);
        assertEquals(actual4, expected4);
    }

    public void testGetShipType() {
        MasterShip masterShip = new MasterShip(2);
        BattleStar battleStar = new BattleStar(2);
        BattleShooter battleShooter = new BattleShooter(2);
        BattleCruiser battleCruiser = new BattleCruiser(2);
        ShipType expected1 = ShipType.MASTERSHIP;
        ShipType expected2 = ShipType.BATTLESTAR;
        ShipType expected3 = ShipType.BATTLESHOOTER;
        ShipType expected4 = ShipType.BATTLECRUISER;
        ShipType actual1 = masterShip.getShipType();
        ShipType actual2 = battleStar.getShipType();
        ShipType actual3 = battleShooter.getShipType();
        ShipType actual4 = battleCruiser.getShipType();
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
        assertEquals(actual3, expected3);
        assertEquals(actual4, expected4);
    }

    public void testGetCoordinate() {
        MasterShip masterShip = new MasterShip(2);
        BattleStar battleStar = new BattleStar(7);
        BattleShooter battleShooter = new BattleShooter(10);
        BattleCruiser battleCruiser = new BattleCruiser(15);
        int expected1 = 2;
        int expected2 = 7;
        int expected3 = 10;
        int expected4 = 15;
        int actual1 = masterShip.getCoordinate();
        int actual2 = battleStar.getCoordinate();
        int actual3 = battleShooter.getCoordinate();
        int actual4 = battleCruiser.getCoordinate();
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
        assertEquals(actual3, expected3);
        assertEquals(actual4, expected4);
    }
}
