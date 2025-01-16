package org.example;

public class Board {
    private final Location[][] locations;
    private final int mines;

    public Board(int size, int mines) {
        this.locations = new Location[size][size];
        // Todo DON'T EXCEED locations
        this.mines = mines;
    }

    public boolean isMined(int x, int y) {
        return getLocation(x, y).mined;
    }

    public print

    private Location getLocation(int x, int y){
        return this.locations[x][y];
    }

    public class Location{
        private boolean revealed;
        private final boolean mined;
        private int adjacency;

        public Location(boolean mined) {
            this.mined = mined;
        }

        public boolean isRevealed() {
            return revealed;
        }

        public boolean isMined() {
            return this.mined;
        }

        public int getAdjacency() {
            return adjacency;
        }
    }
}
