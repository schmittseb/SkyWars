package com.skywars.Move;

import com.skywars.Board;
import com.skywars.Ships.Ship;

public abstract class Move {

    private final Board board;
    private final Ship movedShip;
    private final int destination;

    public static final Move NULL_MOVE = new NullMove();

    private Move(final Board board, final Ship movedShip, final int destination) {
        this.board = board;
        this.movedShip = movedShip;
        this.destination = destination;
    }

    public int getDestination() {
        return this.destination;
    }

    public int getCurrentCoordinate() {
        return this.getMovedShip().getCoordinate();
    }

    public Ship getMovedShip() {
        return this.movedShip;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Move)) {
            return false;
        }
        final Move otherMove = (Move) other;
        return getDestination() == otherMove.getDestination() && getMovedShip().equals(otherMove.getMovedShip());
    }

    public Board execute() {
        //construct new board
        final Board.Builder builder = new Board.Builder();

        // Get the mastership and if its not the moved ship place it at the same pos
        Ship masterShip = this.board.getMasterShip();

        // Do the same for all enemy ships
        for (final Ship ship : this.board.getEnemyShips()) {
            if (!this.movedShip.equals(ship)) {
                builder.setShip(ship);
            }
        }

        if (!this.movedShip.equals(masterShip)) {
            builder.setShip(masterShip);
        }

        // the ship which is left is being moved and then the new instance is placed by the builder
        builder.setShip(this.movedShip.moveShip(this));
        return builder.build();
    }

    public static final class NormalMove extends Move {

        public NormalMove(final Board board, final Ship movedShip, final int destination) {
            super(board, movedShip, destination);
        }

    }

    public static final class NullMove extends Move {
        public NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("cant do that");
        }
    }


    public static Move createMove(final Board board, final int currentCoordinate, final int destination) {
        for (final Move move : board.getAllLegalMoves()) {
            if (move.getCurrentCoordinate() == currentCoordinate && move.getDestination() == destination) {
                return move;
            }
        }
        return NULL_MOVE;
    }

}
