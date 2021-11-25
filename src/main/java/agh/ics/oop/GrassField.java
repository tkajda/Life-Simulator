package agh.ics.oop;

import java.lang.Math;
import java.util.Random;


public class GrassField extends AbstractWorldMap {


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
}