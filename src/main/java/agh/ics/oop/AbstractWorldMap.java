package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {


    public static MapBoundary boundedMap= new MapBoundary();
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected Map<Vector2d, Grass> grassFields = new HashMap<>();
    private static final Vector2d MARGIN = new Vector2d(1,1);


    public Vector2d getBottomLeft() {
        return boundedMap.getBottomLeft();
    }
    public Vector2d getTopRight() {
        return boundedMap.getTopRight();
    }



    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);

        return visualizer.draw(boundedMap.getBottomLeft().substract(MARGIN), boundedMap.getTopRight().add(MARGIN));
    }


    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        Object a = objectAt(animal.getPosition());
        if (!canMoveTo(animal.getPosition())) {
            if (a instanceof Animal) {
                throw new IllegalArgumentException("cannot place at " + animal.getPosition());
            }
        }
        animals.put(animal.getPosition(), animal);
        boundedMap.addToMap( animal.getPosition(), animal);
        animal.addObserver(this);
        animal.addObserver(boundedMap);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !animals.containsKey(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position) || grassFields.containsKey(position);
    }


    @Override
    public Object objectAt(Vector2d position) {

        if (isOccupied(position)) {
            if (animals.get(position) != null){
                return animals.get(position);
            }
            return grassFields.get(position);
        }
        return null;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal a = animals.remove(oldPosition);
        animals.put(newPosition, a);
    }


}