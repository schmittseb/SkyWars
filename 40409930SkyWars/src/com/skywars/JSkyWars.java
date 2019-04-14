package com.skywars;

import com.skywars.GUI;
import com.skywars.Move.Move;
import com.skywars.Move.Transition;
import com.skywars.Ships.*;

import java.io.*;
import java.util.*;

public class JSkyWars implements Serializable {

    private Board board;
    private boolean isGameStarted;
    private boolean isGameOver;
    private boolean isMasterMove;
    private boolean isEnemyMove;
    private boolean isMasterShipModeDefensive;
    private int roundCount;
    private Log log;


    public static void main(String[] args) throws InterruptedException {
        GUI gui = new GUI();
    }

    public JSkyWars() {
        this.isMasterMove = false;
        this.isGameStarted = false;
        this.isGameOver = false;
        this.isEnemyMove = false;
        this.isMasterShipModeDefensive = true;
        this.board = Board.createEmptyBoard();
        this.roundCount = 0;
        this.log = new Log();
    }

    final static int[] POSSIBLE_MOVES = {-5, -4, -3, -1, 1, 3, 4, 5};

    public static int getRandomMoveOffset() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(POSSIBLE_MOVES.length - 1);
        return POSSIBLE_MOVES[randomNumber];
    }

    public void startNewGame(){
        this.setBoard(Board.createStartGameBoard());
        this.roundCount = 0;
        this.isGameOver = false;
        this.log.clear();
    }

    public void moveShip(final Ship ship) {
        int current = ship.getCoordinate();
        int destination = current + getRandomMoveOffset();
        final Move move = Move.createMove(this.getBoard(), current, destination);
        final Transition transition = this.getBoard().makeMove(move);
        if (transition.getMoveStatus().isDone()) {
            this.setBoard(transition.getTransitionBoard());
        } else {
            moveShip(ship);
        }
    }

    public void moveMasterShip(){
        moveShip(this.getBoard().getMasterShip());
        spawnEnemyShip();
        this.setMasterMove(false);
        this.setEnemyMove(true);
    }

    public void moveEnemyShips(){
        ArrayList<EnemyShip> enemyShips = this.getBoard().getEnemyShips();
        for (EnemyShip enemyShip: enemyShips){
            moveShip(enemyShip);
        }
        this.setMasterMove(false);
        this.setEnemyMove(false);
    }

    public void spawnEnemyShip() {
        final Board board = this.getBoard();
        Board.Builder builder = new Board.Builder();
        MasterShip masterShip = board.getMasterShip();
        ArrayList<EnemyShip> enemyShips = board.getEnemyShips();
        builder.setShip(masterShip);
        for (EnemyShip enemyShip : enemyShips) {
            builder.setShip(enemyShip);
        }
        Random random = new Random();
        int spawnCheck = random.nextInt(3);
        if (spawnCheck == 0) {
            int whichShip = random.nextInt(3);
            switch (whichShip) {
                case 0:
                    builder.setShip(new BattleStar(0));
                    break;
                case 1:
                    builder.setShip(new BattleShooter(0));
                    break;
                case 2:
                    builder.setShip(new BattleCruiser(0));
                    break;
            }
        }
        this.setBoard(builder.build());
    }

    public  void conflict() {
        final Board board = this.getBoard();

        final MasterShip masterShip = board.getMasterShip();
        final ArrayList<EnemyShip> enemyShips = board.getEnemyShips();

        final int coordinate = masterShip.getCoordinate();
        final Tile tile = this.getBoard().getTile(coordinate);

        Board.Builder builder = new Board.Builder();
        if (tile.getEnemyShips().size() > 0) {
            if (isMasterShipModeDefensive() == true) {
                if (tile.getEnemyShips().size() < 2) {
                    builder.setShip(masterShip);
                    for (EnemyShip enemyShip : enemyShips) {
                        if (enemyShip.getCoordinate() != coordinate) {
                            builder.setShip(enemyShip);
                        } else {
                            log.addShip(enemyShip);
                        }
                    }
                } else {
                    for (EnemyShip enemyShip : enemyShips) {
                        builder.setShip(enemyShip);
                    }
                    isGameOver = true;
                }
            } else {
                if (tile.getEnemyShips().size() < 3) {
                    builder.setShip(masterShip);
                    for (EnemyShip enemyShip : enemyShips) {
                        if (enemyShip.getCoordinate() != coordinate) {
                            builder.setShip(enemyShip);
                        }else {
                            log.addShip(enemyShip);
                        }
                    }
                } else {
                    for (EnemyShip enemyShip : enemyShips) {
                        builder.setShip(enemyShip);
                    }
                    isGameOver = true;
                }
            }
        } else {
            for (EnemyShip enemyShip : enemyShips) {
                builder.setShip(enemyShip);
            }
        }
        builder.setShip(masterShip);
        this.setBoard(builder.build());
    }

    public void newRound() {
        this.conflict();
        this.setMasterMove(true);
        this.setEnemyMove(false);
        this.roundCount++;
    }

    public void saveGame() {
        final JSkyWars jSkyWars = this;
        try {
            FileOutputStream fos = new FileOutputStream("SkyWarsGame.save");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(jSkyWars);
            oos.close();
            fos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        try {
            FileInputStream fis = new FileInputStream("SkyWarsGame.save");
            ObjectInputStream ois = new ObjectInputStream(fis);
            final JSkyWars jSkyWars= (JSkyWars) ois.readObject();
            this.isGameOver = false;
            this.board = jSkyWars.board;
            this.isGameStarted = jSkyWars.isGameStarted;
            this.isMasterMove = jSkyWars.isMasterMove;
            this.isEnemyMove = jSkyWars.isEnemyMove;
            this.isMasterShipModeDefensive = jSkyWars.isMasterShipModeDefensive;
            this.roundCount = jSkyWars.roundCount;
            this.isGameOver = jSkyWars.isGameOver;
            this.log = jSkyWars.log;
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // GETTER and SETTER

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(final Board board) {
        this.board = board;
        this.setGameStarted(true);
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(final boolean isGameStarted) {
        this.isGameStarted = isGameStarted;
        this.setMasterMove(true);
    }

    public boolean isMasterMove() {
        return this.isMasterMove;
    }

    public void setMasterMove(final boolean masterMove) {
        this.isMasterMove = masterMove;
    }

    public boolean isEnemyMove() {
        return this.isEnemyMove;
    }

    public void setEnemyMove(final boolean enemyMove) {
        this.isEnemyMove = enemyMove;
    }

    public void changeMasterShipModeDefensive() {
        this.isMasterShipModeDefensive = !this.isMasterShipModeDefensive;
    }

    public boolean isMasterShipModeDefensive() {
        return this.isMasterShipModeDefensive;
    }

    public boolean isGameOver() {
        return this.isGameOver;
    }

    public int getRoundCount() {
        return this.roundCount;
    }

    public Log getLog() { return log; }

}
