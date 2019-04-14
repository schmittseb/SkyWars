package com.skywars.Ships;

import com.skywars.Board;
import com.skywars.Move.Move;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.skywars.Board.isValidTileCoordinate;

public abstract class Ship implements Serializable {

    private final UUID uniqueID;
    private final ShipType shipType;
    private final int coordinate;


    // List with the offsets the ship can move to
    private final static int[] POSSIBLE_MOVES ={-5, -4, -3, -1, 1, 3, 4, 5};

    Ship (final ShipType shipType, final int coordinate){
        this.shipType = shipType;
        this.coordinate = coordinate;
        this.uniqueID = UUID.randomUUID();
    }

    public Collection<Move> calculateLegalMoves(final Board board) {
        int destinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();
        for (final int coordinateOffset: POSSIBLE_MOVES) {
            destinationCoordinate = this.coordinate + coordinateOffset;
            if(isValidTileCoordinate(destinationCoordinate)
                    && (isFirstColumnLegalMove(this.coordinate, coordinateOffset)==true)
                    && (isFourthColumnLegalMove(this.coordinate, coordinateOffset)==true)){
                legalMoves.add(new Move.NormalMove(board, this, destinationCoordinate));
            }
        }
        return legalMoves;
    }

    public abstract Ship moveShip(final Move move);

    // Method to check if ship is in first column and if the move is legal (to prevent move out of board)
    public static boolean isFirstColumnLegalMove(final int currentPosition, final int coordinateOffset) {
        if (currentPosition == 0 || currentPosition == 4 || currentPosition ==8 || currentPosition ==12){
            if ((coordinateOffset == -5) || (coordinateOffset == -1) || (coordinateOffset == 3)){
                return false;
            }
        }
        return true;
    }

    // Method to check if ship is in fourth column and if the move is legal (to prevent move out of board)
    public static boolean isFourthColumnLegalMove(final int currentPosition, final int coordinateOffset) {
        if (currentPosition == 3 || currentPosition == 7 || currentPosition ==11 || currentPosition ==15){
            if((coordinateOffset == -3) || (coordinateOffset == 1) || (coordinateOffset == 5)){
                return false;
            }
        }
        return true;
    }

    // Getters and Setters
    public ShipType getShipType() {return this.shipType;}
    public int getCoordinate() {return this.coordinate;}

    public enum ShipType{

        MASTERSHIP("MS"),
        ENEMYSHIP("EN"),
        BATTLECRUISER("CR"),
        BATTLESHOOTER("SH"),
        BATTLESTAR("ST");
        private String shipName;

        ShipType(final String shipName){
            this.shipName = shipName;
        }

        @Override
        public String toString(){
            return this.shipName;
        }
    }

}
