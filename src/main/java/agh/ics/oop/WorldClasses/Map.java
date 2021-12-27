package agh.ics.oop.WorldClasses;



import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.Interfaces.IMapObserver;
import agh.ics.oop.Interfaces.IWorldMap;

import java.lang.reflect.Array;
import java.util.*;

public class Map implements IWorldMap, IPositionChangeObserver, IMapObserver {


    //map objects
    protected java.util.Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    protected java.util.Map<Vector2d, Grass> grassFields = new HashMap<>();
    private static final Vector2d MARGIN = new Vector2d(1,1);
    public ArrayList<Animal> spawnedAnimalsThisDay = new ArrayList<>();

    //map properies
    protected final int mapWidth; //placeholder
    protected final int mapHeight; //placeholder
    private final double jungleRatio; //jungle surface ratio comparing to map surface
    private final Vector2d mapBL =  new Vector2d(0,0);
    private final Vector2d mapTR;
    private final Set<IMapObserver> observers = new HashSet<>();
    private boolean isMagic = false; //default
    private int magicHappened = 0;

    //grass properties
    protected int plantEnergy;
    private static final int grassSpawnedEachDay = 1;
    private static final int MAGICNUMBER = 5;
    private static final int MAGICTIMES = 3;

    //animal properties
    private final int moveEnergy;
    private final int startEnergy;
    private Vector2d jungleBL;
    private Vector2d jungleTR;
    protected int currentlyLivingAnimals = 0;

    //properties and methods for map observers
    protected int posChangedForNAniamls = 0;


    public Map(int MapHeight, int MapWidth, double JungleRatio, int StartEnergy, int PlantEnergy, int MoveEnergy, boolean isMagic) {
        this.mapWidth = MapWidth;
        this.mapHeight= MapHeight;
        this.jungleRatio= JungleRatio;
        this.startEnergy= StartEnergy;
        this.plantEnergy= PlantEnergy;
        this.mapTR = new Vector2d(mapWidth-1, mapHeight-1);
        this.moveEnergy = MoveEnergy;
        setJungle();
        this.isMagic = isMagic;
    }

    public int getNumOfGrass() {
        return grassFields.size();
    }



    public void addObserver(IMapObserver observer) {
        observers.add(observer);
    }


