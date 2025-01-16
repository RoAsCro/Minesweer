package org.example;

public class BoardLimitException extends RuntimeException {
    public BoardLimitException(String message) {
        super("That location is not on the board!");
    }
}
