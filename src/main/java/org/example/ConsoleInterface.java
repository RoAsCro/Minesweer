package org.example;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class ConsoleInterface implements UserInterface {

    private final static String COORD_REGEX = "^[0-9]+,[0-9]+";

    @Override
    public int getInt(String prompt) {
        return getInt(prompt, -1);
    }

    @Override
    public int getInt(String prompt, int max) {
        while (true) {
            display(prompt);
            Scanner reader = new Scanner(System.in);
            try {
                int input = reader.nextInt();
                if (max != -1 && input > max) {
                    display("Please enter a number less than or equal to " + max + ".");
                    continue;
                }
                if (input < 1) {
                    display("Please enter a number greater than 0.");
                    continue;
                }
                return input;
            } catch (InputMismatchException e) {
                display("Please enter a whole numeral.");
            }
        }
    }

    @Override
    public Coordinate getCoordinate(String prompt) {
        while (true) {
            display(prompt);
            display("Please enter this as two positive numbers separated by a comma, " +
                    "e.g. '2,4'");
            display("Enter '0' to go back.");
            Scanner reader = new Scanner(System.in);
            String input = reader.next();
            if (input.equals("0")) {
                return null;
            }
            if (!input.matches(COORD_REGEX)) {
                display("Not a valid input.");
                continue;
            }
            String[] separated = input.split(",");
            int[] coords = new int[2];
            coords[0] = Integer.parseInt(separated[0]);
            coords[1] = Integer.parseInt(separated[1]);
            return new Coordinate(coords[0], coords[1]);
        }
    }

    @Override
    public void display(String prompt) {
        System.out.println(prompt);
    }

    @Override
    public void displayBoard(Board board, boolean showBombs) {
        StringBuilder builder = new StringBuilder();
        LinkedList<String> rows = new LinkedList<>();

        board.getIterator().iterateAll(c ->
                {
                    String displayString = "";

                    if (!board.isRevealed(c)) {
                        if (showBombs) {
                            if (board.isMined(c)) {
                                displayString += "[B]";
                            } else {
                                displayString += "[X]";
                            }
                        }
                        else if (board.isFlagged(c)) {
                            displayString += "[f]";
                        } else {
                            displayString += "[X]";
                        }
                    } else {
                        int adjacency = board.getAdjacency(c);
                        if (adjacency == 0) {
                            displayString += "[ ]";
                        } else {
                            displayString += "[" + adjacency + "]";
                        }
                    }
                    displayString += "\t";
                    if (c.getX() == board.getSize() - 1) {
                        displayString += "\n";
                        builder.append(displayString);
                        rows.add(builder.toString());
                        builder.setLength(0);
                    } else {
                        builder.append(displayString);
                    }

                }
                );
        StringBuilder displayBuilder = new StringBuilder();
        int currentY = board.getSize() - 1;
        while (!rows.isEmpty()) {
            displayBuilder.append(currentY).append("\t");
            displayBuilder.append(rows.removeLast());
            currentY--;
        }
        displayBuilder.append("\t");
        for (int i = 0; i < board.getSize(); i++) {
            displayBuilder.append(i).append("\t");
        }
        display(displayBuilder.toString());
    }
}
