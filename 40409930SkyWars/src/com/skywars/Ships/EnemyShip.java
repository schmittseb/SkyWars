package com.skywars.Ships;

import java.io.Serializable;

public abstract class EnemyShip extends Ship implements Serializable {

    EnemyShip(final ShipType shipType, final int coordinate) {
        super(shipType, coordinate);
    }

}

