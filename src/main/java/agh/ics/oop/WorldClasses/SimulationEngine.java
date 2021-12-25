package agh.ics.oop.WorldClasses;



import agh.ics.oop.Enums.MoveDirection;
import agh.ics.oop.Interfaces.IEngine;
import agh.ics.oop.Interfaces.IMapObserver;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.WorldClasses.AbstractWorldMap;
import agh.ics.oop.WorldClasses.Animal;
import agh.ics.oop.WorldClasses.Vector2d;

import java.util.*;


public class SimulationEngine implements  Runnable, IEngine, IMapObserver {

    private final List<Animal> animals = new ArrayList<>();
    private List<MoveDirection> moves;
    private final AbstractWorldMap map;
    private final Set<IMapObserver> observers = new HashSet<>();
    private int grassEnergy = 88;
    private int grassSpawnedEachDay = 100;


    //constructor
    public SimulationEngine( AbstractWorldMap map, int numberOfAnimalsAtStart) {
        this.map = map;
        map.addObserver(this);
        spawnStartingAnimals(numberOfAnimalsAtStart);
    }


    public void spawnStartingAnimals(int numberOfAnimals) {
        Random rng = new Random();
        int i = 0;
        while(i<numberOfAnimals) {
            Vector2d animalPosition = new Vector2d(rng.nextInt(14), rng.nextInt(14));
            if (!map.isOccupied(animalPosition)) {
                Animal animal = new Animal(map, animalPosition, 10);
                animals.add(animal);
                map.place(animal);
                i++;
            }
        }
    }


    public void setGrassEnergy(int grassEnergy) {
        this.grassEnergy = grassEnergy;
    }


    public void setMoves(List<MoveDirection> moves) {
        this.moves=moves;
    }



    //notice engine observers about
    public void addObserver(IMapObserver observer) {
        observers.add(observer);
    }


    public void simulateDay() {
        for(IMapObserver observer: observers) {
            observer.simulateDay();
        }
    }


    @Override
    public void run() {

        int i = 0;
        while(i<20000) {
            for(Animal animal: animals) {
                animal.moveWithPref();
            }
            System.out.println(animals);

            map.startDay();
            this.removeDead();
            animals.addAll(map.getSpawnedAnimalsThisDay());

            i++;
            simulateDay();
            }
        }



    public void removeDead() {
        animals.removeIf(animal -> animal.getEnergy() <= 0);
    }

}