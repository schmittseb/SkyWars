package com.skywars.Ships;

import com.skywars.Move.Move;
import java.io.Serializable;

public class BattleStar extends EnemyShip implements Serializable {

    public BattleStar(final int coordinate) {
        super(ShipType.BATTLESTAR, coordinate);
    }

    @Override
    public String toString(){
        return Ship.ShipType.BATTLESTAR.toString();
    }


    @Override
    public Ship moveShip(final Move move) {
        return new BattleStar(move.getDestination());
    }
}
