package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.util.Random;


public class GrassField implements IWorldMap {

    List<Grass> grassFields = new ArrayList<>();
    Random generator = new Random();
    private int num;
    RectangularMap map = new RectangularMap(100, 100);
    MapVisualizer visualizer = new MapVisualizer(map);

    public GrassField(int n) {
        this.num = n;
    }
    int a = (int) (Math.sqrt(10*num));

    public String toString() {
        Vector2d bottomLeft= new Vector2d(99999, 99999);
        Vector2d topRight = new Vector2d(-1,-1);


        for (Grass i: grassFields) {
            Vector2d v = i.getPosition();

        }

        return visualizer.draw(bottomLeft,topRight);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
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


    public void putGrass() {
        int i = 0;
        while (i<=num) {
            Vector2d v = new Vector2d(generator.nextInt(a),generator.nextInt(a));
            if (!isOccupied(v)) {
                grassFields.add(new Grass(v));
                i++;
            }
        }
    }


}
