package agh.ics.oop;

import agh.ics.oop.Enums.MapDirection;
import agh.ics.oop.Enums.MoveDirection;
import agh.ics.oop.WorldClasses.Animal;
import agh.ics.oop.WorldClasses.BorderlessMap;
import agh.ics.oop.WorldClasses.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BorderlessMapTest {

    BorderlessMap createMap() {
        return new BorderlessMap(25,25,0.2,100,1,1,false);
    }

    Animal create(BorderlessMap map) {
        Animal a = new Animal(map, new Vector2d(24,5), 100);
        map.place(a);
        a.setRandomGene();
        a.setEnergy(100,1);
        return a;
    }
    BorderlessMap map = createMap();
    Animal a = create(map);

    @Test
    void calcNewPositionTest() {
        a.setOrient(MapDirection.EAST);
        a.move(MoveDirection.FORWARD);
        assertEquals(a.getPosition(), new Vector2d(0,5));

        a.setOrient(MapDirection.SW);
        a.move(MoveDirection.FORWARD);
        assertEquals(a.getPosition(), new Vector2d(24,4));
    }



}
