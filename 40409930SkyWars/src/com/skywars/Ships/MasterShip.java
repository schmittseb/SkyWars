package com.skywars.Ships;

import com.skywars.Move.Move;

import java.io.Serializable;

public class MasterShip extends Ship implements Serializable {

    public MasterShip(final int coordinate) {
        super(ShipType.MASTERSHIP, coordinate);
    }

    @Override
    public String toString() {
        return ShipType.MASTERSHIP.toString();
    }

    @Override
    public Ship moveShip(final Move move) {
        return new MasterShip(move.getDestination());
    }


}
