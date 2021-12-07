package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {


    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected Map<Vector2d, Grass> grassFields = new HashMap<>();
    private static final Vector2d MARGIN = new Vector2d(1,1);

    abstract Vector2d[] getCorners();

    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        Vector2d[] corners = this.getCorners();
        return visualizer.draw(corners[0].substract(MARGIN), corners[1].add(MARGIN));
    }


    @Override
    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPos())) {
            return false;
        }
        animals.put(animal.getPos(), animal);
        animal.addObserver(this);
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