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
    private int allEverLivingAnimals=0;
    private int DAY = 0;

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

        allEverLivingAnimals = animals.size();

        while(animals.size()>0) {

            for(Animal animal: animals) {

                animal.moveWithPref();
            }
            map.startDay();
            this.removeDead();

            animals.addAll(map.getSpawnedAnimalsThisDay());
            allEverLivingAnimals += map.getSpawnedAnimalsThisDay().size();

            DAY++;
            simulateDay();
        }
    }


    public int getDay() {
        return DAY;
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


    public int getDominantGene() {

        int[] list = {0,0,0,0,
                0,0,0,0};
        int maxAt = 0;

        for(Animal animal: animals) {
            for(Integer gene: animal.getGenes()) {
                list[gene]++;
            }
        }

        for (int i = 0; i < list.length; i++) {
            maxAt = list[i] > list[maxAt] ? i : maxAt;
        }
        return maxAt;
    }


    public List<Integer> findDominantGenotype() {
        int best = 0;
        int i = 0;
        int animalIndex = 0;
        for(Animal animal:animals) {
            i++;
            int cnt = 0;
            for(Animal animals1: animals) {
                if (animal.getGenes().equals(animals1.getGenes())) {
                    cnt++;
                }
            }
            if(cnt>best){
                best = cnt;
                animalIndex= i;
            }
        }
        return animals.get(animalIndex).getGenes();
    }



    public double getAverageNumOfChildren(){
        int sum=0;
        for(Animal animal: animals) {
            sum+=animal.getNumOfChildren();
        }
        if (animals.size() != 0) {
            return sum/allEverLivingAnimals;
        }
        return 0;
    }


}