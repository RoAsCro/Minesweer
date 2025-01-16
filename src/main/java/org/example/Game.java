package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final float MAX_MINE_PERCENTAGE = 0.5f;

    private boolean firstMove = true;
    private Board board;
    private UserInterface userInterface;

    public void setUp(){
        //Display welcome message
        // Prompt user for size
        //Prompt user for mines/diffculty
        // Go to play menu
        this.userInterface.display("Welcome to Minesweeper!");
        int size = this.userInterface.getInt("Please enter the size of the board.");
        int maxMines = (int) (MAX_MINE_PERCENTAGE * size);
        int mines = this.userInterface.getInt("Please enter the number of mines you want (Max number - " +
                maxMines +
                ").", maxMines);
        this.board = new Board(size, mines);
        menu();
    }

    public void menu() {
        // Display the board
        //1 Display board
        //2 Make a move
        //3 flag a location
        boolean go = true;
        while (go) {
            displayBoard();
            int selection = this.userInterface.getInt(
                    """
                            Please choose one:
                            1. Display the board
                            2. Make a move
                            3. Flag a location
                            4. Exit
                            """,
                    4 //TODO NO MINIMUM
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
                        if (!makeMove(coord)){
                            go = false;
                        }
                        break;
                    case 3:
                        coord = this.userInterface.getCoordinate("Please enter the coordinate " +
                                "of the location you want to select.");
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
    }

    public void flag(Coordinate coord) throws BoardLimitException{
        this.board.flag(coord);
    }

    public boolean makeMove(Coordinate coord) throws BoardLimitException{
        // First move? Generate.
        // Exception? go back
        // is revealed? go back
        // [Is flagged? Cofnirm]
        // Is mined? Game over
        // Cascade reveals
        // Check for game over
        if (this.firstMove) {
            this.board.generate(coord);
            this.firstMove = false;
        }
        if (this.board.isRevealed(coord)){
            this.userInterface.display("That location is already revealed!");
            return true;
        }
        if (this.board.isMined(coord)) {
            return false;
        }
        reveal(coord, new ArrayList<>());
        return true;
    }
    public void displayBoard(){
        // 1. Revealed?
        //  a. No - Flagged?
        //  b. yes - adjacancy
        //
        // Hand representation of the board to interface
    }

    private void reveal(Coordinate coord, List<Coordinate> used) {
        try {
            if (used.contains(coord) || this.board.isRevealed(coord)) {
                return;
            }
        } catch (BoardLimitException e) {
            return;
        }
        used.add(coord);
        this.board.reveal(coord);
        this.board.getIterator().iterateAdjacent(c -> reveal(c, used), coord);
    }
}
