package org.example;

public interface UserInterface {
    int getInt(String prompt);
    int getInt(String prompt, int max);
    Coordinate getCoordinate(String prompt);
    void display(String prompt);
    void displayBoard(Board board);
}
