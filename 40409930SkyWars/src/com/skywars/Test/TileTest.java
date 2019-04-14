package com.skywars.Test;

import com.skywars.Tile;
import com.skywars.Ships.BattleCruiser;
import com.skywars.Ships.BattleStar;
import com.skywars.Ships.EnemyShip;
import com.skywars.Ships.MasterShip;
import java.util.ArrayList;
import junit.framework.TestCase;

public class TileTest extends TestCase {
    public TileTest() {
    }

    public void testToString() {
        ArrayList<EnemyShip> enemyShips1 = new ArrayList();
        ArrayList<EnemyShip> enemyShips2 = new ArrayList();
        BattleCruiser battleCruiser1 = new BattleCruiser(3);
        BattleStar battleStar = new BattleStar(3);
        BattleCruiser battleCruiser2 = new BattleCruiser(15);
        enemyShips1.add(battleCruiser1);
        enemyShips1.add(battleStar);
        enemyShips2.add(battleCruiser2);
        Tile tile1 = Tile.createTile(0, (MasterShip)null, new ArrayList());
        Tile tile2 = Tile.createTile(2, new MasterShip(2), new ArrayList());
        Tile tile3 = Tile.createTile(3, (MasterShip)null, enemyShips1);
        Tile tile4 = Tile.createTile(15, new MasterShip(15), enemyShips2);
        String actual1 = tile1.toString();
        String actual2 = tile2.toString();
        String actual3 = tile3.toString();
        String actual4 = tile4.toString();
        String expected1 = "-";
        String expected2 = "MS";
        String expected3 = "CRST";
        String expected4 = "MSCR";
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
        assertEquals(actual3, expected3);
        assertEquals(actual4, expected4);
    }

    public void testIsTileOccupied() {
        ArrayList<EnemyShip> enemyShips1 = new ArrayList();
        ArrayList<EnemyShip> enemyShips2 = new ArrayList();
        BattleCruiser battleCruiser1 = new BattleCruiser(3);
        BattleStar battleStar = new BattleStar(3);
        BattleCruiser battleCruiser2 = new BattleCruiser(15);
        enemyShips1.add(battleCruiser1);
        enemyShips1.add(battleStar);
        enemyShips2.add(battleCruiser2);
        Tile tile1 = Tile.createTile(0, (MasterShip)null, new ArrayList());
        Tile tile2 = Tile.createTile(2, new MasterShip(2), new ArrayList());
        Tile tile3 = Tile.createTile(3, (MasterShip)null, enemyShips1);
        Tile tile4 = Tile.createTile(15, new MasterShip(15), enemyShips2);
        boolean actual1 = tile1.isTileOccupied();
        boolean actual2 = tile2.isTileOccupied();
        boolean actual3 = tile3.isTileOccupied();
        boolean actual4 = tile4.isTileOccupied();
        boolean expected1 = false;
        boolean expected2 = true;
        boolean expected3 = true;
        boolean expected4 = true;
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
        assertEquals(actual3, expected3);
        assertEquals(actual4, expected4);
    }

    public void testGetCoordinate() {
        ArrayList<EnemyShip> enemyShips1 = new ArrayList();
        ArrayList<EnemyShip> enemyShips2 = new ArrayList();
        BattleCruiser battleCruiser1 = new BattleCruiser(3);
        BattleStar battleStar = new BattleStar(3);
        BattleCruiser battleCruiser2 = new BattleCruiser(15);
        enemyShips1.add(battleCruiser1);
        enemyShips1.add(battleStar);
        enemyShips2.add(battleCruiser2);
        Tile tile1 = Tile.createTile(0, (MasterShip)null, new ArrayList());
        Tile tile2 = Tile.createTile(2, new MasterShip(2), new ArrayList());
        Tile tile3 = Tile.createTile(3, (MasterShip)null, enemyShips1);
        Tile tile4 = Tile.createTile(15, new MasterShip(15), enemyShips2);
        int actual1 = tile1.getCoordinate();
        int actual2 = tile2.getCoordinate();
        int actual3 = tile3.getCoordinate();
        int actual4 = tile4.getCoordinate();
        int expected1 = 0;
        int expected2 = 2;
        int expected3 = 3;
        int expected4 = 15;
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
        assertEquals(actual3, expected3);
        assertEquals(actual4, expected4);
    }

