package org.example;

public class Board {
    // On a board, the first figure is the x, the second is the y
    private final Location[][] locations;
    private final int mines;
    private final int size;

    public Board(int size, int mines) {
        this.locations = new Location[size][size];
        // Todo DON'T EXCEED locations
        this.mines = mines;
        this.size = size;
    }

    public int getAdjacency(int x, int y) {
        return getLocation(x, y).adjacency;
    }

    public boolean isRevealed(int x, int y) {
        return getLocation(x, y).revealed;
    }

    public boolean isMined(int x, int y) {
        return getLocation(x, y).mined;
    }

    public Location getLocation(int x, int y){
       checkExceeds(x);
       checkExceeds(y);
        return this.locations[x][y];
    }

    private void checkExceeds(int coord) {
        if (coord < 0 || coord >= this.size) {
            throw new BoardLimitException();
        };
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
