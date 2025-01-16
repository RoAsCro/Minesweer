package org.example;

import java.util.*;

public class Board {

    private final int mines;
    private final int size;
    // Location in list is (x+1) * y
    private final List<Location> locationList = new ArrayList<>();
    // On a board, the first figure is the x, the second is the y
    private final Location[][] locations;


    public Board(int size, int mines) {
        this.locations = new Location[size][size];
        // Todo DON'T EXCEED locations
        this.mines = mines;
        this.size = size;
        preGenerate();
    }

    public void generate(int x, int y) {
        List<Location> unconsumed = new ArrayList<>(this.locationList);
        unconsumed.remove((x+1) * y);
        Random random = new Random();
        for (int i = 0; i < this.mines; i++) {
            int coord = random.nextInt(this.locationList.size());
            this.locationList.remove()
                    .mined = true;
        }
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

    private void preGenerate() {
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                Location current = new Location(x, y);
                this.locations[x][y] = current;
                this.locationList.add(current);
            }
        }
    }

    public class Location{
        private boolean revealed = false;
        private boolean mined = false;
        private int adjacency = 0;
        private final int x;
        private final int y;

        public Location(int x, int y) {
            this.x = x;
            this.y = y;
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