    public void testGetMasterShip() {
        ArrayList<EnemyShip> enemyShips1 = new ArrayList();
        ArrayList<EnemyShip> enemyShips2 = new ArrayList();
        MasterShip masterShip1 = new MasterShip(2);
        MasterShip masterShip2 = new MasterShip(15);
        BattleCruiser battleCruiser1 = new BattleCruiser(3);
        BattleStar battleStar = new BattleStar(3);
        BattleCruiser battleCruiser2 = new BattleCruiser(15);
        enemyShips1.add(battleCruiser1);
        enemyShips1.add(battleStar);
        enemyShips2.add(battleCruiser2);
        Tile tile1 = Tile.createTile(0, (MasterShip)null, new ArrayList());
        Tile tile2 = Tile.createTile(2, masterShip1, new ArrayList());
        Tile tile3 = Tile.createTile(3, (MasterShip)null, enemyShips1);
        Tile tile4 = Tile.createTile(15, masterShip2, enemyShips2);
        MasterShip actual1 = tile1.getMasterShip();
        MasterShip actual2 = tile2.getMasterShip();
        MasterShip actual3 = tile3.getMasterShip();
        MasterShip actual4 = tile4.getMasterShip();
        MasterShip expected1 = null;
        MasterShip expected3 = null;
        assertEquals(actual1, expected1);
        assertEquals(actual2, masterShip1);
        assertEquals(actual3, expected3);
        assertEquals(actual4, masterShip2);
    }

    public void testGetEnemyShips() {
        ArrayList<EnemyShip> enemyShips1 = new ArrayList();
        ArrayList<EnemyShip> enemyShips2 = new ArrayList();
        BattleCruiser battleCruiser1 = new BattleCruiser(3);
        BattleStar battleStar = new BattleStar(3);
        BattleCruiser battleCruiser2 = new BattleCruiser(15);
        enemyShips1.add(battleCruiser1);
        enemyShips1.add(battleStar);
        enemyShips2.add(battleCruiser2);
        Tile tile1 = Tile.createTile(0, (MasterShip)null, new ArrayList());
        Tile tile2 = Tile.createTile(2, new MasterShip(2), new ArrayList());
        Tile tile3 = Tile.createTile(3, (MasterShip)null, enemyShips1);
        Tile tile4 = Tile.createTile(15, new MasterShip(15), enemyShips2);
        ArrayList<EnemyShip> actual1 = tile1.getEnemyShips();
        ArrayList<EnemyShip> actual2 = tile2.getEnemyShips();
        ArrayList<EnemyShip> actual3 = tile3.getEnemyShips();
        ArrayList<EnemyShip> actual4 = tile4.getEnemyShips();
        ArrayList<EnemyShip> expected1 = new ArrayList();
        ArrayList<EnemyShip> expected2 = new ArrayList();
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
        assertEquals(actual3, enemyShips1);
        assertEquals(actual4, enemyShips2);
    }

    public void testCreateTile() {
        ArrayList<EnemyShip> enemyShips1 = new ArrayList();
        ArrayList<EnemyShip> enemyShips2 = new ArrayList();
        MasterShip masterShip1 = new MasterShip(2);
        MasterShip masterShip2 = new MasterShip(15);
        BattleCruiser battleCruiser1 = new BattleCruiser(3);
        BattleStar battleStar = new BattleStar(3);
        BattleCruiser battleCruiser2 = new BattleCruiser(15);
        enemyShips1.add(battleCruiser1);
        enemyShips1.add(battleStar);
        enemyShips2.add(battleCruiser2);
        Tile actual1 = Tile.createTile(0, (MasterShip)null, new ArrayList());
        Tile actual2 = Tile.createTile(2, masterShip1, new ArrayList());
        Tile actual3 = Tile.createTile(3, (MasterShip)null, enemyShips1);
        Tile actual4 = Tile.createTile(15, masterShip2, enemyShips2);
        Tile expected1 = Tile.createTile(0, (MasterShip)null, new ArrayList());
        Tile expected2 = Tile.createTile(2, masterShip1, new ArrayList());
        Tile expected3 = Tile.createTile(3, (MasterShip)null, enemyShips1);
        Tile expected4 = Tile.createTile(15, masterShip2, enemyShips2);
        assertEquals(actual1, expected1);
        assertEquals(actual2, expected2);
        assertEquals(actual3, expected3);
        assertEquals(actual4, expected4);
    }
}
