package org.example;

public class Board {
    private final Location[][] locations;
    private final int mines;
    private final int size;

    public Board(int size, int mines) {
        this.locations = new Location[size][size];
        // Todo DON'T EXCEED locations
        this.mines = mines;
        this.size = size;
    }

    public boolean isRevealed(int x, int y) {
        return getLocation(x, y).mined;
    }

    public boolean isMined(int x, int y) {
        Location location = getLocation(x, y);

        return location == null ? null : location.mined;
    }

    public Location getLocation(int x, int y){
        if (checkExceeds(x) || checkExceeds(y)) {
            return null;
        }
        return this.locations[x][y];
    }

    private boolean checkExceeds(int coord) {
        return coord < 0 || coord >= this.size;
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
