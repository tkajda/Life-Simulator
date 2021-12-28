package agh.ics.oop;

import agh.ics.oop.Enums.MapDirection;
import agh.ics.oop.WorldClasses.Animal;
import agh.ics.oop.WorldClasses.EnergyComparator;
import agh.ics.oop.WorldClasses.Map;
import agh.ics.oop.WorldClasses.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnergyComparatorTest {

    Map createMap() {
        return new Map(7,7,0.2,100,1,1,false);
    }
    Map map = createMap();


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
    void compareEnergyTest() {
        Animal A = createParentA();
        Animal B = createParentB();
        ArrayList<Animal> arr = new ArrayList<>();
        arr.add(A);
        arr.add(B);
        assertEquals(arr.size(),2);
        arr.sort(new EnergyComparator());
        assertEquals(arr.size(),2);
        B.moveWithPref();
        arr.sort(new EnergyComparator());
        assertEquals(arr.get(0), A);
        assertEquals(arr.get(1), B);
    }

}
