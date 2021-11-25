package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap {


    protected List<Animal> animals = new ArrayList<>();
    List<Grass> grassFields = new ArrayList<>();
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
        animals.add(animal);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        for (Animal i: animals) {
            if (i.getPos().equals(position)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal i: animals) {
            if (i.getPos().equals(position)) {
                return true;
            }
        }
        for (Grass j: grassFields) {
            if(j.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (isOccupied(position)) {
            for (Animal i : animals) {
                if (i.getPos().equals(position)) {
                    return i;
                }
            }
        }
        return null;
    }

}
