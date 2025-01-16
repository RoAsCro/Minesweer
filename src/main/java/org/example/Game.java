package org.example;

public class Game {
    private static final float MAX_MINE_PERCENTAGE = 0.5f;

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
                    4 //NO MINIMUM
            );
            switch (selection) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
        }
    }

    public void flag(int x, int y) throws BoardLimitException{
        // exception? go back
    }

    public void makeMove(int x, int y) throws BoardLimitException{
        // First move? Generate.
        // Exception? go back
        // Cascade reveals
    }
    public void displayBoard(){
        // 1. Revealed?
        //  a. No - Flagged?
        //  b. yes - adjacancy
        //
        // Hand representation of the board to interface
    }
}
