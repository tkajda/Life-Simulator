package agh.ics.oop;

import static org.junit.jupiter.api.Assertions.*;

import agh.ics.oop.Enums.MoveDirection;
import agh.ics.oop.WorldClasses.*;
import org.junit.jupiter.api.Test;

class GrassFieldTest {
    @Test
    void testMovement() {
        AbstractWorldMap map = new GrassField(10);
        Animal animal = new Animal(map, new Vector2d(2, 2));
        Animal animal1 = new Animal(map, new Vector2d(2, 2));
        // test grass <=> scan map before putting animal innit
        int a = 10, cnt = 0;
        for(int i=0; i<a; i++) {
            for (int j=0; j<a; j++) {
                Vector2d v = new Vector2d(i,j);
                if (map.objectAt(v) instanceof Grass) {
                    cnt++;
                }

            }
        }
        assertEquals(10, cnt);
        assertTrue(map.place(animal));
        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertEquals(animal, map.objectAt(new Vector2d(2, 2)));
        assertFalse(map.canMoveTo(new Vector2d(2, 2)));
        assertTrue(map.canMoveTo(new Vector2d(2, 3)));


        assertThrows(IllegalArgumentException.class, () ->
                                            map.place(animal1));


        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.BACKWARD);

        assertTrue(map.isOccupied(new Vector2d(2, 0)));
        assertEquals(animal, map.objectAt(new Vector2d(2, 0)));
        assertTrue(map.canMoveTo(new Vector2d(2, 2)));
        assertFalse(map.canMoveTo(new Vector2d(2, 0)));

    }

}