package org.example;

public class Board {
    private final Location[][] locations;
    private final int mines;

    public Board(int size, int mines) {
        this.locations = new Location[size][size];
        // DON'T EXCEED
        this.mines = mines;
    }

    public boolean isMined(int x, int y) {

    }


    private class Location{
        boolean revealed;
        boolean mined;
        int adjacency;
    }
}
