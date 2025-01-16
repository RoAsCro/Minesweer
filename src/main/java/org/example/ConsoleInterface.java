package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInterface implements UserInterface {
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
        return null;
    }

    @Override
    public void display(String prompt) {

    }

    @Override
    public void displayBoard(Board board) {

    }
}
