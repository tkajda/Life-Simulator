package agh.ics.oop.WorldClasses;

import agh.ics.oop.Interfaces.IEngine;
import agh.ics.oop.Interfaces.IMapObserver;

import java.util.*;


public class SimulationEngine implements  Runnable, IEngine, IMapObserver {

    private final List<Animal> animals = new ArrayList<>();
    private final Map map;
    private final Set<IMapObserver> observers = new HashSet<>();
    private final int mapWidth;
    private final int mapHeight;
    public int avgLifeLen = 0;
    public int deadAnimals = 0;

    //constructor
    public SimulationEngine(Map map, int numberOfAnimalsAtStart, int mapWidth, int mapHeight) {
        this.map = map;
        map.addObserver(this);
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        spawnStartingAnimals(numberOfAnimalsAtStart);

    }



    public void spawnStartingAnimals(int numberOfAnimals) {
        Random rng = new Random();
        int i = 0;

        while(i<numberOfAnimals) {
            Vector2d animalPosition = new Vector2d(rng.nextInt(mapWidth-1), rng.nextInt(mapHeight-1));
            if (!map.isOccupied(animalPosition)) {
                Animal animal = new Animal(map, animalPosition, 10);
                animals.add(animal);
                map.place(animal);
                i++;
            }
        }
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
        System.out.println("silmulation has started!");

        while(animals.size()>0) {

            for(Animal animal: animals) {

                animal.moveWithPref();
            }
            map.startDay();
            this.removeDead();
            animals.addAll(map.getSpawnedAnimalsThisDay());
            i++;
            simulateDay();
        }
        System.out.println("days passed" + i);
    }


    public int getNumOfLivingAnimals() {
        return animals.size();
    }

    public int getAvarageEnergy() {

        int sum=0;
        for(Animal animal: animals) {
            sum+= animal.getEnergy();
        }
        if(animals.size()==0) {
            return 0;
        }
        return sum/animals.size();
    }

    public void removeDead(){
        for (Animal animal:animals) {
            if(animal.getEnergy()<=0) {
                avgLifeLen+=animal.lengthOfLife;
                deadAnimals++;
            }
        }
        animals.removeIf(animal -> animal.getEnergy() <= 0);
    }

    public double getAvgLifeLen() {
        if (deadAnimals!=0) {
            return avgLifeLen/deadAnimals;
        }
        return 0;
    }

    public int getNumOfGrass(){
        return map.getNumOfGrass();
    }




}