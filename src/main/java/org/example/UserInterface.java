package org.example;

public interface UserInterface {
    int getInt(String prompt);

    /**
     * Prompts user for an integer. Will only return positive values equal to or less than the max.
     * @param prompt an additional prompt for the user
     * @param max the maximum value, inclusive, of the integer to be returned
     * @return an integer from the user
     */
    int getInt(String prompt, int max);

    /**
     * Prompts user for a Coordinate and returns it. Returns null if the user chooses to go back.
     * @param prompt an additional prompt for the user
     * @return the coordinate from the user, or null if the user chooses to go back
     */
    Coordinate getCoordinate(String prompt);
    void display(String prompt);
    void displayBoard(Board board, boolean showBombs);
}
