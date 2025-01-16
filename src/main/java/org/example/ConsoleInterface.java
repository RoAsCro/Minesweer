package org.example;

public class ConsoleInterface implements UserInterface {
    @Override
    public int getInt(String prompt) {
        return 0;
    }

    @Override
    public int getInt(String prompt, int max) {
        return 0;
    }

    @Override
    public Coordinate getCoordinate(String prompt) {
        return null;
    }

    @Override
    public void display(String prompt) {

    }

    @Override
    public void displayBoard(Board board) {

    }
}
