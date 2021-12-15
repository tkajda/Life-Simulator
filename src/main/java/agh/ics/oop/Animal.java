package agh.ics.oop;

import java.util.*;

class Animal  implements IMapElement {
    private MapDirection orient;
    private Vector2d v;
    private final AbstractWorldMap map;
    private final Set<IPositionChangeObserver> observers = new HashSet<>();
    private final Gene gene = new Gene();
    public int energy;
    public int moveEnergy;
    private List<Integer> genes;


    public Animal(AbstractWorldMap map) {
        this.map = map;
    }


    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPos, Vector2d newPos) {
        for (IPositionChangeObserver observer: observers) {
            observer.positionChanged(oldPos, newPos);
        }
    }


    public Animal(AbstractWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.v = initialPosition;
//        this.energy = StartingEnergy;
        this.orient = MapDirection.NORTH;
//        Random x= new Random();
//        int randomDirection = x.nextInt(8);
//        switch(randomDirection) {
//            case 0: this.orient=MapDirection.NORTH;
//            case 1: this.orient=MapDirection.SE;
//            case 2: this.orient=MapDirection.SOUTH;
//            case 3: this.orient=MapDirection.SW;
//            case 4: this.orient=MapDirection.NW;
//            case 5: this.orient=MapDirection.WEST;
//            case 6: this.orient=MapDirection.EAST;
//            case 7: this.orient=MapDirection.NE;
//        }
    }

    public String toString() {
        return switch(this.orient) {
            case NORTH -> "^";
            case SOUTH -> "v";
            case EAST -> ">";
            case WEST -> "<";
        };
    }

    @Override
    public String getName() {
        return "Z"+ " " + this.getPosition().toString();
    }


    @Override
    public String imageAddress() {
        String x=
        switch(this.getDirection()) {
            case NORTH -> "up";
            case SOUTH -> "down";
            case EAST -> "right";
            case WEST -> "left";
        };
        return "src/main/resources/" + x + ".png";
    }

    boolean isAt(Vector2d position) {return getPosition().equals(position);}

    public MapDirection getDirection() {return orient;}

    public Vector2d getPosition() {return v;}


    public List<Integer> getAnimalGenes() {
        return this.genes;
    }

    public void move(MoveDirection direction)  {

        this.energy-=this.moveEnergy;

        switch (direction) {
            case RIGHT -> this.orient = orient.next();
            case LEFT -> this.orient = orient.previous();
        }
        Vector2d newPos = this.getPosition();

            switch(direction) {
            case FORWARD -> newPos=this.v.add(this.orient.toUnitVector());
            case BACKWARD -> newPos=this.v.add(this.orient.toUnitVector().opposite());
        }

            if (map.canMoveTo(newPos) || newPos.equals(this.getPosition())) {
            positionChanged(this.v, newPos);
            this.v = newPos;
        }

    }


    //PROJECT PROJECT PROJECT PROJECT PROJECT PROJECT PROJECT PROJECT PROJECT PROJECT PROJECT PROJECT PROJECT
//
    public void moveWithPref() {
        Random x = new Random();

        this.energy--;
        int a = x.nextInt(32);
        List<Integer> arr = this.gene.getGenes();
        switch(arr.get(a)) {
            case 0: this.move(MoveDirection.FORWARD);
            case 7: this.move(MoveDirection.BACKWARD);
            default:
                for (int i = 0; i<arr.get(a);i++) {
                    this.orient = this.orient.next();
                }
        }
    }
//

//
//    public void setGenes(Animal parent1, Animal parent2) {
//        Random x = new Random();
//        int side = x.nextInt(2);
//        switch (side) {
//            case 0: break;
//            case 1: break;
//        }
//    }
//
//
//
//
//    public void loseEnergy(int percent) {
//        this.energy *=(int) (1-percent);
//    }
//

    public void setEnergy(int startEnergy, int moveEnergy) {
        this.energy = startEnergy;
        this.moveEnergy = moveEnergy;
    }

    public void setRandomGene() {
        this.gene.setRandomGenes();
        this.genes = gene.getGenes();
    }

    public void setGenesBaseOnParents(Animal parent1, Animal parent2) {
        this.gene.setGenes(parent1, parent2);
        this.genes = gene.getGenes();
    }

    public void eatGrass(Grass grass) {
        this.energy+=grass.energy;
    }

    public void copulate(Object object) {
        Animal otherAnimal = (Animal) object;
        int newAnimalEnergy = (int) ((this.energy /4) + (otherAnimal.energy /4));
        this.energy = (int) (this.energy * 3/4);
        otherAnimal.energy = (int) (otherAnimal.energy*3/4);

        Animal newAnimal = new Animal(this.map, this.getPosition());
        newAnimal.setGenesBaseOnParents(this, otherAnimal);
        newAnimal.setEnergy(newAnimalEnergy, moveEnergy);
        map.spawnNewAnimal(newAnimal);
    }

    public int getEnergy() {
        return this.energy;
    }
    public List<Integer> getGenes() {
        return this.genes;
    }



}