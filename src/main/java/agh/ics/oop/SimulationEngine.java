package agh.ics.oop;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SimulationEngine implements  Runnable, IEngine, IMapObserver,IPositionChangeObserver {

    private final List<Animal> animals = new ArrayList<>();
    private List<MoveDirection> moves;
    private final AbstractWorldMap map;
    private final Set<IMapObserver> observers = new HashSet<>();
    private int grassEnergy = 88;
    private int grassSpawnedEachDay = 1;

    public SimulationEngine( AbstractWorldMap map, Vector2d[] initialPositions) {
        this.map = map;
        map.addObserver(this);
        map.setJungle(0.4);

        map.setJungle(0.4);
        for (Vector2d pos : initialPositions) {

            Animal animal = new Animal(map, pos);
            animals.add(animal);
            map.place(animal);
        }
    }

    public void addObserver(IMapObserver observer) {
        observers.add(observer);
    }


    public void setGrassEnergy(int grassEnergy) {
        this.grassEnergy = grassEnergy;
    }

    public void setMoves(List<MoveDirection> moves) {
        this.moves=moves;
    }


    @Override
    public void run() {
        System.out.println("its running");

        int i = 0;
        while(i<100) {
            for(Animal animal: animals) {
                animal.moveWithPref();
            }
            map.spawnGrassOnSteppe(this.grassSpawnedEachDay);
//            map.removeDeadAnimals();
            i++;


            simulateDay();
        }

    }

    public void simulateDay() {
        for(IMapObserver observer: observers) {
            observer.simulateDay();
        }
    }


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {

    }
}