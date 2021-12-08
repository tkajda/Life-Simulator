package agh.ics.oop;

import java.lang.Math;
import java.util.Random;


public class GrassField extends AbstractWorldMap {


    public GrassField(int grassNum) {
        Random generator = new Random();
        int i = 0;
        int a = (int) (Math.sqrt(10 * grassNum));

//        Grass grass22 = new Grass(new Vector2d(2,2));
//        grassFields.put(grass22.getPosition(), grass22);
//        boundedMap.addToMap(grass22, grass22.getPosition());
//
//
//        Grass grass34 = new Grass(new Vector2d(3,4));
//        grassFields.put(grass34.getPosition(), grass34);
//        boundedMap.addToMap(grass34, grass34.getPosition());

        while (i < grassNum) {
            Vector2d pos = new Vector2d(generator.nextInt(a), generator.nextInt(a));
            if (!super.isOccupied(pos)) {
                Grass ngrass = new Grass(pos);

                grassFields.put(pos, ngrass);
                boundedMap.addToMap(ngrass, ngrass.getPosition());
                i++;
            }
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

        for (Grass grass : grassFields.values()) {
            topRight = topRight.upperRight(grass.getPosition());
            bottomLeft = bottomLeft.lowerLeft(grass.getPosition());
        }
        for (Animal a : animals.values()) {
            topRight = topRight.upperRight(a.getPosition());
            bottomLeft = bottomLeft.lowerLeft(a.getPosition());
        }

        return new Vector2d[]{bottomLeft, topRight};
    }

    public Object objectAt(Vector2d position) {
        if (isOccupied(position)) {
            Object o = super.objectAt(position);
            if (o != null) {
                return o;
            }
            if (grassFields.containsKey(position)) {
                return grassFields.get(position);
            }
        }
        return null;
    }

}