    public int getNumOfCurLivingAnimals() {
        return currentlyLivingAnimals;
    }

    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);

        return visualizer.draw(mapBL.substract(MARGIN), mapTR.add(MARGIN));
    }

    public void removeAnimal(Vector2d oldPosition, Animal animal) {
        for (Animal animal1: animals.get(oldPosition)) {

            if (animal1.equals(animal)) {
                animals.get(oldPosition).remove(animal);
                if (animals.get(oldPosition).size() == 0) {
                    animals.remove(oldPosition);
                }
                break;
            }
        }
    }


    //informs map that animal has changed its position
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        posChangedForNAniamls++;
        if (!oldPosition.equals(newPosition)) {
            if(animals.get(oldPosition)!=null){
            removeAnimal(oldPosition,animal);
            insertToAnimals(animal, newPosition);
            }
        }
        if (currentlyLivingAnimals==posChangedForNAniamls) {
            simulateDay();
        }
    }



    //IWorldMap methods
    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        Object a = objectAt(animal.getPosition());
        if (!canMoveTo(animal.getPosition())) {
            if (a instanceof Animal) {
                throw new IllegalArgumentException("cannot place at " + animal.getPosition());
            }
        }
        animal.setRandomGene();
        animal.setEnergy(startEnergy,moveEnergy );
        insertToAnimals(animal, animal.getPosition());
        currentlyLivingAnimals++;

        animal.addObserver(this);

        return true;
    }



    @Override
    public Object objectAt(Vector2d position) {
        if (isOccupied(position)) {
            if (animals.get(position) != null){
                return animals.get(position);
            }
        }
        if(isOccupiedByGrass(position)){
            return grassFields.get(position); }
        return null;
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(mapBL) && position.precedes(mapTR);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }


    //find grass methods
    public boolean isOccupiedByGrass(Vector2d position) {
        return grassFields.containsKey(position);
    }

    public Object grassAt(Vector2d position) {
        return grassFields.get(position);
    }


    public void insertToAnimals(Animal animal, Vector2d position) {
        if (isOccupied(position)){
            animals.get(position).add(animal);
        }
        else {
            ArrayList<Animal> animalsAtPos = new ArrayList<>();
            animalsAtPos.add(animal);
            animals.put(position,animalsAtPos);
        }
    }

    //set jungle position and fill it with plants

    public int[] findRectangle(int width, int height, int surface) {
        int indexI=0, indexJ=0;
        double best = Integer.MAX_VALUE;
        for (int i = 1; i< width; i++) {
            for(int j =1;j<height;j++) {
                if (Math.abs((double)i*j/surface-jungleRatio)<=best) {
                    best = Math.abs((double)i*j/surface-jungleRatio);
                    indexI=i;
                    indexJ=j;
                }
            }
        }


        return new int[]{indexI, indexJ};
    }


    public void setJungle() {
        int surface = mapHeight*mapWidth;


        int[] whArr = findRectangle(mapWidth,mapHeight,surface);
        int jungleWidth = whArr[0];
        int jungleHeight = whArr[1];

        int midHeight = mapHeight/2;
        int midWidth = mapWidth/2;


        this.jungleBL = new Vector2d(midWidth-Math.floorDiv(jungleWidth-1, 2), midHeight-Math.floorDiv(jungleHeight-1, 2));
        this.jungleTR = new Vector2d(midWidth+Math.floorDiv(jungleWidth, 2), midHeight+Math.floorDiv(jungleHeight, 2));
    }
    public Vector2d getJungleBL() {
        return jungleBL;
    }
    public Vector2d getJungleTR() {
        return jungleTR;
    }

    //MAP SIMULATION----------------------------------------------------------------------------------------------------------------------
    //animals moving in prefered direction
    public void startDay() {
        this.spawnedAnimalsThisDay.clear();
        if(this.isMagic && currentlyLivingAnimals==MAGICNUMBER && magicHappened<MAGICTIMES) {
            System.out.println("magic HAPPENS" + magicHappened);
            magicHappened++;
            copyAnimals();
            for(Animal animal:spawnedAnimalsThisDay) {
                insertToAnimals(animal, animal.getPosition());
            }
        }
        sortAnimalsByEnergyInTheMorning();
        this.spawnGrassInTheJungle();
        this.spawnGrassOnSteppe();
        this.eatPlants();
        this.removeDeadAnimals();
        this.copulate();

    }

    public void copyAnimals() {
        for(ArrayList<Animal> arrayList: animals.values()) {
            for(Animal animal: arrayList) {

                Random rng = new Random();
                Vector2d position = new Vector2d(rng.nextInt(mapWidth-1),rng.nextInt(mapHeight-1));
                Animal cpAnimal = new Animal(this,position,animal.getStartEnergy());
                cpAnimal.setGeneIfCopy(animal);
                cpAnimal.setEnergy(animal.getEnergy(),moveEnergy);
                cpAnimal.addObserver(this);
                spawnedAnimalsThisDay.add(cpAnimal);
                currentlyLivingAnimals++;

            }
        }
    }


    //checking for animals that can eat each day
    public void eatPlants() {
        for(ArrayList<Animal> arrayList: animals.values()) {

            if (isOccupiedByGrass(arrayList.get(0).getPosition())) {
                int i = arrayList.size()-1;
                int highestEnergy = arrayList.get(i).getEnergy();
                int cnt = 0;

                while(i>=0 && arrayList.get(i).getEnergy()==highestEnergy) {
                    cnt++;
                    i--;
                }

                for(int j= arrayList.size()-1; j >arrayList.size()-1-cnt;j--) {
                    arrayList.get(j).eatGrass(Math.floorDiv(plantEnergy, cnt));
                }
                grassFields.remove(arrayList.get(0).getPosition());
            }
        }
    }
    //copulation is supposed to go here
    //checking for animals that can copulate
    public ArrayList<Animal> findPossibleParents(ArrayList<Animal> arr) {
        ArrayList<Animal> res = new ArrayList<>();
        for(int i = arr.size()-1; i >= 0; i--) {
            Animal parent = arr.get(i);
            if (parent.getEnergy()>=0.5*startEnergy) {
                res.add(parent);
            }
            if(res.size() == 2) {
                return res;
            }
        }
        return null;
    }


    public void copulate() {
        for(ArrayList<Animal> arrayList: animals.values()) {
            if(arrayList.size()>1) {
                ArrayList<Animal> parents = findPossibleParents(arrayList);
                if (parents != null) {
                    spawnNewAnimal(parents.get(0), parents.get(1));
                }
            }
        }

    }

    public void spawnNewAnimal(Animal parent1, Animal parent2) {


        int parent1EnergyAfter = (int) Math.ceil(parent1.getEnergy()/4);
        int parent2EnergyAfter = (int) Math.ceil(parent2.getEnergy()/4);
        int childEnergy = (int) (parent2EnergyAfter + parent1EnergyAfter);

        parent1.setEnergy(parent1.getEnergy()-parent1EnergyAfter, moveEnergy);
        parent2.setEnergy(parent2.getEnergy()-parent2EnergyAfter, moveEnergy);

        Animal child = new Animal(this, parent1.getPosition(), childEnergy);

        currentlyLivingAnimals++;
        child.setEnergy(childEnergy, moveEnergy);
        child.setGenesBasedOnParents(parent1, parent2);
        spawnedAnimalsThisDay.add(child);
        animals.get(child.getPosition()).add(child);
        child.addObserver(this);
    }

    public ArrayList<Animal> getSpawnedAnimalsThisDay() {
        return spawnedAnimalsThisDay;
    }


    public void removeDeadAnimals() {
        ArrayList<Vector2d> positionsToDelete = new ArrayList<>();
        for(ArrayList<Animal> arrayList: animals.values()) {

            Vector2d key = arrayList.get(0).getPosition();
            int animalsAtPos = arrayList.size();

            arrayList.removeIf(animal -> animal.getEnergy() <= 0);

            currentlyLivingAnimals -= (animalsAtPos-arrayList.size());

            if(arrayList.size()==0) {
                positionsToDelete.add(key);
            }
        }
        for(Vector2d positionToDelete: positionsToDelete) {
            animals.remove(positionToDelete);
        }
    }


    public void sortAnimalsByEnergyInTheMorning() {
        for(ArrayList<Animal> arrayList:animals.values()) {
            arrayList.sort(new EnergyComparator());
        }
    }



    //spawning new grass on map each day
    public void spawnGrassOnSteppe() {
        int i = 0;
        int tooManyTimes = (int) Math.pow(grassSpawnedEachDay,2);
        int cnt =0;


        Random generator= new Random();
        while (i<grassSpawnedEachDay && cnt < tooManyTimes) {

            Vector2d grassSpawnedPos = new Vector2d(generator.nextInt(this.mapWidth),
                    generator.nextInt(this.mapHeight));

            if(!this.isOccupied(grassSpawnedPos) && !this.isOccupiedByGrass(grassSpawnedPos)) {
                if (grassSpawnedPos.x < jungleBL.x || grassSpawnedPos.x >= jungleTR.x || grassSpawnedPos.y<jungleBL.y || grassSpawnedPos.y>=jungleTR.y) {
                    Grass ngrass = new Grass(grassSpawnedPos, plantEnergy);
                    grassFields.put(ngrass.getPosition(), ngrass);
                    i++;
                }
            }
            cnt++;
        }
    }


    public void spawnGrassInTheJungle() {
        int i = 0;
        int tooManyTimes = (int) Math.pow(grassSpawnedEachDay+1,2);

        Random generator= new Random();

        while(i<tooManyTimes) {
            Vector2d grassSpawnedPos = new Vector2d(generator.nextInt(this.mapWidth),
                    generator.nextInt(this.mapHeight));
            if (!isOccupied(grassSpawnedPos) && !isOccupiedByGrass(grassSpawnedPos)
                    && grassSpawnedPos.follows(jungleBL) && grassSpawnedPos.precedes(jungleTR)) {

                Grass newGrass = new Grass(grassSpawnedPos,plantEnergy);
                grassFields.put(grassSpawnedPos,newGrass);
                break;
            }
            i++;
        }
    }


    @Override
    public void simulateDay() {
        for (IMapObserver observer: observers) {
            observer.simulateDay();
        }
        posChangedForNAniamls=0;
    }
}