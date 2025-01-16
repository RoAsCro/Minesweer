package org.example;

public class Game {
    private Board board;
    private UserInterface userInterface;

    public void setUp(){
        //Display welcome message
        // Prompt user for size
        //Prompt user for mines/diffculty
        // Go to play menu
    }

    public void menu() {
        // Display the board
        //1 Display board
        //2 Make a move
        //3 flag a location
    }

    public void flag(int x, int y) throws BoardLimitException{
        // exception? go back
    }

    public void makeMove(int x, int y) throws BoardLimitException{
        // First move? Generate.
        // Exception? go back
        // Cascade reveals
    }
    public void display(){
        // 1. Revealed?
        //  a. No - Flagged?
        //  b. yes - adjacancy
        //
        // Hand representation of the board to interface
    }
}
