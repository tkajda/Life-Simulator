package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    void testEach() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(map, new Vector2d(1,1));
        Animal animal2 = new Animal(map, new Vector2d(1,1));


        assertFalse(map.isOccupied(new Vector2d(5,5)));
        assertFalse(map.canMoveTo(new Vector2d(-1,0)));
        assertTrue(map.place(animal));
        assertNull(map.objectAt(new Vector2d(0,0)));


        assertTrue(map.isOccupied(new Vector2d(1,1)));
        assertTrue(map.canMoveTo(new Vector2d(3,3)));
        assertFalse(map.place(animal2));
        assertEquals(map.objectAt(new Vector2d(1, 1)), animal);


    }

    @Test
    void testMovement() {
        RectangularMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(map, new Vector2d(2, 2));

        assertTrue(map.place(animal));

        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertFalse(map.isOccupied(new Vector2d(2, 3)));

        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);

        assertFalse(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.isOccupied(new Vector2d(2, 0)));
    }
}