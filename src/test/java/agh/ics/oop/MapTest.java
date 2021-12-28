package agh.ics.oop;

import agh.ics.oop.Enums.MapDirection;
import agh.ics.oop.Enums.MoveDirection;
import agh.ics.oop.WorldClasses.Animal;
import agh.ics.oop.WorldClasses.Map;
import agh.ics.oop.WorldClasses.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {

    Map createMap() {
     return new Map(7,7,0.2,100,1,1,false);
    }
    Map map = createMap();


    @Test
    void spawnGrassTest() {
        map.startDay();
        map.startDay();
        map.startDay();


        assertTrue(map.getNumOfGrass()>2);

    }
    Animal createAnimal() {
        Animal a = new Animal(map,new Vector2d(2,2),100);
        a.setRandomGene();
        a.setEnergy(100,1);
        map.place(a);
        return a;
    }

    @Test
    void objectAtTest() {
        Animal a = createAnimal();
        assertTrue(map.isOccupied(new Vector2d(2,2)));
        assertNotNull(map.objectAt(new Vector2d(2, 2)));
        assertTrue(map.canMoveTo(new Vector2d(3,3)));
    }

    @Test
    void removeDeadAnimalsTest() {
        Animal a = createAnimal();
        a.setEnergy(0,1);
        map.removeDeadAnimals();
        assertNull(map.objectAt(new Vector2d(2,2)));
    }



    @Test
    void jungleTest() {
        Map map2= new Map(10,10,0.5,100,1,1,false);
        assertEquals(map.getJungleBL(), new Vector2d(1,3));
        assertEquals(map.getJungleTR(), new Vector2d(5,4));
        assertEquals(map2.getJungleBL(), new Vector2d(2,2));
        assertEquals(map2.getJungleTR(), new Vector2d(8,8));

    }


    Animal createParentA() {
        Animal A = new Animal(map,new Vector2d(2,2), 100);
        A.setEnergy(100,1);
        A.setRandomGene();
        map.place(A);
        return A;
    }

    Animal createParentB() {
        Animal B = new Animal(map,new Vector2d(2,1), 100);
        B.setOrient(MapDirection.NORTH);
        B.setEnergy(100,1);
        B.setRandomGene();
        map.place(B);
        return B;
    }

    @Test
    void copulationTest() {
        Animal A = createParentA();
        Animal B = createParentB();
        assertEquals(map.getNumOfAnimals(), 2);
        B.move(MoveDirection.FORWARD);
        map.startDay();
        assertEquals(map.getNumOfAnimals(),3);
    }








}
