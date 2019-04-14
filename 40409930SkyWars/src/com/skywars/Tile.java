package com.skywars;

import com.skywars.Ships.EnemyShip;
import com.skywars.Ships.MasterShip;

import java.io.Serializable;
import java.util.ArrayList;

public class Tile implements Serializable {

    private final int coordinate;
    private final MasterShip masterShip;
    private ArrayList<EnemyShip> enemyShips = new ArrayList<EnemyShip>();

    private Tile(final int coordinate, final MasterShip masterShip, final ArrayList<EnemyShip> enemyShips) {
        this.coordinate = coordinate;
        this.enemyShips = enemyShips;
        this.masterShip = masterShip;
    }


    public String toString() {
        if (this.masterShip == null && this.enemyShips.size() == 0){
            return "-";
        }
        String string = "";
        if (this.masterShip == null && this.enemyShips != null) {
            for (EnemyShip enemyShip : this.enemyShips) {
                string = string.concat(enemyShip.toString());
            }
        }
        if (this.masterShip != null && this.enemyShips == null) {
            string = this.masterShip.toString();
        }
        if (this.masterShip != null && this.enemyShips != null) {
            string = string.concat(this.masterShip.toString());
            for (EnemyShip enemyShip : this.enemyShips) {
                string = string.concat(enemyShip.toString());
            }
        }

        return string;
    }

    public boolean isTileOccupied(){
        return !(this.masterShip == null && this.enemyShips.size() == 0);
    }

    public int getCoordinate(){
        return this.coordinate;
    }

    public  MasterShip getMasterShip(){
        return this.masterShip;
    }

    public  ArrayList<EnemyShip> getEnemyShips(){
        return this.enemyShips;
    }

    public static Tile createTile(final int coordinate, final MasterShip ship, ArrayList<EnemyShip> enemyShips){
        return new Tile(coordinate, ship, enemyShips);
    }

}
