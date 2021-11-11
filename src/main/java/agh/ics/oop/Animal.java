package agh.ics.oop;

class Animal {
    private MapDirection orient = MapDirection.NORTH;
    private Vector2d v = new Vector2d(2,2);
    private final IWorldMap map;

    public Animal(IWorldMap map) {
        this.map = map;
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.v = initialPosition;

    }
    public String toString() {
        return switch(this.orient) {
            case NORTH -> " ^ ";
            case SOUTH -> " v ";
            case EAST -> " > ";
            case WEST -> " < ";
        };
    }

    boolean isAt(Vector2d position) {return getPos().equals(position);}

    public MapDirection getDirection() {return orient;}

    public Vector2d getPos() {return v;}

    public void move(MoveDirection direction)  {

        switch (direction) {
            case RIGHT -> orient = orient.next();
            case LEFT -> orient = orient.previous();
        }

        Vector2d newPos= new Vector2d(-1,-1);

        switch(direction) {
            case FORWARD -> newPos=this.v.add(this.orient.toUnitVector());
            case BACKWARD -> newPos=this.v.add(this.orient.toUnitVector().opposite());
        }
        if (map.canMoveTo(newPos)) {
            this.v = newPos;
        }

    }


}
