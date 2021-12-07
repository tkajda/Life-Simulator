package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class World {

    public static void main(String[] args) {

        try {
            List<MoveDirection> directions= OptionsParser.parse(args);
            IWorldMap field = new GrassField(10);
            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
            IEngine engine = new SimulationEngine(directions, field, positions);
            engine.run();

        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex);
            System.exit(0);

        }

    }
}