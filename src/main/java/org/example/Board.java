package org.example;

import java.util.*;
import java.util.function.Consumer;

public class Board {

    private final int mines;
    private final int size;
    // Location in list is (x+1) * y
    private final List<Location> locationList = new ArrayList<>();
    // On a board, the first figure is the x, the second is the y
    private final Location[][] locations;
    private final BoardIterator iterator = new BoardIterator(this);


    public Board(int size, int mines) {
        this.locations = new Location[size][size];
        // Todo DON'T EXCEED locations
        this.mines = mines;
        this.size = size;
        preGenerate();
    }

    public void generate(Coordinate coord) {
        List<Location> unconsumed = new ArrayList<>(this.locationList);
        unconsumed.remove((coord.getX()+1) * coord.getY());
        Random random = new Random();
        for (int i = 0; i < this.mines; i++) {
            Location current = this.locationList.remove(
                    random.nextInt(this.locationList.size()));
            current.mined = true;
            int currentX = current.coord.getX();
            int currentY = current.coord.getY();
            // Increases adjacency of all adjacent Locations by one
            this.iterator.iterateAdjacent(c -> getLocation(c).adjacency++,
                    current.coord);

//            for (int adjX = currentX-1; adjX <= currentX+1; adjX++) {
//                for (int adjY = currentY-1; adjY <= currentY+1; adjY++) {
//                    try {
//                        getLocation(new Coordinate(adjX, adjY)).adjacency++;
//                    } catch (BoardLimitException _) {
//                    }
//                }
//            }
        }
    }

    public int getAdjacency(Coordinate coord) {
        return getLocation(coord).adjacency;
    }

    public BoardIterator getIterator() {
        return this.iterator;
    }

    public boolean isFlagged(Coordinate coord) {
        return getLocation(coord).flagged;
    }

    public boolean isRevealed(Coordinate coord) {
        return getLocation(coord).revealed;
    }

    public boolean isMined(Coordinate coord) {
        return getLocation(coord).mined;
    }

    public void flag(Coordinate coord) {
        getLocation(coord).flagged = true;
    }

    public void reveal(Coordinate coord) {
        getLocation(coord).revealed = true;
    }

    public Location getLocation(Coordinate coord) throws BoardLimitException {
       if (checkExceeds(coord.getX()) || checkExceeds(coord.getY())) {
           throw new BoardLimitException();
       }
       return this.locations[coord.getX()][coord.getY()];
    }

    private boolean checkExceeds(int coord) {
        return coord < 0 || coord >= this.size;
    }

    private void preGenerate() {
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                Location current = new Location(new Coordinate(x, y));
                this.locations[x][y] = current;
                this.locationList.add(current);
            }
        }
    }

    public class Location{
        private final Coordinate coord;
        private boolean revealed = false;
        private boolean mined = false;
        private boolean flagged = false;
        private int adjacency = 0;

        public Location(Coordinate coord) {
            this.coord = coord;
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

    public class BoardIterator {
        private final Board board;

        public BoardIterator(Board board) {
            this.board = board;
        }
        public void iterateAll(Consumer<Coordinate> consumer){

        }
        public void iterateAdjacent(Consumer<Coordinate> consumer, Coordinate coord) {
            for (int adjX = coord.getX()-1; adjX <= coord.getX()+1; adjX++) {
                for (int adjY = coord.getY()-1; adjY <= coord.getY()+1; adjY++) {
                    try {
                        consumer.accept(new Coordinate(adjX, adjY));
                    } catch (BoardLimitException _) {

                    }
                }
            }
        }
    }
}
