package agh.ics.oop;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimulationEngineTest {


    @Test
    void runTest() {


        //test 1 on map1
        String[] args = "f b r l f f r r f f f f f f f f".split(" ");
        List<MoveDirection> directions = OptionsParser.parse(args);
        AbstractWorldMap map = new GrassField(10);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        SimulationEngine engine = new SimulationEngine(map, positions);
        engine.setMoves(directions);
        engine.run();

        assertFalse(map.objectAt(new Vector2d(2,2)) instanceof Animal);
        assertFalse((map.objectAt(new Vector2d(3,4)) instanceof Animal));
        assertTrue(map.isOccupied(new Vector2d(3,7)));
        assertTrue(map.isOccupied(new Vector2d(2,-1)));


        //test 1 on map2
        String[] args1 = "f b r l f f r r f f f f f f f f b b b b b b b b b b b b".split(" ");
        List<MoveDirection> directions1 = OptionsParser.parse(args1);
        AbstractWorldMap map1 = new GrassField(10);
        Vector2d[] positions1 = {new Vector2d(2, 2), new Vector2d(3, 4)};
        SimulationEngine engine1 = new SimulationEngine(map1,positions1);
        engine1.setMoves(directions1);
        engine1.run();

        assertFalse(map1.objectAt(new Vector2d(2,2)) instanceof Animal);
        assertFalse(map1.objectAt(new Vector2d(3,4)) instanceof Animal);
        assertTrue(map1.isOccupied(new Vector2d(2,5)));
        assertTrue(map1.isOccupied(new Vector2d(3,1)));


        //test 2 on map2;
        // 4 animals included;

        String[] args2 = "f r l f f f b r l b f f f f l l r b b f f l r l f b".split(" ");
        List<MoveDirection> directions2= OptionsParser.parse(args2);
        Vector2d[] positions2 = {new Vector2d(8,4), new Vector2d(0,0)};
        SimulationEngine engine2 = new SimulationEngine(map1, positions2);
        engine2.setMoves(directions2);
        engine2.run();


        assertTrue(map1.isOccupied(new Vector2d(3,-1)));
        assertTrue(map1.isOccupied(new Vector2d(3,1)));
        assertTrue(map1.isOccupied(new Vector2d(7,3)));
        assertTrue(map1.isOccupied(new Vector2d(2,5)));

        assertFalse(map1.objectAt(new Vector2d(0,0)) instanceof Animal);
        assertFalse(map1.objectAt(new Vector2d(2,2)) instanceof Animal);
        assertFalse(map1.objectAt(new Vector2d(3,4)) instanceof Animal);
        assertFalse(map1.objectAt(new Vector2d(8,4)) instanceof Animal);

    }

}
