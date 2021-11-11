package agh.ics.oop;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SimulationEngineTest {

    @Test
    void runTest() {

        //test 1
        String[] args3 = "f b r l".split(" ");
        MoveDirection[] directions3 = OptionsParser.parse(args3);

        IWorldMap map3 = new RectangularMap(10,10);
        Vector2d[] positions3 = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine3 = new SimulationEngine(directions3,map3, positions3);
        engine3.run();

        assertEquals(map3.getAnimal(0).getPos(), new Vector2d(2,3));
        assertEquals(map3.getAnimal(0).getDirection(), MapDirection.EAST);
        assertEquals(map3.getAnimal(1).getPos(), new Vector2d(3,3));
        assertEquals(map3.getAnimal(1).getDirection(), MapDirection.WEST);



        //test 1 on map1
        String[] args = "f b r l f f r r f f f f f f f f".split(" ");
        MoveDirection[] directions = OptionsParser.parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        assertTrue(map.isOccupied(new Vector2d(2,0)));
        assertTrue(map.isOccupied(new Vector2d(3,4)));
        assertFalse(map.isOccupied(new Vector2d(5,4)));
        assertFalse(map.isOccupied(new Vector2d(1,4)));


        //test 1 on map2
        String[] args1 = "f b r l f f r r f f f f f f f f b b b b b b b b b b b b".split(" ");
        MoveDirection[] directions1 = OptionsParser.parse(args1);
        IWorldMap map1 = new RectangularMap(10, 5);
        Vector2d[] positions1 = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine1 = new SimulationEngine(directions1,map1,positions1);
        engine1.run();

        assertTrue(map1.isOccupied(new Vector2d(2,4)));
        assertTrue(map1.isOccupied(new Vector2d(3,0)));
        assertFalse(map1.isOccupied(new Vector2d(5,4)));
        assertFalse(map1.isOccupied(new Vector2d(1,4)));


        //test 2 on map2;
        // 4 animals included;
        // wrong arguments included;
        String[] args2 = "f r l f f f x x x x x x x x b r l b f f f f l l r b b f f l r l f b".split(" ");
        MoveDirection[] directions2 = OptionsParser.parse(args2);
        Vector2d[] positions2 = {new Vector2d(8,4), new Vector2d(0,0)};
        IEngine engine2 = new SimulationEngine(directions2, map1, positions2);
        engine2.run();


        assertTrue(map1.isOccupied(new Vector2d(2,4)));
        assertTrue(map1.isOccupied(new Vector2d(3,0)));
        assertTrue(map1.isOccupied(new Vector2d(2,0)));
        assertTrue(map1.isOccupied(new Vector2d(7,2)));

        assertFalse(map1.isOccupied(new Vector2d(0,0)));
        assertFalse(map1.isOccupied(new Vector2d(2,2)));
        assertFalse(map1.isOccupied(new Vector2d(3,4)));
        assertFalse(map1.isOccupied(new Vector2d(8,4)));



    }

}
