package com.skywars.Test;

import com.skywars.Board;
import com.skywars.Tile;
import com.skywars.Board.Builder;
import com.skywars.Move.Move;
import com.skywars.Move.MoveStatus;
import com.skywars.Move.Transition;
import com.skywars.Move.Move.NormalMove;
import com.skywars.Ships.BattleCruiser;
import com.skywars.Ships.BattleShooter;
import com.skywars.Ships.BattleStar;
import com.skywars.Ships.EnemyShip;
import com.skywars.Ships.MasterShip;
import com.skywars.Ships.Ship;
import com.skywars.Ships.Ship.ShipType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import junit.framework.TestCase;

public class BoardTest extends TestCase {

    public BoardTest() {
    }

    public void testCreateEmptyBoard() {
        Builder builder = new Builder();
        Board expected = builder.build();
        Board actual = Board.createEmptyBoard();
        assertEquals(expected, actual);
    }

    public void testStartGame() {
        Builder builder = new Builder();
        Random rand = new Random();
        int randomNumber = rand.nextInt(16);
        builder.setShip(new MasterShip(randomNumber));
        Board expectedBoard = builder.build();
        Board actualBoard = Board.createStartGameBoard();
        ArrayList<EnemyShip> actualEnemyShips = actualBoard.getEnemyShips();
        ArrayList<EnemyShip> expectedEnemyShips = new ArrayList();
        MasterShip expectedMasterShip = expectedBoard.getMasterShip();
        MasterShip actualMasterShip = actualBoard.getMasterShip();
        int expectedCoordinate = expectedMasterShip.getCoordinate();
        int actualCoordinate = actualMasterShip.getCoordinate();
        assertEquals(actualEnemyShips, expectedEnemyShips);
    }

    public void testToString() {
        Builder builder = new Builder();
        builder.setShip(new MasterShip(1));
        Board board = builder.build();
        String actual = board.toString();
        String expected = "  - MS  -  -\n  -  -  -  -\n  -  -  -  -\n  -  -  -  -\n";
        assertEquals(actual, expected);
    }

    public void testMakeMove() {
        BattleShooter battleShooter = new BattleShooter(5);
        MasterShip masterShip = new MasterShip(15);
        Builder builder = new Builder();
        builder.setShip(battleShooter);
        builder.setShip(masterShip);
        Board board = new Board(builder);
        NormalMove move1 = new NormalMove(board, battleShooter, 10);
        NormalMove move2 = new NormalMove(board, masterShip, 12);
        Transition actual1 = board.makeMove(move1);
        Transition expected1 = new Transition(board, move1, MoveStatus.DONE);
        Transition actual2 = board.makeMove(move2);
        Transition expected2 = new Transition(board, move2, MoveStatus.ILLEGAL_MOVE);
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
    }

    public void testIsMoveLegal() {
        BattleShooter battleShooter = new BattleShooter(5);
        MasterShip masterShip = new MasterShip(15);
        Builder builder = new Builder();
        builder.setShip(battleShooter);
        builder.setShip(masterShip);
        Board board = new Board(builder);
        Move move1 = new NormalMove(board, masterShip, 12);
        Move move2 = new NormalMove(board, battleShooter, 2);
        Move move3 = new NormalMove(board, battleShooter, 11);
        boolean expected1 = false;
        boolean expected2 = true;
        boolean expected3 = false;
        boolean actual1 = board.isMoveLegal(move1);
        boolean actual2 = board.isMoveLegal(move2);
        boolean actual3 = board.isMoveLegal(move3);
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
        assertEquals(actual3, expected3);
    }

    public void testGetAllLegalMoves() {
    }

    public void testIsValidTileCoordinate() {
        boolean expected1 = Board.isValidTileCoordinate(0);
        boolean expected2 = Board.isValidTileCoordinate(-1);
        boolean expected3 = Board.isValidTileCoordinate(16);
        boolean expected4 = Board.isValidTileCoordinate(4);
        boolean expected5 = Board.isValidTileCoordinate(5);
        boolean actual1 = true;
        boolean actual2 = false;
        boolean actual3 = false;
        boolean actual4 = true;
        boolean actual5 = true;
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
        assertEquals(actual3, expected3);
        assertEquals(actual4, expected4);
        assertEquals(actual5, expected5);
    }

    public void testGetTile() {
        Builder builder = new Builder();
        MasterShip masterShip = new MasterShip(1);
        BattleCruiser battleCruiser = new BattleCruiser(10);
        ArrayList<EnemyShip> enemyShips1 = new ArrayList();
        ArrayList<EnemyShip> enemyShips2 = new ArrayList();
        enemyShips2.add(battleCruiser);
        builder.setShip(masterShip);
        Board board = builder.build();
        Tile expected1 = Tile.createTile(0, (MasterShip)null, enemyShips1);
        Tile expected2 = Tile.createTile(1, masterShip, enemyShips1);
        Tile expected3 = Tile.createTile(10, (MasterShip)null, enemyShips2);
        Tile actual1 = board.getTile(0);
        Tile actual2 = board.getTile(1);
        Tile actual3 = board.getTile(8);
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
        assertEquals(actual3, expected3);
    }

    public void testGetEnemyShips() {
        Builder builder = new Builder();
        MasterShip masterShip = new MasterShip(1);
        BattleCruiser battleCruiser1 = new BattleCruiser(8);
        BattleStar battleStar1 = new BattleStar(10);
        BattleStar battleStar2 = new BattleStar(10);
        BattleCruiser battleCruiser2 = new BattleCruiser(5);
        BattleShooter battleShooter1 = new BattleShooter(13);
        BattleShooter battleShooter2 = new BattleShooter(10);
        builder.setShip(masterShip);
        builder.setShip(battleCruiser1);
        builder.setShip(battleStar1);
        builder.setShip(battleStar2);
        builder.setShip(battleCruiser2);
        builder.setShip(battleShooter1);
        builder.setShip(battleShooter2);
        Board board = builder.build();
        ArrayList<EnemyShip> actual = board.getEnemyShips();
        ArrayList<EnemyShip> expected = new ArrayList();
        expected.add(battleCruiser1);
        expected.add(battleStar1);
        expected.add(battleStar2);
        expected.add(battleCruiser2);
        expected.add(battleShooter1);
        expected.add(battleShooter2);
        assertEquals(actual, expected);
    }

    public void testGetMasterShip() {
        Builder builder = new Builder();
        MasterShip masterShip = new MasterShip(1);
        BattleCruiser battleCruiser1 = new BattleCruiser(8);
        BattleStar battleStar1 = new BattleStar(10);
        BattleStar battleStar2 = new BattleStar(10);
        BattleCruiser battleCruiser2 = new BattleCruiser(5);
        BattleShooter battleShooter1 = new BattleShooter(13);
        BattleShooter battleShooter2 = new BattleShooter(10);
        builder.setShip(masterShip);
        builder.setShip(battleCruiser1);
        builder.setShip(battleStar1);
        builder.setShip(battleStar2);
        builder.setShip(battleCruiser2);
        builder.setShip(battleShooter1);
        builder.setShip(battleShooter2);
        Board board = builder.build();
        MasterShip actual = board.getMasterShip();
        assertEquals(actual, masterShip);
    }
}
