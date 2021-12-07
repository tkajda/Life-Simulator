package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;


public class SimulationEngine implements IEngine {

    private final List<Animal> animals = new ArrayList<>();
    private final List<MoveDirection> moves;
    private final IWorldMap map;

    public SimulationEngine(List<MoveDirection> moves, IWorldMap map, Vector2d[] initialPositions) {
        this.moves = moves;
        this.map = map;

        for (Vector2d pos : initialPositions) {

            Animal animal = new Animal(map, pos);
            animals.add(animal);

            map.place(animal);

        }
    }

    @Override
    public void run() {
        System.out.println(map);
        int currentAnimal = 0;
        for (MoveDirection oneMove : moves) {
            animals.get(currentAnimal).move(oneMove);
            currentAnimal = (currentAnimal + 1) % animals.size();
        }
        System.out.println(map);
    }
}