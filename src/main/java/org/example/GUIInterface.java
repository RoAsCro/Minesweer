package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GUIInterface implements UserInterface {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;
    private static final int GRID_ROWS = 3;
    private static final int GRID_COLS = 3;
    private static final int FIELD_COLS = 5;
    private static final int BUTTON_SIZE = 10;
    private static final int MOVE_CODE = 2;
    private static final int FLAG_CODE = 3;


    private final JFrame frame  = new JFrame("Minesweeper");
    private final JPanel displayPanel = new JPanel();
    private final JLabel notificationLabel = new JLabel();
    private boolean firstDisplay = true;
    private final JTextField field = new JTextField();
    private final Map <Coordinate, CoordButton> buttonMap = new HashMap<>();
    private final Color buttonColor = new JButton().getBackground();
    private int input =-1;
    private Coordinate coord = null;


    public GUIInterface(){
        this.frame.add(displayPanel);
        this.displayPanel.add(notificationLabel);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                    System.exit(0);
            }
        });

        this.frame.setLayout(new GridLayout(GRID_ROWS, GRID_COLS));

        this.field.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        input = Integer.parseInt(field.getText());
                        notifyThreads();
                    } catch (NumberFormatException _) {
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        this.displayPanel.add(this.field);
        this.field.setColumns(FIELD_COLS);
        this.frame.setSize(WIDTH, HEIGHT);

        this.frame.setVisible(true);
    }

    synchronized public void notifyThreads(){
        notify();
    }

    @Override
    public int getInt(String prompt) {
        return getIntHelper(prompt, -1, false);
    }

    @Override
    synchronized public int getInt(String prompt, int max) {
        return getIntHelper(prompt, max, true);
    }

    synchronized private int getIntHelper(String prompt, int max, boolean usingMax) {
        if (this.firstDisplay) { //We only care about the prompt before the board is on screen
            display(prompt);
        }
        this.input = -1;
        boolean go = true;
        while (go) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (usingMax && input > max) {
                input = -1;
                this.notificationLabel.setText("Must be no more than " + max +
                        (firstDisplay ? " - " + prompt : ""));
                continue;
            }
            else if (input <= 0) {
                this.notificationLabel.setText("Must be a positive number" +
                        (firstDisplay ? " - " + prompt : ""));
                input = -1;
                continue;
            }
            go = false;
            this.field.setText("");
        }
        return input;
    }

    @Override
    synchronized public Coordinate getCoordinate(String prompt) {
        Coordinate inUse = this.coord;
        this.coord = null;
        return inUse;
    }

    @Override
    public void display(String prompt) {
        this.notificationLabel.setText(prompt);
    }

    @Override
    public void displayBoard(Board board, boolean showBombs) {
        if (firstDisplay) {
            this.displayPanel.remove(this.field);
            frame.setLayout(new GridLayout(board.getSize() + 1, 1));
            for (int i = 0; i < board.getSize(); i++) {
                JPanel row = new JPanel();
                row.setLayout(new GridLayout(1, board.getSize()));
                for (int j = 0; j < board.getSize(); j++) {

                    CoordButton button = new CoordButton(i, j);
                    button.setSize(BUTTON_SIZE, BUTTON_SIZE);
                    button.setText("?");
                    this.buttonMap.put(new Coordinate(i, j), button);
                    row.add(button);
                }
                this.frame.add(row);
            }
            frame.pack();
            firstDisplay = false;
        } else {
            board.getIterator().iterateAll(c ->
                    {
                        CoordButton button = this.buttonMap.get(c);
                        if (board.isRevealed(c)) {
                            button.setBackground(Color.white);
                            int adj = board.getAdjacency(c);
                            if (adj == 0) {
                                button.setText("");
                            } else {
                                button.setText("" + adj);
                            }
                        } else {
                            if (board.isFlagged(c)) {
                                button.setText("!");
                                button.setBackground(Color.YELLOW);
                            } else {
                                button.setText("?");
                                button.setBackground(this.buttonColor);
                            }
                            if (showBombs) {
                                if (board.isMined(c)) {
                                    button.setText("x");
                                    button.setBackground(Color.RED);
                                }
                            }
                        }
                    }
            );
        }

    }
    private class CoordButton extends JButton {
        int x = 0;
        int y = 0;
        Coordinate buttonCoord;

        public CoordButton(int x, int y) {
            this.x = x;
            this.y = y;
            this.buttonCoord = new Coordinate(x, y);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        input = MOVE_CODE;
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        input = FLAG_CODE;
                    }
                    coord = buttonCoord;
                    notifyThreads();
                }

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            });
        }
    }
}
