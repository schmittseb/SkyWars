package com.skywars.Ships;

import com.skywars.Move.Move;
import java.io.Serializable;

public class BattleCruiser extends EnemyShip implements Serializable {

    public BattleCruiser(final int coordinate) {
        super(ShipType.BATTLECRUISER, coordinate);
    }

    @Override
    public String toString(){
        return Ship.ShipType.BATTLECRUISER.toString();
    }

    @Override
    public Ship moveShip(final Move move) {
        return new BattleCruiser(move.getDestination());
    }
}
