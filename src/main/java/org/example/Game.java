package org.example;

import java.util.LinkedList;
import java.util.List;

public class Game {
    private static final float MAX_MINE_PERCENTAGE = 0.25f;
    private static final int BOARD_MIN = 5;
    private static final int BOARD_MAX = 25;
    private static final int MINE_DEDUCTION = 9;

    private final UserInterface userInterface;

    private boolean firstMove = true;
    private int revealedCount = 0;
    private Board board;

    public Game(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public void setUp(){
        this.userInterface.display("Welcome to Minesweeper!");
        int size = 0;
        String prompt = "Please enter the desired length of the board - boards are square and can be at most " +
                BOARD_MAX +
                " squares long.";
        String currentPrompt = prompt;
        while (size < BOARD_MIN) {
            size = this.userInterface
                    .getInt(currentPrompt, BOARD_MAX);
            if (size < BOARD_MIN) {
                currentPrompt = prompt + " - The board must be at least length " + BOARD_MIN + ".";
            }
        }

        int maxMines = (int) (MAX_MINE_PERCENTAGE * size * size);
        int mines = this.userInterface.getInt("Please enter the number of mines you want (no more than " +
                maxMines +
                " are recommended).", size * size - MINE_DEDUCTION);
        this.board = new Board(size, mines);
        menu();
    }

    public void menu() {
        boolean go = true;
        while (go) {
            displayBoard();
            int selection = this.userInterface.getInt(
                    """
                            Please choose one:
                            1. Display the board
                            2. Make a move
                            3. Flag or unflag a location
                            4. Exit
                            """,
                    4
            );
            Coordinate coord;
            switch (selection) {
                case 1:
                    displayBoard();
                    break;
                case 2:
                    coord = getAndValidateCoordinate("Please enter the coordinate " +
                            "of the location you want to select.\n" +
                            "(Coordinates should be x,y - enter the column first, then the row)");
                    if (coord == null) {
                        continue;
                    }
                    if (!makeMove(coord)){
                        go = false;
                    }
                    break;
                case 3:
                    coord = getAndValidateCoordinate("Please enter the coordinate " +
                            "of the location you want to flag or unflag.\n" +
                            "Coordinates should be x,y - enter the column first, then the row)");
                    if (coord == null) {
                        continue;
                    }
                    flag(coord);
                    break;
                case 4:
                    go = false;
                    break;
            }
        }
        this.userInterface.displayBoard(this.board, true);
    }

    public void flag(Coordinate coord) {
        this.board.flagUnflag(coord);
    }

    public boolean makeMove(Coordinate coord) {
        if (this.board.isFlagged(coord)) {
            this.userInterface.display("You can't reveal a flagged square!");
            return true;
        }
        if (this.firstMove) {
            this.board.generate(coord);
            this.firstMove = false;
        }
        if (this.board.isRevealed(coord)){
            this.userInterface.display("That location is already revealed!");
            return true;
        }
        if (this.board.isMined(coord)) {
            this.userInterface.display("You hit a bomb!");
            return false;
        }
        revealLoop(coord);
//        reveal(coord, new ArrayList<>());
        int size = this.board.getSize();

        if (this.revealedCount == size * size - this.board.getMines()) {
            this.userInterface.display("You win!");

            return false;
        }

        return true;
    }
    public void displayBoard(){
        int size = this.board.getSize();
        this.userInterface.display("There are currently " +
                (size * size - this.revealedCount - this.board.getMines()) +
                " safe squares left to uncover.");
        this.userInterface.displayBoard(this.board, false);
    }

    private Coordinate getAndValidateCoordinate(String prompt) {
        Coordinate coord = this.userInterface.getCoordinate(prompt);
        if (coord == null) {
            return null;
        }
        if (coord.getX() < 0 || coord.getY() < 0 ||
                coord.getY() >= this.board.getSize() || coord.getX() >= this.board.getSize()) {
            this.userInterface.display("That location is not on the board!");
            return null;
        }
        return coord;
    }

    private void revealLoop(Coordinate coord) {
        LinkedList<Coordinate> used = new LinkedList<>();
        LinkedList<Coordinate> stack = new LinkedList<>();
        stack.add(coord);
        used.add(coord);
        while (!stack.isEmpty()) {
            Coordinate current = stack.pop();
            this.board.reveal(current);
            this.revealedCount++;
            if (this.board.getAdjacency(current) > 0) {
                continue;
            }
            this.board.getIterator().iterateAdjacent(c ->
                        revealAdd(c, used, stack),
                    current);

        }

    }

    private void revealAdd(Coordinate coord, List<Coordinate> used, List<Coordinate> stack) {
        if (used.contains(coord)) {
            return;
        }
        used.add(coord);
        if (this.board.isMined(coord) || this.board.isRevealed(coord) || this.board.isFlagged(coord)) {
            return;
        }
        stack.add(coord);
    }

    @Deprecated
    private void reveal(Coordinate coord, List<Coordinate> used) {
        if (used.contains(coord)) {
            return;
        }
        used.add(coord);
        if (this.board.isMined(coord) || this.board.isRevealed(coord)  || this.board.isFlagged(coord)) {
            return;
        }
        this.board.reveal(coord);
        this.revealedCount++;
        if (this.board.getAdjacency(coord) > 0) {
            return;
        }
        this.board.getIterator().iterateAdjacent(c -> reveal(c, used), coord);
    }
}
