package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.util.Random;


public class GrassField implements IWorldMap {

    Vector2d startingPosition = new Vector2d(2,2);
    List<Grass> grassFields = new ArrayList<>();
    MapVisualizer visualizer = new MapVisualizer(this);
    List<Animal> animals = new ArrayList<>();

    public GrassField(int grassNum) {
        Random generator = new Random();
        int i = 0;
        int a = (int) (Math.sqrt(10*grassNum));
        while (i<grassNum) {
            Vector2d pos = new Vector2d(generator.nextInt(a), generator.nextInt(a));
            if (!isOccupied(pos)) {
                grassFields.add(new Grass(pos));
                i++;
            }
        }
    }


    public String toString() {
        int inf = Integer.MAX_VALUE;
        Vector2d bl=new Vector2d(inf,inf);
        Vector2d tr=new Vector2d(-1,-1);


        for (Grass i: grassFields) {
            bl = bl.lowerLeft(i.getPosition());
            tr = tr.upperRight(i.getPosition());
        }
        for(Animal j: animals) {
            bl = bl.lowerLeft(j.getPos());
            tr = tr.upperRight(j.getPos());
        }
        return visualizer.draw(bl,tr);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return isOccupied(position);
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
    public boolean isOccupied(Vector2d position) {
        for (Animal j: animals) {
            if (j.isAt(position)) {
                return true;
            }
        }
        for (Grass i: grassFields) {
            if (i.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (isOccupied(position)) {
            for (Grass i : grassFields) {
                if (i.getPosition().equals(position)) {
                    return i;
                }
            }
        }
        return null;
    }
}
