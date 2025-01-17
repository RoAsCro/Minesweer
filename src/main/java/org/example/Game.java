package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final float MAX_MINE_PERCENTAGE = 0.25f;

    private final UserInterface userInterface;

    private boolean firstMove = true;
    private int revealedCount = 0;
    private Board board;

    public Game(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public void setUp(){
        this.userInterface.display("Welcome to Minesweeper!");
        int size = this.userInterface.getInt("Please enter the desired length of the board - boards are square.");
        int maxMines = (int) (MAX_MINE_PERCENTAGE * size * size);
        int mines = this.userInterface.getInt("Please enter the number of mines you want (no more than " +
                maxMines +
                " are recommended).", size * size - 9);
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
            try {
                Coordinate coord;
                switch (selection) {
                    case 1:
                        displayBoard();
                        break;
                    case 2:
                        coord = this.userInterface.getCoordinate("Please enter the coordinate " +
                                "of the location you want to select.");
                        if (coord == null) {
                            continue;
                        }
                        if (!makeMove(coord)){
                            go = false;
                        }
                        break;
                    case 3:
                        coord = this.userInterface.getCoordinate("Please enter the coordinate " +
                                "of the location you want to flag or unflag.");
                        if (coord == null) {
                            continue;
                        }
                        flag(coord);
                        break;
                    case 4:
                        go = false;
                        break;
                }
            } catch (BoardLimitException e) {
                this.userInterface.display(e.getMessage());
            }
        }
        this.userInterface.displayBoard(this.board, true);
    }

    public void flag(Coordinate coord) throws BoardLimitException{
        this.board.flagUnflag(coord);
    }

    public boolean makeMove(Coordinate coord) throws BoardLimitException{
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
        reveal(coord, new ArrayList<>());

        int size = this.board.getSize();

        if (revealedCount == size * size - this.board.getMines()) {
            this.userInterface.display("You win!");

            return false;
        }

        return true;
    }
    public void displayBoard(){
        this.userInterface.displayBoard(this.board, false);
    }

    private void reveal(Coordinate coord, List<Coordinate> used) {
        try {
            if (used.contains(coord)) {
                return;
            }
        } catch (BoardLimitException e) {
            return;
        }
        used.add(coord);
        if (this.board.isMined(coord) || this.board.isRevealed(coord)) {
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
