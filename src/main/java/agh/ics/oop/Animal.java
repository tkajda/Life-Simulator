package agh.ics.oop;


import java.util.*;

class Animal  implements IMapElement{
    private MapDirection orient = MapDirection.NORTH;
    private Vector2d v = new Vector2d(2,2);
    private final IWorldMap map;
    private final Set<IPositionChangeObserver> observers = new HashSet<>();

    public Animal(IWorldMap map) {
        this.map = map;
    }


    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPos, Vector2d newPos) {
        for (IPositionChangeObserver observer: observers) {
            observer.positionChanged(oldPos, newPos);
        }
    }


    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.v = initialPosition;

    }
    public String toString() {
        return switch(this.orient) {
            case NORTH -> "^";
            case SOUTH -> "v";
            case EAST -> ">";
            case WEST -> "<";
        };
    }
    @Override
    public String getName() {
        return "Z"+ " " + this.getPosition().toString();
    }


    @Override
    public String imageAddress() {
        String x=
        switch(this.getDirection()) {
            case NORTH -> "up";
            case SOUTH -> "down";
            case EAST -> "right";
            case WEST -> "left";
        };
        return "src/main/resources/" + x + ".png";

    }

    boolean isAt(Vector2d position) {return getPosition().equals(position);}

    public MapDirection getDirection() {return orient;}

    public Vector2d getPosition() {return v;}

    public void move(MoveDirection direction)  {

        switch (direction) {
            case RIGHT -> this.orient = orient.next();
            case LEFT -> this.orient = orient.previous();
        }
        Vector2d newPos = this.getPosition();

        switch(direction) {
            case FORWARD -> newPos=this.v.add(this.orient.toUnitVector());
            case BACKWARD -> newPos=this.v.add(this.orient.toUnitVector().opposite());
        }
        if (map.canMoveTo(newPos) || newPos.equals(this.getPosition())) {
            positionChanged(this.v, newPos);
            this.v = newPos;
        }

    }


}