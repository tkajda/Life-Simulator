package agh.ics.oop;



import java.util.*;


public class SimulationEngine implements  Runnable, IEngine, IMapObserver,IPositionChangeObserver {

    private final List<Animal> animals = new ArrayList<>();
    private List<MoveDirection> moves;
    private final AbstractWorldMap map;
    private final Set<IMapObserver> observers = new HashSet<>();
    private int grassEnergy = 88;
    private int grassSpawnedEachDay = 1;

    public SimulationEngine( AbstractWorldMap map) {
        this.map = map;
        map.addObserver(this);
        map.setJungle(0.4);

        map.setJungle(0.4);
        spawnStartingAnimals(10);
    }


    public void spawnStartingAnimals(int numberOfAnimals) {
        Random rng = new Random();
        int i = 0;
        while(i<numberOfAnimals) {
            Vector2d animalPosition = new Vector2d(rng.nextInt(20), rng.nextInt(20));
            if (!map.isOccupied(animalPosition)) {
                Animal animal = new Animal(map, animalPosition);
                animals.add(animal);
                map.place(animal);
                i++;
            }
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
        while(i<200) {
            for(Animal animal: animals) {
                animal.moveWithPref();
            }
            map.spawnGrassOnSteppe(this.grassSpawnedEachDay);
            map.eatPlants();
//            map.removeDeadAnimals();

            this.removeDead();

            i++;

            System.out.println("day" + i);
            simulateDay();
        }
        System.out.println(animals);
        System.out.println(map);

    }

    public void removeDead() {
        animals.removeIf(animal -> animal.getEnergy() <= 0);
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