package agh.ics.oop;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class AnimalTest {

    Animal abc = new Animal();

    @Test
    void orientationTest() {

        MoveDirection[][] moves = {
                {MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.RIGHT}, //test1
                {MoveDirection.FORWARD,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.LEFT,MoveDirection.BACKWARD,MoveDirection.BACKWARD}, //test2
                {MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.LEFT}, //test3
                {MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.LEFT,MoveDirection.LEFT} //test4
        };

        MapDirection[] result = {MapDirection.NORTH, MapDirection.SOUTH, MapDirection.EAST,MapDirection.WEST};
        int i = 0;

        for(MoveDirection[] p1: moves) {
            for(MoveDirection a: p1) {
                abc.move(a);
            }
            assertEquals(abc.getDirection(), result[i]);
            i++;

        }
    }


    @Test
    void isAtMapTest() {

        MoveDirection[] moves = {MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD};
        for(int i=0; i<10; i++) {
            for(MoveDirection dir: moves) {
                abc.move(dir);
            }
            abc.move(MoveDirection.LEFT);

            assertTrue(abc.getPos().x <= 4 && 0 <= abc.getPos().x &&
                    abc.getPos().y <= 4 && 0 <= abc.getPos().y);
        }
    }


    @Test
    void positionTest() {

        Vector2d[] vectors = {new Vector2d(3,1),
                                new Vector2d(3,4),
                                new Vector2d(0,2),
                                new Vector2d(1,3)};

        MoveDirection[][] moves = {
                {MoveDirection.BACKWARD,MoveDirection.LEFT,MoveDirection.BACKWARD,MoveDirection.RIGHT}, //test1
                {MoveDirection.FORWARD,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.LEFT,MoveDirection.BACKWARD,MoveDirection.BACKWARD}, //test2
                {MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.LEFT,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD}, //test3
                {MoveDirection.RIGHT,MoveDirection.RIGHT,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.BACKWARD,MoveDirection.LEFT,MoveDirection.FORWARD} //test4
        };
        int i = 0;

        for(MoveDirection[] p: moves) {
            for(MoveDirection dir: p) {
                abc.move(dir);

            }
        assertTrue(abc.isAt(vectors[i]));
        i++;

        }
    }

}





