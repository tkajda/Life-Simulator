package agh.ics.oop.WorldClasses;

import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.Enums.*;

import java.util.*;

public class Animal  implements IMapElement {

    //refering to map
    private final Map map;
    private MapDirection orient;
    private Vector2d position;
    public int lengthOfLife = 0;

    //observers
    private final Set<IPositionChangeObserver> observers = new HashSet<>();

    //genes
    private final Gene gene = new Gene();
    private List<Integer> genes;

    //energy
    public int energy;
    public int moveEnergy;
    public int startingEnergy;



    //constructor
    public Animal(Map map, Vector2d initialPosition, int startingEnergy) {
        this.map = map;
        this.position = initialPosition;
        this.startingEnergy = startingEnergy;


        Random rng= new Random();
        int randomDirection = rng.nextInt(8);
        switch (randomDirection) {
            case 0 -> this.orient = MapDirection.NORTH;
            case 1 -> this.orient = MapDirection.SE;
            case 2 -> this.orient = MapDirection.SOUTH;
            case 3 -> this.orient = MapDirection.SW;
            case 4 -> this.orient = MapDirection.NW;
            case 5 -> this.orient = MapDirection.WEST;
            case 6 -> this.orient = MapDirection.EAST;
            case 7 -> this.orient = MapDirection.NE;
        }
    }



    //methods to operate on animal observers
    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPos, Vector2d newPos) {
        for (IPositionChangeObserver observer: observers) {
            observer.positionChanged(oldPos, newPos, this);
        }
    }


    //getters
    public int getEnergy() {
        return this.energy;
    }
    public int getStartEnergy(){return this.startingEnergy;};
    public List<Integer> getGenes() {
        return this.genes;
    }
    @Override
    public String getName() {
        return "Z"+ " " + this.getPosition().toString();
    }

    public MapDirection getDirection() {return orient;}

    public Vector2d getPosition() {return position;}


    public List<Integer> getAnimalGenes() {
        return this.genes;
    }



    public String toString() {
        return switch(this.orient) {
            case NW -> "NW";
            case NE -> "NE";
            case SW -> "SW";
            case SE -> "SE";
            case NORTH -> "^";
            case SOUTH -> "v";
            case EAST -> ">";
            case WEST -> "<";
        };
    }


    @Override
    public String imageAddress() {
        String x=
        switch(this.getDirection()) {
            case NORTH -> "up";
            case SOUTH -> "down";
            case EAST -> "right";
            case WEST -> "left";
            case NW -> "topleft";
            case NE -> "topright";
            case SW -> "bottomleft";
            case SE -> "bottomright";
        };
        return "src/main/resources/" + x + ".png";
    }



    //do random move basing on genetype
    //MoveDirection includes 8 directions now(6 are just rotations of animal)
    public void moveWithPref() {

        this.lengthOfLife++;
        Random rng = new Random();
        this.energy-=this.moveEnergy;

        int gene = rng.nextInt(32);
        List<Integer> arr = this.gene.getGenes();
        switch(arr.get(gene)) {
            case 0: this.move(MoveDirection.FORWARD);
            case 4: this.move(MoveDirection.BACKWARD);
            default:
                for (int i = 0; i<arr.get(gene);i++) {
                    this.orient = this.orient.next();
                }
                positionChanged(this.getPosition(),this.getPosition());
        }
    }

    //make a move forward or backwards
    public void move(MoveDirection direction)  {
        Vector2d newPos = this.getPosition();

            switch(direction) {
            case FORWARD -> newPos=this.position.add(this.orient.toUnitVector());
            case BACKWARD -> newPos=this.position.add(this.orient.toUnitVector().opposite());
        }
        if (map.canMoveTo(newPos) || newPos.equals(this.getPosition())) {
            positionChanged(this.position, newPos);
            this.position = newPos;
        }
    }


    //set energy, could also be included in constructor
    public void setEnergy(int startEnergy, int moveEnergy) {
        this.energy = startEnergy;
        this.moveEnergy = moveEnergy;
    }

    //restoring energy if animal steps on grass
    public void eatGrass(int energy) {
        this.energy+=energy;
    }


    //methods to generate genes-random if animal doesnt have parents :(,
    // setGenesBasedOnParents-if has 2 parents
    public void setRandomGene() {
        this.gene.setRandomGenes();
        this.genes = gene.getGenes();
    }

    public void setGenesBasedOnParents(Animal parent1, Animal parent2) {
        this.gene.setGenes(parent1, parent2);
        this.genes = gene.getGenes();
    }

}