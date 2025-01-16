package org.example;

import java.util.Objects;

public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate c) {
            return c.x == this.x && c.y == this.y;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.x, this.y);
    }
}
