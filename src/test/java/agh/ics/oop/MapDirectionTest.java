package agh.ics.oop;

import static org.junit.jupiter.api.Assertions.*;

import agh.ics.oop.Enums.MapDirection;
import org.junit.jupiter.api.Test;

public class MapDirectionTest {

    @Test
    void nextTest() {
        assertEquals(MapDirection.NE, MapDirection.NORTH.next());
        assertEquals(MapDirection.EAST, MapDirection.NE.next());
        assertEquals(MapDirection.SE, MapDirection.EAST.next());
        assertEquals(MapDirection.SOUTH, MapDirection.SE.next());
        assertEquals(MapDirection.SW, MapDirection.SOUTH.next());
        assertEquals(MapDirection.WEST, MapDirection.SW.next());
        assertEquals(MapDirection.NW, MapDirection.WEST.next());
        assertEquals(MapDirection.NORTH, MapDirection.NW.next());
    }
    @Test
    void previousTest() {
        assertEquals(MapDirection.NORTH, MapDirection.NE.previous());
        assertEquals(MapDirection.NW, MapDirection.NORTH.previous());
        assertEquals(MapDirection.WEST, MapDirection.NW.previous());
        assertEquals(MapDirection.SW, MapDirection.WEST.previous());
        assertEquals(MapDirection.SOUTH, MapDirection.SW.previous());
        assertEquals(MapDirection.SE, MapDirection.SOUTH.previous());
        assertEquals(MapDirection.EAST, MapDirection.SE.previous());
        assertEquals(MapDirection.NE, MapDirection.EAST.previous());
    }

}
