package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GUIInterface implements UserInterface {
    JFrame frame  = new JFrame("Minesweeper");
    JPanel displayPanel = new JPanel();
    JLabel notificationLabel = new JLabel();
    boolean firstDisplay = true;
    JTextField field = new JTextField();
    int input =-1;
    String coord = null;
    Map <Coordinate, CoordButton> buttonMap = new HashMap<>();
    Color buttonColor = null;

    public GUIInterface(){
        frame.add(displayPanel);
        displayPanel.add(notificationLabel);


        frame.setLayout(new GridLayout(3, 1));

        this.field.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        input = Integer.parseInt(field.getText());
                    } catch (NumberFormatException _) {
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        displayPanel.add(this.field);
        this.field.setColumns(5);
        this.frame.setSize(750, 500);

        this.frame.setVisible(true);
    }

    public void listen(){

    }

    @Override
    public int getInt(String prompt) {
        return getInt(prompt, -1);
    }

    @Override
    synchronized public int getInt(String prompt, int max) {
        if (firstDisplay) {
            display(prompt);
        }
        input = -1;
        boolean go = true;
        while (go) {
            while (input == -1) {
                try {
                    wait(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            if (max != -1 && input > max) {
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
        int x;
        int y;
        String[] parts = coord.split(",");
        x = Integer.parseInt(parts[0]);
        y = Integer.parseInt(parts[1]);
        coord = null;
        return new Coordinate(x,y);
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
                    button.setSize(10, 10);
                    button.setText("?");
                    buttonMap.put(new Coordinate(i, j), button);
                    row.add(button);
                    this.buttonColor = button.getBackground();

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

        public CoordButton(int x, int y) {
            this.x = x;
            this.y = y;
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        input = 2;
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        input = 3;
                    }
                    coord = x + "," + y;
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
