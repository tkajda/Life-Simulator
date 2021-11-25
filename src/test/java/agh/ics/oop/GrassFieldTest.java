package agh.ics.oop;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class GrassFieldTest {
    @Test
    void testMovement() {
        IWorldMap map = new GrassField(10);
        Animal animal = new Animal(map, new Vector2d(2, 2));

        assertTrue(map.place(animal));

        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertEquals(animal, map.objectAt(new Vector2d(2, 2)));
        assertFalse(map.canMoveTo(new Vector2d(2, 2)));
        assertTrue(map.canMoveTo(new Vector2d(2, 3)));

        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);

        System.out.println(animal.getPos());
        assertTrue(map.isOccupied(new Vector2d(2, 0)));
        assertEquals(animal, map.objectAt(new Vector2d(2, 0)));
        assertTrue(map.canMoveTo(new Vector2d(2, 2)));
        assertFalse(map.canMoveTo(new Vector2d(2, 0)));
    }

}