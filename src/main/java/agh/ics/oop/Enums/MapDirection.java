package agh.ics.oop.Enums;


import agh.ics.oop.WorldClasses.Vector2d;

public enum MapDirection {
    NORTH,
    NE,
    EAST,
    SE,
    SOUTH,
    SW,
    WEST,
    NW;

    public String toString() {
        return switch (this) {
            case EAST -> "Wschód";
            case WEST -> "Zachód";
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
            case NE -> "Północny-wschód";
            case SE -> "Południowy-wschód";
            case SW -> "Południowy-zachód";
            case NW -> "Północny-zachód";
        };
    }
    public MapDirection next() {
        return switch(this) {
            case NORTH -> NE;
            case NE -> EAST;
            case EAST -> SE;
            case SE -> SOUTH;
            case SOUTH -> SW;
            case SW -> WEST;
            case WEST -> NW;
            case NW -> NORTH;

        };
    }
    public MapDirection previous() {
        return switch(this) {
            case NORTH -> NW;
            case NW -> WEST;
            case WEST -> SW;
            case SW -> SOUTH;
            case SOUTH -> SE;
            case SE -> EAST;
            case EAST -> NE;
            case NE -> NORTH;
        };
    }
    public Vector2d toUnitVector() {
        return switch(this) {
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1,0);
            case NORTH -> new Vector2d(0,1);
            case SOUTH -> new Vector2d(0,-1);
            case NE -> new Vector2d(1,1);
            case SE -> new Vector2d(1,-1);
            case SW -> new Vector2d(-1,-1);
            case NW -> new Vector2d(-1,1);
        };
    }
}