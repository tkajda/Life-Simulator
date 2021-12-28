package agh.ics.oop;

import static org.junit.jupiter.api.Assertions.*;

import agh.ics.oop.Enums.MapDirection;
import agh.ics.oop.Enums.MoveDirection;
import agh.ics.oop.WorldClasses.Animal;
import agh.ics.oop.WorldClasses.Map;
import agh.ics.oop.WorldClasses.Vector2d;
import org.junit.jupiter.api.Test;


public class AnimalTest {

    Animal createAnimal() {
        Map map1 = new Map(5,5,0.2,1000,1,1,false);
        Animal a = new Animal(map1,new Vector2d(2,2),1000);
        a.setEnergy(1000,1);
        a.setRandomGene();
        return a;
    }
    Animal a = createAnimal();

    @Test
    void orientationTest() {


        MapDirection startingDir = a.getDirection();
        a.move(MoveDirection.FORWARD);
        assertSame(a.getDirection(), startingDir);
        a.move(MoveDirection.BACKWARD);
        assertSame(a.getDirection(),startingDir);
    }





    @Test
    void positionTest() {

        MapDirection startingDir = a.getDirection();
        a.move(MoveDirection.FORWARD);
        Vector2d v = new Vector2d(2,2);
        assertEquals(a.getPosition(),v.add(startingDir.toUnitVector()) );

        a.move(MoveDirection.FORWARD);
        assertEquals(a.getPosition(),v.add(startingDir.toUnitVector()).add(startingDir.toUnitVector()) );

        a.setPosition(new Vector2d(0,0));
        assertEquals(new Vector2d(0,0), a.getPosition());

    }

    @Test
    void energyTest() {

        assertEquals(a.getEnergy(), 1000);
        a.moveWithPref();
        assertEquals(a.getEnergy(), 999);

        a.eatGrass(10);
        assertEquals(a.getEnergy(), 1009);
    }


}





