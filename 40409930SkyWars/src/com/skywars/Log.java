package com.skywars;

import com.skywars.Ships.EnemyShip;

import java.io.Serializable;
import java.util.ArrayList;

public class Log implements Serializable {

    private final ArrayList<EnemyShip> destroyedShips;

    public Log() {
        this.destroyedShips = new ArrayList<>();
    }

    public ArrayList<EnemyShip> getDestroyedShips() {
        return this.destroyedShips;
    }

    public void addShip(final EnemyShip enemyShip) {
        this.destroyedShips.add(enemyShip);
    }

    public void clear() {
        this.destroyedShips.clear();
    }

}