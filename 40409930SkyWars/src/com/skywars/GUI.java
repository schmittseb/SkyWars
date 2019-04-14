package com.skywars;

import com.skywars.Board;
import com.skywars.JSkyWars;
import com.skywars.Log;
import com.skywars.Ships.EnemyShip;
import com.skywars.Tile;



import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.updateComponentTreeUI;


public class GUI  {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final LogPanel logPanel;
    private final BotPanel botPanel;
    private final TopPanel topPanel;
    private final JMenuBar menuBar;
    private JSkyWars jSkyWars;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(770, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 400);
    private final static Dimension BUTTON_PANEL_DIMENSION = new Dimension(100, 100);
    private final static Dimension BUTTON_SIZE = new Dimension(200, 35);
    private static String shipIconPath = "Ships/";

    // Colour codes
    private final Color firstTileColour = Color.decode("#ffffff");
    private final Color secondTileColour = Color.decode("#ADD8E6");

    public GUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        // create a new game instance
        this.jSkyWars = new JSkyWars();

        // create the GUI window and set the gneral layout
        this.gameFrame = new JFrame("SkyWars");
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);

        // Menu bar
        this.menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        this.gameFrame.setJMenuBar(menuBar);

        // Board panel with the tiles
        this.boardPanel = new BoardPanel(this.jSkyWars);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        // Create the move log
        this.logPanel = new LogPanel();
        this.logPanel.setSize(BUTTON_PANEL_DIMENSION);
        this.gameFrame.add(this.logPanel, BorderLayout.LINE_END);

        // Create the bottom panel
        this.botPanel = new BotPanel();
        this.botPanel.setSize(BUTTON_PANEL_DIMENSION);
        this.gameFrame.add(this.botPanel, BorderLayout.PAGE_END);

        // Create the top panel
        this.topPanel = new TopPanel();
        this.topPanel.setSize(BUTTON_PANEL_DIMENSION);
        this.gameFrame.add(this.topPanel, BorderLayout.PAGE_START);

        this.gameFrame.setVisible(true);

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateComponentTreeUI(gameFrame);
        createEvents();
    }

    private void createEvents() {
        this.topPanel.newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jSkyWars.startNewGame();
                boardPanel.drawBoard(jSkyWars.getBoard());
                logPanel.print(jSkyWars.getLog());
            }
        });

        this.topPanel.changeMasterShipModeButton.addActionListener(e -> {
            jSkyWars.changeMasterShipModeDefensive();
            String string = (jSkyWars.isMasterShipModeDefensive() ? "defensive" : "offensive");
            JOptionPane.showMessageDialog(gameFrame, "The master ship mode was set to " + string + "!");
        });

        this.botPanel.moveMasterShipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jSkyWars.isGameOver() == false) {
                    if (jSkyWars.isGameStarted()) {
                        if (jSkyWars.isMasterMove()) {
                            jSkyWars.moveMasterShip();
                            boardPanel.drawBoard(jSkyWars.getBoard());
                        } else {
                            JOptionPane.showMessageDialog(gameFrame, "Move the enemy ships first and end the round");
                        }
                    } else {
                        JOptionPane.showMessageDialog(gameFrame, "You have to start the game first, before you can move ships.");
                    }
                } else {
                    JOptionPane.showMessageDialog(gameFrame,
                            "You lost the game. You survived " + jSkyWars.getRoundCount() + " rounds. You can start a new game if you want");
                }
            }
        });

        this.botPanel.moveEnemyShipsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jSkyWars.isGameOver() == false) {
                    if (jSkyWars.isGameStarted()) {
                        if (!jSkyWars.isMasterMove() && jSkyWars.isEnemyMove()) {
                            jSkyWars.moveEnemyShips();
                            boardPanel.drawBoard(jSkyWars.getBoard());
                        } else {
                            JOptionPane.showMessageDialog(gameFrame, "Move the mastership first.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(gameFrame, "You have to start the game first, before you can move ships.");
                    }
                } else {
                    JOptionPane.showMessageDialog(gameFrame, "You lost the game. You survived " + jSkyWars.getRoundCount() + " rounds. You can start a new game if you want");
                }
            }
        });

        this.botPanel.newRoundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jSkyWars.isGameOver() == false) {
                    if (jSkyWars.isGameStarted()) {
                        if (jSkyWars.isMasterMove() == false && jSkyWars.isEnemyMove() == false) {
                            jSkyWars.newRound();
                            boardPanel.drawBoard(jSkyWars.getBoard());
                            logPanel.print(jSkyWars.getLog());
                        } else {
                            JOptionPane.showMessageDialog(gameFrame, "You have to move all ships, before you can start a new round.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(gameFrame, "You have to start the game first, before you can move ships.");
                    }
                } else {
                    JOptionPane.showMessageDialog(gameFrame, "You lost the game. You survived " + jSkyWars.getRoundCount() + " rounds. You can start a new game if you want");
                }
            }
        });

        this.botPanel.wholeRoundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jSkyWars.isGameOver() == false) {
                    if (jSkyWars.isGameStarted()) {
                        if (jSkyWars.isMasterMove()) {
                            jSkyWars.moveMasterShip();
                            jSkyWars.moveEnemyShips();
                            jSkyWars.newRound();
                            boardPanel.drawBoard(jSkyWars.getBoard());
                            logPanel.print(jSkyWars.getLog());
                        } else {
                            JOptionPane.showMessageDialog(gameFrame, "It has to be the masterships turn. Please finish the round first.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(gameFrame, "You have to start the game first, before you can move ships.");
                    }
                } else {
                    JOptionPane.showMessageDialog(gameFrame, "You lost the game. You survived " + jSkyWars.getRoundCount() + " rounds. You can start a new game if you want");
                }
            }
        });
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("Menu");
        final JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jSkyWars.saveGame();
            }
        });
        fileMenu.add(saveGame);

        final JMenuItem loadGame = new JMenuItem("Load Game");
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jSkyWars.loadGame();
                boardPanel.drawBoard(jSkyWars.getBoard());
                logPanel.print(jSkyWars.getLog());
            }
        });
        fileMenu.add(loadGame);

        return fileMenu;
    }



    public class TopPanel extends JPanel {
        private final JButton newGameButton;
        private final JButton changeMasterShipModeButton;

        TopPanel() {
            this.newGameButton = new JButton("Start new game", new ImageIcon(""));
            this.add(newGameButton);
            newGameButton.setPreferredSize(BUTTON_SIZE);

            this.changeMasterShipModeButton = new JButton("Change master ship mode", new ImageIcon(""));
            this.add(changeMasterShipModeButton);
            changeMasterShipModeButton.setPreferredSize(BUTTON_SIZE);
        }
    }

    public class BotPanel extends JPanel {
        JButton moveMasterShipButton;
        JButton moveEnemyShipsButton;
        JButton newRoundButton;
        JButton wholeRoundButton;

        BotPanel() {
            super(new GridLayout());

            this.moveMasterShipButton = new JButton("Move master ship", new ImageIcon(""));
            this.add(moveMasterShipButton);
            moveMasterShipButton.setPreferredSize(BUTTON_SIZE);

            this.moveEnemyShipsButton = new JButton("Move enemy ships", new ImageIcon(""));
            this.add(moveEnemyShipsButton);
            moveEnemyShipsButton.setPreferredSize(BUTTON_SIZE);

            this.newRoundButton = new JButton("Start new round", new ImageIcon(""));
            this.add(newRoundButton);
            newRoundButton.setPreferredSize(BUTTON_SIZE);

            this.wholeRoundButton = new JButton("Simulate whole round", new ImageIcon(""));
            this.add(wholeRoundButton);
            wholeRoundButton.setPreferredSize(BUTTON_SIZE);
        }
    }

    public class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel(final JSkyWars jSkyWars) {
            super(new GridLayout(4, 4));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < Board.TILES; i++) {
                final TilePanel tilePanel = new TilePanel(jSkyWars, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }


        public void drawBoard(final Board board) {
            removeAll();
            for (final TilePanel tilePanel : boardTiles) {
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel {
        private final int tileId;


        TilePanel(final JSkyWars jSkyWars, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            assignTileColour();
            assignShipIcon(jSkyWars.getBoard());
            validate();
        }

        public void drawTile(final Board board) {
            assignTileColour();
            assignShipIcon(board);
            validate();
            repaint();
        }


        // TODO add some more icons for 3 upwards
        private void assignShipIcon(final Board board) {
            // create a fresh board first
            this.removeAll();
            Tile tile = board.getTile(this.tileId);
            if (tile.isTileOccupied()) {
                try {
                    BufferedImage image = null;
                    String string = board.getTile(this.tileId).toString();
                    if (string.length() == 2) {
                        image = ImageIO.read(new File(shipIconPath + board.getTile(this.tileId).toString() + ".png"));
                    } else if (string.length() == 4) {
                        if (string.contains("MS")) {
                            image = ImageIO.read(new File(shipIconPath + "MS1.png"));
                        } else {
                            image = ImageIO.read(new File(shipIconPath + "two.png"));
                        }
                    } else if (string.length() == 6) {
                        if (string.contains("MS")) {
                            image = ImageIO.read(new File(shipIconPath + "MS2.png"));
                        } else {
                            image = ImageIO.read(new File(shipIconPath + "three.png"));
                        }
                    } else if (string.length() == 8) {
                        if (string.contains("MS")) {
                            image = ImageIO.read(new File(shipIconPath + "MS3.png"));
                        } else {
                            image = ImageIO.read(new File(shipIconPath + "three+.png"));
                        }
                    }
                    else if (string.length() > 8) {
                        if (string.contains("MS")) {
                            image = ImageIO.read(new File(shipIconPath + "MS3+.png"));
                        } else {
                            image = ImageIO.read(new File(shipIconPath + "three+.png"));
                        }
                    }
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void assignTileColour() {
            if (this.tileId < 4 || (this.tileId > 7 && this.tileId < 12)) {
                setBackground(this.tileId % 2 == 0 ? firstTileColour : secondTileColour);
            } else {
                setBackground(this.tileId % 2 != 0 ? firstTileColour : secondTileColour);
            }
        }

    }

    public class LogPanel extends JPanel {

        final JLabel label;
        private final JScrollPane scrollPane;
        private final Dimension LOG_PANEL_DIMENSION = new Dimension(150, 100);

        LogPanel() {
            this.setLayout(new BorderLayout());
            this.setSize(LOG_PANEL_DIMENSION);
            this.label = new JLabel("<html><body>Destroyed Ships:</body></html>");
            label.setHorizontalAlignment(JLabel.LEFT);
            label.setVerticalAlignment(JLabel.TOP);
            label.setPreferredSize(new Dimension(150, 100));
            this.scrollPane = new JScrollPane(label);
            this.add(scrollPane, BorderLayout.CENTER);
            this.setVisible(true);
            validate();
        }

        public void print(final Log log) {
            String logText = "<html><body>Destroyed Ships:";
            for (EnemyShip enemyShip : log.getDestroyedShips()) {
                final String text = "<br>" + enemyShip.toString() + " at " + enemyShip.getCoordinate();
                logText = logText + text;
            }
            logText.concat("</body></html>");
            this.label.setText(logText);
            validate();
            repaint();
        }
    }
}