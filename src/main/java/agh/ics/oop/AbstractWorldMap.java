package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {


    public static MapBoundary boundedMap= new MapBoundary();
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected Map<Vector2d, Grass> grassFields = new HashMap<>();
    private static final Vector2d MARGIN = new Vector2d(1,1);

    private int mapWidth=14; //placeholder
    private int mapHeight=14; //placeholder
    private double jungleRatio;
    protected int plantEnergy = 0;
    private int moveEnergy=1; //placeholder
    private int startEnergy = 10; //placeholder
    private Vector2d jungleBL;
    private Vector2d jungleTR;

    public Vector2d getBottomLeft() {
        return boundedMap.getBottomLeft();
    }

    public Vector2d getTopRight() {
        return boundedMap.getTopRight();
    }



    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(boundedMap.getBottomLeft().substract(MARGIN), boundedMap.getTopRight().add(MARGIN));
    }


    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        Object a = objectAt(animal.getPosition());
        if (!canMoveTo(animal.getPosition())) {
            if (a instanceof Animal) {
                throw new IllegalArgumentException("cannot place at " + animal.getPosition());
            }
        }
        animals.put(animal.getPosition(), animal);
        animal.setRandomGene();
        animal.setEnergy(startEnergy, moveEnergy);

        boundedMap.addToMap( animal.getPosition(), animal);
        animal.addObserver(this);
        animal.addObserver(boundedMap);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !animals.containsKey(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position) || grassFields.containsKey(position);
    }


    @Override
    public Object objectAt(Vector2d position) {

        if (isOccupied(position)) {
            if (animals.get(position) != null){
                return animals.get(position);
            }
            return grassFields.get(position);
        }
        return null;
    }





    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal a = animals.remove(oldPosition);
        if (this.objectAt(newPosition) instanceof Grass) {
            Grass x = grassFields.remove(newPosition);
            a.eatGrass(x);
        }
        else if (this.objectAt(newPosition) instanceof Animal) {
            a.copulate(this.objectAt(newPosition));
        }
        animals.put(newPosition, a);
    }





    public void setJungle(double jungleRatio) {
        int jungleHeight = (int) ((this.mapHeight-1)*jungleRatio);
        int jungleWidth = (int) ((this.mapWidth-1)*jungleRatio);

        this.jungleBL = new Vector2d((int)((this.mapWidth-1)-jungleWidth)/2,
                                            (int)((this.mapHeight-1)-jungleHeight)/2);
        this.jungleTR = new Vector2d(jungleBL.x+jungleWidth-1, jungleBL.y+jungleHeight-1);

//        spawnGrassInTheJungle(jungleBL, jungleTR);
    }


    public void removeDeadAnimals(IPositionChangeObserver observer) {
        System.out.println(animals);
        for(Animal animal: animals.values() ) {
            if (animal.energy<=0) {
                animal.removeObserver(this);
                animal.removeObserver(boundedMap);
                animal.removeObserver(observer);
                animals.remove(animal.getPosition());

            }
        }
    }

    public void spawnNewAnimal(Animal animal) {
        animals.put(animal.getPosition(), animal);
        boundedMap.addToMap( animal.getPosition(), animal);
        animal.addObserver(this);
        animal.addObserver(boundedMap);
    }

    public void spawnGrassInTheJungle(Vector2d botLeft, Vector2d topRight) {

        for(int i =botLeft.x; i<topRight.x;i++) {
            for(int j=botLeft.y;j<topRight.y;j++) {
                if (!this.isOccupied(new Vector2d(i,j))) {
                    Grass newGrass = new Grass(new Vector2d(i,j), this.plantEnergy);
                    grassFields.put(new Vector2d(i,j), newGrass);
                }
            }
        }
    }




    public void spawnGrassOnSteppe(int grassSpawnedEachDay) {

        int i = 0;
        int tooManyTimes = (int) Math.pow(grassSpawnedEachDay,4);
        int cnt =0;

        Random generator= new Random();

        while (i<grassSpawnedEachDay || cnt < tooManyTimes) {
            Vector2d grassSpawnedPos = new Vector2d(generator.nextInt(this.mapWidth),
                    generator.nextInt(this.mapHeight-1));

            if (!isOccupied(grassSpawnedPos) &&(grassSpawnedPos.precedes(this.jungleBL) || grassSpawnedPos.follows(this.jungleTR))) {
                Grass ngrass = new Grass(grassSpawnedPos, plantEnergy);
                grassFields.put(ngrass.getPosition(), ngrass);
                i++;
            }
            cnt++;

        }
    }





}