package agh.ics.oop.WorldClasses;


import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import agh.ics.oop.WorldClasses.Vector2d;

import java.util.HashSet;
import java.util.Set;

public class Grass implements IMapElement {

    private final Vector2d v;
    public int energy;
    public Grass(Vector2d grassPosition, int grassEnergy) {
        this.v = grassPosition;
        this.energy = grassEnergy;
    }

    public Vector2d getPosition() {
        return v;
    }

    private final Set<IPositionChangeObserver> observers = new HashSet<>();

    @Override
    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public String toString() {
        return "*";
    }

    @Override
    public String imageAddress() {
        return "src/main/resources/grass.png";
    }

    @Override
    public String getName() {
        return "Trawa";
    }

}
