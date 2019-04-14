package com.skywars;

import com.skywars.Move.Move;
import com.skywars.Move.MoveStatus;
import com.skywars.Move.Transition;
import com.skywars.Ships.*;

import java.io.Serializable;
import java.util.*;

public class Board implements Serializable {

    private final Tile[] gameBoard;
    private final ArrayList<EnemyShip> enemyShips;
    private final MasterShip masterShip;

    public static final int TILES = 16;
    public static final int TILES_ROW = 4;


    public Board(final Builder builder) {
        this.gameBoard = createBoard(builder);
        this.enemyShips = calculateEnemyShips(this.gameBoard);
        this.masterShip = calculateMasterShip(this.gameBoard);
    }

    public static Tile[] createBoard(final Builder builder) {
        final Tile[] tiles = new Tile[TILES];
        for (int i = 0; i < TILES; i++) {
            ArrayList<EnemyShip> enemyShips = new ArrayList<>();
            MasterShip masterShip = null;
            for (Ship ship : builder.boardConfiguration) {
                if (ship.getCoordinate() == i) {
                    if (ship.getShipType() == Ship.ShipType.MASTERSHIP) {
                        masterShip = (MasterShip) ship;
                    } else {
                        Ship.ShipType shipType = ship.getShipType();
                        switch (shipType) {
                            case BATTLECRUISER:
                                enemyShips.add((BattleCruiser) ship);
                                break;
                            case BATTLESTAR:
                                enemyShips.add((BattleStar) ship);
                                break;
                            case BATTLESHOOTER:
                                enemyShips.add((BattleShooter) ship);
                                break;
                        }
                    }
                }
            }
            tiles[i] = Tile.createTile(i, masterShip, enemyShips);
        }
        return tiles;
    }

    // creates a board only without any ship on it
    public static Board createEmptyBoard() {
        final Builder builder = new Builder();
        return builder.build();
    }

    // creates a standard starting board with the mastership at a random position
    public static Board createStartGameBoard() {
        final Builder builder = new Builder();
        Random rand = new Random();
        int randomNumber = rand.nextInt(TILES);
        builder.setShip(new MasterShip(randomNumber));
        //builder.setShip(new MasterShip(0));
        //builder.setShip(new BattleCruiser(0));
        //builder.setShip(new BattleCruiser(0));
        //builder.setShip(new BattleCruiser(0));
        return builder.build();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < TILES; i++) {
            final String tileText = this.gameBoard[i].toString();
            builder.append(String.format("%3s", tileText));
            if ((i + 1) % TILES_ROW == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Transition makeMove(final Move move) {
        if (!isMoveLegal(move)) {
            return new Transition(this, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionBoard = move.execute();
        // new board we transition to
        return new Transition(transitionBoard, move, MoveStatus.DONE);
    }

    public boolean isMoveLegal(final Move move) {
        Collection<Move> legalMoves = getAllLegalMoves();
        if (legalMoves.contains(move)) {
            return true;
        } else {
            return false;
        }
    }

    public Collection<Move> getAllLegalMoves() {
        Collection<Move> enemyStandardLegalMoves = calculateLegalEnemyMoves(this.enemyShips);
        Collection<Move> masterStandardLegalMoves = calculateLegalMoves();
        Collection<Move> finalList = new ArrayList<>();
        finalList.addAll(enemyStandardLegalMoves);
        finalList.addAll(masterStandardLegalMoves);
        return finalList;
    }

    private Collection<Move> calculateLegalEnemyMoves(Collection<EnemyShip> enemyShips) {
        final ArrayList<Move> legalMoves = new ArrayList<>();
        for (final Ship ship : enemyShips) {
            legalMoves.addAll(ship.calculateLegalMoves(this));
        }
        return legalMoves;
    }

    private Collection<Move> calculateLegalMoves() {
        final ArrayList<Move> legalMoves = new ArrayList<>();
        final MasterShip masterShip = this.getMasterShip();
        if (masterShip != null) {
            legalMoves.addAll(masterShip.calculateLegalMoves(this));
        }
        return legalMoves;
    }


    private ArrayList<EnemyShip> calculateEnemyShips(final Tile[] gameBoard) {
        final ArrayList<EnemyShip> enemyShips = new ArrayList<>();
        for (final Tile tile : gameBoard) {
            if (tile.isTileOccupied()) {
                for (EnemyShip ship : tile.getEnemyShips()) {
                    enemyShips.add(ship);
                }
            }
        }
        return enemyShips;
    }

    private MasterShip calculateMasterShip(final Tile[] gameBoard) {
        MasterShip masterShip;
        for (final Tile tile : gameBoard) {
            if (tile.isTileOccupied() && tile.getMasterShip() != null) {
                masterShip = tile.getMasterShip();
                return masterShip;
            }
        }
        return null;
    }

    public static boolean isValidTileCoordinate(int coordinate) {
        if (coordinate >= 0 && coordinate < TILES) {
            return true;
        } else {
            return false;
        }
    }

    public static class Builder {
        ArrayList<Ship> boardConfiguration;

        public Builder() {
            this.boardConfiguration = new ArrayList<>();
        }

        public Builder setShip(final Ship ship) {
            this.boardConfiguration.add(ship);
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }

    // Getter and setter
    public Tile getTile(final int coordinate) {
        return gameBoard[coordinate];
    }

    public ArrayList<EnemyShip> getEnemyShips() {
        return enemyShips;
    }

    public MasterShip getMasterShip() {
        return this.masterShip;
    }

}
