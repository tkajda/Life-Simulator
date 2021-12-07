package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SimulationEngineTest {


    @Test
    void runTest() {


        //test 1 on map1
        String[] args = "f b r l f f r r f f f f f f f f".split(" ");
        List<MoveDirection> directions = OptionsParser.parse(args);
        IWorldMap map = new GrassField(10);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        assertFalse(map.objectAt(new Vector2d(2,2)) instanceof Animal);
        assertFalse((map.objectAt(new Vector2d(3,4)) instanceof Animal));
        assertTrue(map.isOccupied(new Vector2d(2,7)));
        assertTrue(map.isOccupied(new Vector2d(-1,-5)));


        //test 1 on map2
        String[] args1 = "f b r l f f r r f f f f f f f f b b b b b b b b b b b b".split(" ");
        List<MoveDirection> directions1 = OptionsParser.parse(args1);
        IWorldMap map1 = new GrassField(10);
        Vector2d[] positions1 = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine1 = new SimulationEngine(directions1,map1,positions1);
        engine1.run();

        assertFalse(map.objectAt(new Vector2d(2,2)) instanceof Animal);
        assertFalse(map.objectAt(new Vector2d(3,4)) instanceof Animal);
        assertTrue(map1.isOccupied(new Vector2d(-1,1)));
        assertTrue(map1.isOccupied(new Vector2d(2,1)));


        //test 2 on map2;
        // 4 animals included;
        // wrong arguments included;
        String[] args2 = "f r l f f f b r l b f f f f l l r b b f f l r l f b".split(" ");
        List<MoveDirection> directions2= OptionsParser.parse(args2);
        Vector2d[] positions2 = {new Vector2d(8,4), new Vector2d(0,0)};
        IEngine engine2 = new SimulationEngine(directions2, map1, positions2);
        engine2.run();


        assertTrue(map1.isOccupied(new Vector2d(-1,1)));
        assertTrue(map1.isOccupied(new Vector2d(-2,-1)));
        assertTrue(map1.isOccupied(new Vector2d(2,1)));
        assertTrue(map1.isOccupied(new Vector2d(0,-2)));

        assertFalse(map.objectAt(new Vector2d(0,0)) instanceof Animal);
        assertFalse(map.objectAt(new Vector2d(2,2)) instanceof Animal);
        assertFalse(map.objectAt(new Vector2d(3,4)) instanceof Animal);
        assertFalse(map.objectAt(new Vector2d(8,4)) instanceof Animal);

    }

}
