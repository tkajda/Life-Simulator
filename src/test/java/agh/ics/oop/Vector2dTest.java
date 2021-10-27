package agh.ics.oop;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class Vector2dTest {


    @Test
    public void equalsTest(){
        Vector2d v1 = new Vector2d(121,223);
        Vector2d v2 = new Vector2d(121,223);
        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v1));
    }

    @Test
    public void toStringTest() {
        assertEquals(new Vector2d(10, 11).toString(), "(10,11)");
    }

    @Test
    public void precedesTest() {
        Vector2d v1 = new Vector2d(10,11);
        Vector2d v2 = new Vector2d(10,9);
        Vector2d v3 = new Vector2d(11,9);
        assertTrue(v2.precedes(v1));
        assertTrue(v2.precedes(v3));
        assertFalse(v1.precedes(v3));
    }
    @Test
    public void followsTest() {
        Vector2d v1 = new Vector2d(10,20);
        Vector2d v2 = new Vector2d(20,30);
        Vector2d v3 = new Vector2d(30,29);
        assertTrue(v2.follows(v1));
        assertTrue(v3.follows(v1));
        assertFalse(v3.follows(v2));
    }


    @Test
    public void upperRightTest() {
        Vector2d v1 = new Vector2d(1000,-2121);
        Vector2d v2 = new Vector2d(20,20);
        Vector2d v3 = new Vector2d(1000,20);
        assertEquals(v3, v1.upperRight(v2));
    }

    @Test
    public void lowerLeft() {
        Vector2d v1 = new Vector2d(1000,-2121);
        Vector2d v2 = new Vector2d(20,20);
        Vector2d v3 = new Vector2d(20,-2121);
        assertEquals(v3,v1.lowerLeft(v2));
    }

    @Test
    public void addTest() {
        Vector2d v1 = new Vector2d(5,3);
        Vector2d v2 = new Vector2d(10,1);
        Vector2d v3 = new Vector2d(15,4);
        Vector2d v4 = new Vector2d(20,7);
        assertEquals(v1.add(v2), v3);
        assertEquals(v1.add(v3), v4);
    }
    @Test
    public void substractTest() {
        Vector2d v1 = new Vector2d(5,3);
        Vector2d v2 = new Vector2d(10,1);
        Vector2d v3 = new Vector2d(-5,2);
        Vector2d v4 = new Vector2d(15,-1);
        assertEquals(v1.substract(v2), v3);
        assertEquals(v2.substract(v4), v3);
    }

    @Test
    public void oppositeTest() {
        Vector2d v1 = new Vector2d(2121,-1);
        Vector2d v2 = new Vector2d(-2121,1);
        Vector2d v3 = new Vector2d(14,15);
        Vector2d v4 = new Vector2d(-14,-15);

        assertEquals(v1.opposite(), v2);
        assertEquals(v3.opposite(), v4);
        assertEquals(v2.opposite(), v1);
        assertEquals(v4.opposite(), v3);
    }


}
