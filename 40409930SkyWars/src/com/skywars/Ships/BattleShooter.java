package com.skywars.Ships;

import com.skywars.Move.Move;
import java.io.Serializable;

public class BattleShooter extends EnemyShip implements Serializable {

    public BattleShooter(final int coordinate) {
        super(ShipType.BATTLESHOOTER, coordinate);
    }

    @Override
    public String toString(){
        return Ship.ShipType.BATTLESHOOTER.toString();
    }

    @Override
    public Ship moveShip(final Move move) {
        return new BattleShooter(move.getDestination());
    }
}
