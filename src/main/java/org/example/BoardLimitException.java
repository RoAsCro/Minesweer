package org.example;

@Deprecated
public class BoardLimitException extends RuntimeException {
    public BoardLimitException(String message) {
        super(message);
    }

    public BoardLimitException() {
        super("That location is not on the board!");
    }
}
