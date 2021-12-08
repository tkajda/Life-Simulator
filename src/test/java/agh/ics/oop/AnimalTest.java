package agh.ics.oop;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class AnimalTest {
    RectangularMap map1 = new RectangularMap(5,5);
    Animal abc = new Animal(map1,new Vector2d(2,2));

    @Test
    void orientationTest() {

//        System.out.println(map1.getTopRight().x);
//        System.out.println(map1.getTopRight().y);

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


        MoveDirection[] moves = {MoveDirection.FORWARD,MoveDirection.FORWARD,
                MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD,MoveDirection.FORWARD};
        for(int i=0; i<10; i++) {
            for(MoveDirection dir: moves) {
                abc.move(dir);
            }
            abc.move(MoveDirection.LEFT);

//            assertTrue(abc.getPos().x <= map1.getTopRight().x && 0 <= abc.getPos().x &&
//                    abc.getPos().y <= map1.getTopRight().y && 0 <= abc.getPos().y);
        }
    }


    @Test
    void positionTest() {


        Vector2d[] vectors = {new Vector2d(-1,-1),
                                new Vector2d(-1,1),
                                new Vector2d(-5,-1),
                                new Vector2d(-1,0)};

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


    @Test
    void testMove() {
        Animal a = new Animal(new RectangularMap(5,5),new Vector2d(2,2));

        assertEquals(new Vector2d(2, 2), a.getPosition());
        assertEquals(MapDirection.NORTH, a.getDirection());

        MoveDirection[] moves = {MoveDirection.FORWARD,MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.RIGHT,
                        MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD,MoveDirection.RIGHT, MoveDirection.FORWARD,
                        MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.FORWARD,
                        MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.LEFT,
                        MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD};

        Vector2d[] positions = {new Vector2d(2, 3), new Vector2d(-1, -1), new Vector2d(0, -1),
                new Vector2d(-1, -1), new Vector2d(-1, -2), new Vector2d(-1, -3),
                new Vector2d(-1, -4), new Vector2d(-1, -1), new Vector2d(-2, -1),
                new Vector2d(-3, -1), new Vector2d(-4, -1), new Vector2d(-5, -1),
                new Vector2d(-1, -1), new Vector2d(-1, 0), new Vector2d(0, 1)};

        MapDirection[]  directions = { MapDirection.NORTH ,MapDirection.EAST ,MapDirection.EAST ,
                MapDirection.SOUTH ,MapDirection.SOUTH ,MapDirection.SOUTH ,MapDirection.SOUTH ,
                MapDirection.WEST ,MapDirection.WEST ,MapDirection.WEST ,MapDirection.WEST ,
                MapDirection.WEST ,MapDirection.NORTH ,MapDirection.NORTH ,MapDirection.WEST ,
                MapDirection.WEST , MapDirection.WEST ,MapDirection.WEST ,MapDirection.SOUTH ,
                MapDirection.SOUTH , MapDirection.SOUTH , MapDirection.SOUTH ,MapDirection.SOUTH ,
        };

        int i = 0;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());
        i++;
              a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());
        i++;

        a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());
        i++;
        a.move(moves[i]);
        assertEquals(positions[i], a.getPosition());
        assertEquals(directions[i], a.getDirection());

    }
}





