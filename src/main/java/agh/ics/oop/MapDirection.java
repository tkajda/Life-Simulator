package agh.ics.oop;


enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    public String toString(MapDirection a) {
        return switch (a) {
            case EAST -> "Wschód";
            case WEST -> "Zachód";
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
        };
    }
    public MapDirection next(MapDirection a) {
        return switch(a) {
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            case NORTH -> EAST;
        };
    }
    public MapDirection previous(MapDirection a) {
        return switch(a) {
            case NORTH -> WEST;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
            case EAST-> NORTH;
        };
    }
    public World.Vector2d toUnitVector (MapDirection a) {
        return switch(a) {
            case NORTH -> new World.Vector2d(0,1);
            case SOUTH -> new World.Vector2d(0,-1);
            case WEST -> new World.Vector2d(-1,0);
            case EAST-> new World.Vector2d(1,0);
        };
    }
}