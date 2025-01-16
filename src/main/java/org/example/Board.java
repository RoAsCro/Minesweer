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
            Location current = this.locationList.remove(
                    random.nextInt(this.locationList.size()));
            current.mined = true;
            int currentX = current.x;
            int currentY = current.y;

            // Increases adjacency of all adjacent Locations by one
            for (int adjX = currentX-1; adjX <= currentX+1; adjX++) {
                for (int adjY = currentY-1; adjY <= currentY+1; adjY++) {
                    try {
                        getLocation(adjX, adjY).adjacency++;
                    } catch (BoardLimitException _) {
                    }
                }
            }
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

    public void reveal(int x, int y) {
        getLocation(x, y).revealed = true;
    }

    public Location getLocation(int x, int y){
       if (checkExceeds(x) || checkExceeds(y)) {
           throw new BoardLimitException();
       }
       return this.locations[x][y];
    }

    private boolean checkExceeds(int coord) {
        return coord < 0 || coord >= this.size;
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
        private final int x;
        private final int y;
        private boolean revealed = false;
        private boolean mined = false;
        private int adjacency = 0;

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
