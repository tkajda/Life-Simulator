package agh.ics.oop;

import java.lang.Math;
import java.util.List;
import java.util.Random;


public class GrassField extends AbstractWorldMap {


    public GrassField(int grassNum) {
        Random generator = new Random();
        int i = 0;
        int a = (int) (Math.sqrt(10 * grassNum));
        while (i < grassNum) {
            Vector2d pos = new Vector2d(generator.nextInt(a), generator.nextInt(a));
            if (!isOccupied(pos)) {
                grassFields.add(new Grass(pos));
                i++;
            }
        }
        for (Animal animal : animals) {
            place(animal);
        }
    }


    public String toString() {
        return super.toString();
    }


    @Override
    public Vector2d[] getCorners() {
        int inf = Integer.MAX_VALUE;
        Vector2d bottomLeft = new Vector2d(inf, inf);
        Vector2d topRight = new Vector2d(-inf, -inf);

        for (Grass grass : grassFields) {
            topRight = topRight.upperRight(grass.getPosition());
            bottomLeft = bottomLeft.lowerLeft(grass.getPosition());
        }
        for (Animal a : animals) {
            topRight = topRight.upperRight(a.getPos());
            bottomLeft = bottomLeft.lowerLeft(a.getPos());
        }

        return new Vector2d[]{bottomLeft, topRight};
    }

    public Object objectAt(Vector2d position) {
        if (isOccupied(position)) {
            Object o = super.objectAt(position);
            if (o != null) {
                return o;
            }
            for (Grass i : grassFields) {
                if (i.getPosition().equals(position)) {
                    return i;
                }
            }
        }
        return null;
    }
}
